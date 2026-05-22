package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.Random;
import java.util.Scanner;

public class SendingMessages {

    private final Random random = new Random();
    private int totalMessagesSent = 0;
    private final StringBuilder messageLog = new StringBuilder();

    public boolean checkMessageID(String messageID) {
        if (messageID == null) {
            return false;
        }
        return messageID.length() <= 10;
    }

    public String checkRecipientCell(String cellNumber) {
        if (cellNumber == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (cellNumber.startsWith("+27") && cellNumber.length() == 12) {
            return "Cell phone number successfully captured.";
        }

        if (cellNumber.startsWith("0") && cellNumber.length() == 10) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {


        if (messageID == null || messageText == null || messageText.trim().isEmpty()) {
            return "00:0:EMPTY";
        }

        String prefix = messageID.length() >= 2 ? messageID.substring(0, 2) : "00";
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        firstWord = firstWord.replaceAll("[^a-zA-Z]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");

        String hash = prefix + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String SentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            return "Message successfully sent.";

        } else if (choice == 0) {
            return "Press 0 to delete the message.";

        } else if (choice == 2) {
            return "Message successfully stored.";
        }


        return "Invalid choice.";
    }


    public String printMessages() {

        return messageLog.toString();
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }


    public void logMessageDetails(String id, String hash, String recipient, String text) {


        messageLog.append("Message ID: ").append(id).append(" ");
        messageLog.append("Message Hash: ").append(hash).append(" ");
        messageLog.append("Recipient: ").append(recipient).append(" ");
        messageLog.append("Message: ").append(text).append("\n\n");

    }


    public String generateRandomID() {
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public void storeMessage(String messageID, String messageHash, String recipient, String messageText) {
        try {
            java.io.File file = new java.io.File("stored_messages.json");
            StringBuilder jsonBuilder = new StringBuilder();

            if (file.exists() && file.length() > 0) {

                String existingContent = new String(java.nio.file.Files.readAllBytes(file.toPath())).trim();


                if (existingContent.endsWith("]")) {
                    jsonBuilder.append(existingContent.substring(0, existingContent.length() - 1));
                    jsonBuilder.append(",\n");

                }

            } else {
                jsonBuilder.append("[\n");
            }


            jsonBuilder.append("  {\n")
                    .append("    \"messageID\": \"").append(messageID).append("\",\n")
                    .append("    \"messageHash\": \"").append(messageHash).append("\",\n")
                    .append("    \"recipient\": \"").append(recipient).append("\",\n")
                    .append("    \"message\": \"").append(messageText.replace("\"", "\\\"")).append("\"\n")
                    .append("  }");

            jsonBuilder.append("\n]");

            java.nio.file.Files.write(file.toPath(), jsonBuilder.toString().getBytes());
        } catch (java.io.IOException e) {

        }
    }
}

