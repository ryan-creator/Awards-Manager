/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awardsManager;

import Emails.MainEmails;


/**
 *
 * @author loganbartosh
 */
public class EmailsTest {
    public static void main(String[] args) {
        MainEmails email = new MainEmails();
        email.forgotPassword("2", "resetLink");
    }
}
