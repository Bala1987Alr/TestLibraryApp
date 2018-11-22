package com.bala.mylibrary.mylibrary.model;

public class UserModel implements IUser {

    String name;
    String password;

    public UserModel(String name, String password)
    {   this.name = name;
        this.password = password;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public int isValidEntry(String name, String password) {
        if (name==null||password==null|| name.length()==0 || password.length()==0)
        {
            return -1;
        }
        return 0;
    }
}
