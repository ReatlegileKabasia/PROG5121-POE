package org.example;

import java.util.Scanner;

public class QuickChatApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SendingMessages messageManager = new SendingMessages();

        System.out.println("Welcome to QuickChat.");

        boolean running = true;

        while (running) {
            System.out.println("\nMenu Options:");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages Options");

            System.out.print("Select an option: ");

            int menuChoice = -1;
            if (input.hasNextInt()) {
                menuChoice = input.nextInt();
                input.nextLine();
            } else {
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

                        messageManager.addMessageToArrays(messageID, messageHash, recipient, messageText, actionChoice);

                        if (actionChoice == 1) {
                            messageManager.logMessageDetails(messageID, messageHash, recipient, messageText);
                            System.out.println(" Sent Message Summary ");
                            System.out.println("Message ID: " + messageID);
                            System.out.println("Message Hash: " + messageHash);
                            System.out.println("Recipient: " + recipient);
                            System.out.println("Message: " + messageText);
                            System.out.println("----------------------------");
                            messageManager.storeMessage(messageID, messageHash, recipient, messageText);
                        } else if (actionChoice == 2) {
                            messageManager.storeMessage(messageID, messageHash, recipient, messageText);
                        }
                    }

                    System.out.println("\nTotal number of messages accumulated this session: " + messageManager.returnTotalMessagess());
                    break;

                case 2:
                    String recentLogs = messageManager.printMessages();
                    if (recentLogs.trim().isEmpty()) {
                        System.out.println("No messages sent during this session yet.");
                    } else {
                        System.out.println("\n=== RECENTLY SENT MESSAGES CONSOLE LOG ===");
                        System.out.print(recentLogs);
                    }
                    break;

                case 3:
                    running = false;
                    break;

                case 4:
                    boolean insideSubMenu = true;
                    while (insideSubMenu) {
                        System.out.println("\n--- Stored Messages Sub-Menu ---");
                        System.out.println("a. Read and parse JSON database file");
                        System.out.println("b. Display longest stored message text");
                        System.out.println("c. Search for message by Message ID");
                        System.out.println("d. Search hashes by Recipient number");
                        System.out.println("e. Delete a message entry using its Hash code");
                        System.out.println("f. Generate Full Database Text Report");
                        System.out.println("g. Return to main menu");
                        System.out.print("Select an option letter: ");

                        String selection = input.nextLine().trim().toLowerCase();
                        if (selection.isEmpty()) continue;

                        switch (selection.charAt(0)) {
                            case 'a':
                                int objectCount = messageManager.readJSONFileIntoArray().size();
                                System.out.println("Successfully processed " + objectCount + " unique JSON components directly into system arrays.");
                                break;
                            case 'b':
                                System.out.println("\nLongest Stored Message:\n" + messageManager.getLongestStoredMessage());
                                break;
                            case 'c':
                                System.out.print("Enter target Message ID to locate: ");
                                String searchID = input.nextLine();
                                System.out.println(messageManager.searchByMessageID(searchID));
                                break;
                            case 'd':
                                System.out.print("Enter target Recipient Cell Phone number: ");
                                String searchCell = input.nextLine();
                                System.out.println(messageManager.searchByRecipient(searchCell));
                                break;
                            case 'e':
                                System.out.print("Enter structural Hash value to target for removal: ");
                                String targetHash = input.nextLine();
                                System.out.println(messageManager.deleteMessageByHash(targetHash));
                                break;
                            case 'f':
                                System.out.println("\n" + messageManager.generateStoredReport());
                                break;
                            case 'g':
                                insideSubMenu = false;
                                break;
                            default:
                                System.out.println("Invalid sub-option letter chosen.");
                                break;
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
        input.close();
    }
}