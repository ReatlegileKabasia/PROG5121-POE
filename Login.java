package org.example;

import java.util.Scanner;

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

public boolean checkPhone(String phoneNumber) {
    if (phoneNumber.startsWith("+27") && phoneNumber.length() == 12) {
        return true;
    }
    return false;
}


public String registerUser(String user, String pass, String phone) {
    if (!userNameCheck(user)) {
        System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
    }
    if (!passwordComplexityCheck(pass)) {
        System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
    }
        if (!checkPhone(phone)) {
        System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
    }


    this.username = user;
    this.password = pass;
     this.phoneNumber = phone;

    return "Username, Password and Cell phone number successfully captured.";
}


public boolean loginUser(String user, String pass) {
    if (user.equals(this.username) && pass.equals(this.password)) {
        return true;
    }
    return false;
}

public String returnLoginStatus(boolean success) {
    if (success) {
        System.out.println("Welcome " + username);
    }
    return "Username or password incorrect, please try again.";
}

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

        // We use a boolean to track if registration is finished
        boolean isRegistered = false;

        System.out.println("-Register Here-");

        while (!isRegistered) {
            System.out.print("First Name: ");
            String f = input.nextLine();

            System.out.print("Last Name: ");
            String l = input.nextLine();

            System.out.print("Username: ");
            String u = input.nextLine();

            System.out.print("Password: ");
            String p = input.nextLine();

            System.out.print("Phone: ");

            String c = input.nextLine();

            String result = auth.registerUser(u, p, c);
            System.out.println(" " + result);


            if (result.equals("Username, Password and Cell phone number successfully captured.")) {
                isRegistered = true;
            } else {
                System.out.println("Please try again with correct details.\n");
            }
        }


        System.out.println("- Login -");
        System.out.print("Username: "); String loginU = input.nextLine();
        System.out.print("Password: "); String loginP = input.nextLine();

        if (auth.loginUser(loginU, loginP)) {
            System.out.println(auth.returnLoginStatus(true));
        } else {
            System.out.println(auth.returnLoginStatus(false));
        }
    }
}