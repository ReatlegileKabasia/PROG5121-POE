package com.mycompany.login;

import java.util.Scanner;

public class Login {

    private String username;
    private String password;
    private String firstName;
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
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasANumber = true;
                break;
            }
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
        // keeping it simple for the check
        String clean = phoneNumber.replace(" ", "");
        if (clean.startsWith("+27") && clean.length() == 12) {
            return true;
        }
        return false;
    }


    public String registerUser(String user, String pass, String f, String l, String phone) {

        boolean canRegister = true;

        if (!userNameCheck(user)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            canRegister = false;
        }
        if (!passwordComplexityCheck(pass)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            canRegister = false;
        }
        if (!checkPhone(phone)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            canRegister = false;
        }

        if (canRegister) {
            this.username = user;
            this.password = pass;
            this.firstName = f;
            this.surName = l;
            this.phoneNumber = phone;

            return "Username, Password and Cell phone number successfully captured.";
        } else {
            return "Registration failed, please try again.";
        }
    }


    public boolean loginUser(String user, String pass) {
        if (user.equals(this.username) && pass.equals(this.password)) {
            return true;
        }
        return false;
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + ", " + surName + " ";
        }
        return "Username or password incorrect, please try again.";
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

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

            String result = auth.registerUser(u, p, f, l, c);
            System.out.println(" " + result);


            if (result.equals("Username, Password and Cell phone number successfully captured.")) {
                isRegistered = true;
            } else {
                System.out.println("Details invalid... try again.\n");
            }
        }


        System.out.println("\n- Login -");
        System.out.print("Username: ");
        String loginU = input.nextLine();
        System.out.print("Password: ");
        String loginP = input.nextLine();

        boolean ok = auth.loginUser(loginU, loginP);
        System.out.println(auth.returnLoginStatus(ok));

    }
}