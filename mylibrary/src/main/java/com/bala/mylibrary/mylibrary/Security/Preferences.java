package com.bala.mylibrary.mylibrary.Security;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

public class Preferences {

    private static final String AndroidKeyStore = "AndroidKeyStore";
    private static final String RSA_MODE =  "RSA/ECB/PKCS1Padding";
    private static final String KEY_ALIAS="LIQUEDAPP";
    public static final String SHARED_PREFENCE_NAME="APPHEART";
    public static final String ENCRYPTED_KEY="ENCRYPTED_KEY";
    public static final String USER_NAME="USER_NAME";
    public static final String USER_PASSWORD="USER_PASSWORD";
    public static final String IS_REGISTERED="IS_REGISTERED";
    public static final String IS_LOGIN="IS_LOGIN";
    private static final String AES_MODE = "AES/ECB/PKCS7Padding";
    private KeyStore keyStore;
    private Context context;


    public Preferences(Context context)
    {
        this.context=context;
    }

    private void generateKeyPair(Context context) throws NoSuchProviderException,InvalidAlgorithmParameterException,KeyStoreException, NoSuchAlgorithmException,
            IOException,CertificateException
    {
        keyStore=KeyStore.getInstance(AndroidKeyStore);
        keyStore.load(null);

        //Here first time we are generating the RSA Key pair, the reason is for
        // this we never prepare any key from key store

        if(!keyStore.containsAlias(KEY_ALIAS));
        {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 30);

            KeyPairGeneratorSpec spec = new  KeyPairGeneratorSpec.Builder(context)
                    .setAlias(KEY_ALIAS)
                    .setSubject(new X500Principal("CN=" + KEY_ALIAS))
                    .setSerialNumber(BigInteger.TEN)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, AndroidKeyStore);
            kpg.initialize(spec);
            kpg.generateKeyPair();

        }


    }
    public byte[] rsaEncrypt(byte[] secret){

        byte[] values=null;
        Cipher inputCipher=null;
        try {

            keyStore=KeyStore.getInstance(AndroidKeyStore);
            keyStore.load(null);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            PublicKey publicKey=privateKeyEntry.getCertificate().getPublicKey();


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // below android m
                inputCipher = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
            }
            else {
                // android m and above
                inputCipher = Cipher.getInstance(RSA_MODE, "AndroidKeyStoreBCWorkaround");
            }
            inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inputCipher);
            cipherOutputStream.write(secret);
            cipherOutputStream.close();
            values = outputStream.toByteArray();
        } catch(Exception exception)
        {
            exception.printStackTrace();

        }
        return values;
    }

    public Key getSecretKey(Context context) throws Exception{

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFENCE_NAME, Context.MODE_PRIVATE);
        String encryptedKeyB64 = pref.getString(ENCRYPTED_KEY, null);
        byte[] encryptedKey = Base64.decode(encryptedKeyB64, Base64.DEFAULT);
        byte[] key = rsaDecrypt(encryptedKey);
        return new SecretKeySpec(key, "AES");

    }

    private void generateAndStoreAES(Context context) throws Exception{
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFENCE_NAME, Context.MODE_PRIVATE);
        String encryptedKeyB64 = pref.getString(ENCRYPTED_KEY, null);

        if (encryptedKeyB64 == null || encryptedKeyB64.length()==0) {
            byte[] key = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(key);
            byte[] encryptedKey = rsaEncrypt(key);
            String encrypted=Base64.encodeToString(encryptedKey, Base64.DEFAULT);
            encryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT);
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(ENCRYPTED_KEY, encryptedKeyB64);
            edit.commit();
        }



    }

    public String encrypt(Context context, byte[] input) throws Exception{
        Cipher c = Cipher.getInstance(AES_MODE, "BC");
        c.init(Cipher.ENCRYPT_MODE, getSecretKey(context));
        byte[] encodedBytes = c.doFinal(input);
        String encryptedBase64Encoded =  Base64.encodeToString(encodedBytes, Base64.DEFAULT);
        return encryptedBase64Encoded;
    }


    public byte[] decrypt(Context context, byte[] encrypted) throws Exception{
        Cipher c = Cipher.getInstance(AES_MODE, "BC");
        c.init(Cipher.DECRYPT_MODE, getSecretKey(context));
        byte[] decodedBytes = c.doFinal(encrypted);
        return decodedBytes;
    }

    public   byte[]  rsaDecrypt(byte[] encrypted) throws Exception {

        keyStore=KeyStore.getInstance(AndroidKeyStore);
        keyStore.load(null);
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(KEY_ALIAS, null);
        Cipher output;
        ArrayList<Byte> values = new ArrayList<>();

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // below android m
                output = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
            }
            else {
                // android m and above
                output = Cipher.getInstance(RSA_MODE, "AndroidKeyStoreBCWorkaround");
            }
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(encrypted), output);

            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }
        } catch(Exception exception) {
            throw new RuntimeException("getCipher: Failed to get an instance of Cipher", exception);
        }
        byte[] bytes = new byte[values.size()];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = values.get(i).byteValue();
        }
        return bytes;
    }

    public void storeUserCredentials(String userName, String password) throws Exception
    {
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREFENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        generateKeyPair(context);
        generateAndStoreAES(context);
        editor.putString(USER_NAME,encrypt(context,userName.getBytes()));
        editor.putString(USER_PASSWORD,encrypt(context,password.getBytes()));
        editor.putBoolean(IS_REGISTERED, true);
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

    }




}
