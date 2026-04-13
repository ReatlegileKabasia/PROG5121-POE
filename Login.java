package com.mycompany.login;

import java.util.Scanner;

public class Login {

    private String username;
    private String password;
    private String Name;
    private String surName;
    private String phoneNumber;



    public boolean userNameCheck (String username) {
        if (username.contains("_")) {
            return username.length() <= 5;
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

        if (!hasANumber) {
            return false;   }

        boolean hasSpecialChar = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;  }
        }
        return hasSpecialChar;


    }

    public boolean checkPhone(String phoneNumber) {

        String clean = phoneNumber.replace(" ", "");
        return clean.startsWith("+27") && clean.length() == 12;
    }


    public String registerUser(String user, String pass, String name, String surname, String phone) {

        boolean allowedRegister = true;

        if (!userNameCheck(user)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            allowedRegister = false;
        }
        if (!passwordComplexityCheck(pass)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            allowedRegister = false;
        }
        if (!checkPhone(phone)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            allowedRegister = false;
        }

        if (allowedRegister) {
            this.username = user;
            this.password = pass;
            this.Name = name;
            this.surName = surname;
            this.phoneNumber = phone;

            return "Username, Password and Cell phone number successfully captured.";
        } else {
            return "Registration failed, please try again.";
        }
    }


    public boolean loginUser(String user, String pass) {
        return user.equals(this.username) && pass.equals(this.password);
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome in " + Name + ", " + surName + " ";
        }
        return "Username or password incorrect, please try again.";
    }

    static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

        boolean isRegistered = false;

        System.out.println("-Register Here-");

        while (!isRegistered) {
            System.out.print("Name: ");
            String n = input.nextLine();

            System.out.print("Surname: ");
            String s = input.nextLine();


            System.out.print("Username: ");
            String u = input.nextLine();

            System.out.print("Password: ");
            String p = input.nextLine();

            System.out.print("Phone Number: ");

            String c = input.nextLine();

            String result = auth.registerUser(u, p, n, s, c);
            System.out.println(" " + result);


            if (result.equals("Username, Password and Cell phone number successfully captured.")) {
                isRegistered = true;
            } else {
                System.out.println("Details invalid... try again.\n");
            }
        }


        System.out.println("- Login -");
        System.out.print("Username: ");
        String loginU = input.nextLine();
        System.out.print("Password: ");
        String loginP = input.nextLine();

        boolean ok = auth.loginUser(loginU, loginP);
        System.out.println(auth.returnLoginStatus(ok));

    }
}