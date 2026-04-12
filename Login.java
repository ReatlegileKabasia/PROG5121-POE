package org.example;

public class Login {

    private String username;
    private String password;
    private String Name;
    private String surName;
    private String phoneNumber;



public boolean userNameCheck (String username) {
    if (username.contains("_")) {
        if (username.length() <= 5) {
            return true;
        }
    }
    return false;
}

public boolean passwordComplexityCheck(String password) {

    if (password.length() < 8) {
        return false;
    }

    if (password.equals(password.toLowerCase())) {
        return false;
    }

    boolean hasANumber = false;
    if (password.matches(".*[0-9].*")) {
        hasANumber = true;


    }

    if (hasANumber == false) {
        return false;   }

    boolean hasSpecialChar = false;

    for (int i = 0; i < password.length(); i++) {
        char c = password.charAt(i);
        if (!Character.isLetterOrDigit(c)) {
            hasSpecialChar = true;  }
    }
    if (hasSpecialChar == false) {
        return false;   }


    return true;


}

public boolean checkPhone(String cell) {
    if (cell.startsWith("+27") && cell.length() == 12) {
        return true;
    }
    return false;
}


public String registerUser(String user, String pass, String first, String last, String phone) {
    if (!userNameCheck(user)) {
        return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    }
    if (!passwordComplexityCheck(pass)) {
        return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    }
    if (!checkPhone(phone)) {
        return "Cell phone number incorrectly formatted or does not contain international code.";
    }


    this.username = user;
    this.password = pass;
     this.phoneNumber = phone;

    return "Username, Password and Cell phone number successfully captured.";
}

}