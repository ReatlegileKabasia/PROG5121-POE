package org.example;
import java.util.Scanner;
public class Login {

    private String username;
    private String password;
    private String Name;
    private String surName;
    private String phoneNumber;

}

public boolean userNameCheck (String username) {
    if (username.contains("_")) {
        if (username.length() <= 5) {
            return true;
        }
    }
    return false;
}

