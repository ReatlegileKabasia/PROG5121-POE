package com.mycompany.login;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testUsername() {
        // Data: kyl_1
        assertTrue(login.userNameCheck("kyl_1"));
    }

    @Test
    public void testPassword() {
        // Data: Ch&&sec@ke99!
        assertTrue(login.passwordComplexityCheck("Ch&&sec@ke99!"));
    }

    @Test
    public void testPhone() {
        assertTrue(login.checkPhone("+27838968976"));
    }
}