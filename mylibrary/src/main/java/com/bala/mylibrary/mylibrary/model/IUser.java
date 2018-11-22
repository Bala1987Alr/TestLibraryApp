package com.bala.mylibrary.mylibrary.model;

public interface IUser {

    String getName();
    String getPassword();
    int isValidEntry(String name, String password);
}
