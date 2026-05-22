package org.example;

import java.util.Scanner;

public class QuickChatApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SendingMessages messageManager = new SendingMessages();

        System.out.println("Welcome to QuickChat.");

        boolean running = true;


        while (running) {
            System.out.println("Menu Options:");

            System.out.println("1. Send Messages");

            System.out.println("2 Show recently sent messages");
            System.out.println("3. Quit");

            System.out.print("Select an option: ");

            int menuChoice = -1;
            if (input.hasNextInt()) {
                menuChoice = input.nextInt();
                input.nextLine();
            } else
            {
                input.nextLine();
                System.out.println("Invalid numeric selection.");
                continue;

            }

            switch (menuChoice) {
                case 1:
                    System.out.print("How many messages do you wish to enter? ");
                    int count = 0;

                    if (input.hasNextInt()) {

                        count = input.nextInt();
                        input.nextLine();
                    } else {
                        input.nextLine();
                        System.out.println("Invalid count entered.");
                        break;
                    }

                    for (int i = 0; i < count; i++) {
                        System.out.println("Entering details for Message " + (i + 1) + ":");

                        String messageID = messageManager.generateRandomID();

                        System.out.print("Enter Recipient Cell Number: ");
                        String recipient = input.nextLine();
                        String recipientValidation = messageManager.checkRecipientCell(recipient);
                        System.out.println(recipientValidation);

                        System.out.print("Enter Message (Max 250 characters): ");
                        String messageText = input.nextLine();

                        if (messageText.length() > 250) {
                            int overLimit = messageText.length() - 250;
                            System.out.println("Message exceeds 250 characters by " + overLimit + "; please reduce the size.");
                            i--;
                            continue;
                        } else {
                            System.out.println("Message ready to send.");
                        }

                        String messageHash = messageManager.createMessageHash(messageID, i, messageText);

                        System.out.println("Options for this message:");

                        System.out.println("1. - Send Message");
                        System.out.println("0. - Disregard Message");
                        System.out.println("2. - Store Message to send later");
                        System.out.print("Your choice: ");

                        int actionChoice = -1;
                        if (input.hasNextInt()) {
                            actionChoice = input.nextInt();
                            input.nextLine();
                        } else {
                            input.nextLine();
                            System.out.println("Invalid input. Disregarding message.");
                            actionChoice = 0;
                        }

                        String actionStatus = messageManager.SentMessage(actionChoice);
                        System.out.println(actionStatus);

                        if (actionChoice == 1) {
                            messageManager.logMessageDetails(messageID, messageHash, recipient, messageText);
                            System.out.println(" Sent Message Summary ");
                            System.out.println("Message ID: " + messageID);
                            System.out.println("Message Hash: " + messageHash);
                            System.out.println("Recipient: " + recipient);
                            System.out.println("Message: " + messageText);
                            System.out.println("----------------------------");
                        }
                    }

                    System.out.println("\nTotal number of messages accumulated this session: " + messageManager.returnTotalMessagess());
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
        input.close();
    }
}