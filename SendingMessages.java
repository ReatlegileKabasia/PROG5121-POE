package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

public class SendingMessages {

    private final Random random = new Random();
    private int totalMessagesSent = 0;
    private final StringBuilder messageLog = new StringBuilder();

    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> disregardedMessages = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();
    private final ArrayList<String> recipients = new ArrayList<>();

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

    public void addMessageToArrays(String id, String hash, String recipient, String text, int actionChoice) {
        messageIDs.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);

        if (actionChoice == 1) {
            sentMessages.add(text);
        } else if (actionChoice == 0) {
            disregardedMessages.add(text);
        } else if (actionChoice == 2) {
            sentMessages.add(text);
        }
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
                jsonBuilder.append("[ ");
            }

            jsonBuilder.append("  {\n")
                    .append("    \"messageID\": \"").append(messageID).append("\",\n")
                    .append("    \"messageHash\": \"").append(messageHash).append("\",\n")
                    .append("    \"recipient\": \"").append(recipient).append("\",\n")
                    .append("    \"message\": \"").append(messageText.replace("\"", "\\\"")).append("\"\n")
                    .append("  }");

            jsonBuilder.append(" ]");
            java.nio.file.Files.write(file.toPath(), jsonBuilder.toString().getBytes());
        } catch (java.io.IOException e) {
        }
    }

    public ArrayList<String> readJSONFileIntoArray() {
        ArrayList<String> records = new ArrayList<>();
        try {
            File file = new File("stored_messages.json");
            if (!file.exists() || file.length() == 0) return records;

            String content = new String(Files.readAllBytes(file.toPath()));
            content = content.replace("[", "").replace("]", "").trim();
            if (content.isEmpty()) return records;

            String[] objects = content.split("\\s*}\\s*,?\\s*\\{\\s*");
            for (String obj : objects) {
                String cleanObj = obj.trim();
                if (!cleanObj.startsWith("{")) cleanObj = "{" + cleanObj;
                if (!cleanObj.endsWith("}")) cleanObj = cleanObj + "}";
                records.add(cleanObj);
            }
        } catch (IOException e) {
        }
        return records;
    }

    public String getLongestStoredMessage() {
        ArrayList<String> rawObjects = readJSONFileIntoArray();
        String longestMsg = "";
        for (String obj : rawObjects) {
            String msgText = extractJSONValue(obj, "message");
            if (msgText.length() > longestMsg.length()) {
                longestMsg = msgText;
            }
        }
        return longestMsg.isEmpty() ? "No messages found." : longestMsg;
    }

    public String searchByMessageID(String targetID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(targetID)) {
                String type = i < sentMessages.size() ? sentMessages.get(i) : "Stored Data";
                return "Recipient: " + recipients.get(i) + " Message: " + type;
            }
        }
        return "Message ID not found.";
    }

    public String searchByRecipient(String targetRecipient) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(targetRecipient)) {
                sb.append("- ").append(messageHashes.get(i)).append(": ").append(messageIDs.get(i)).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No records found for that recipient.";
    }

    public String deleteMessageByHash(String targetHash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(targetHash)) {
                String removedText = i < sentMessages.size() ? sentMessages.get(i) : "Message details";
                messageIDs.remove(i);
                messageHashes.remove(i);
                recipients.remove(i);
                if (i < sentMessages.size()) {
                    sentMessages.remove(i);
                }
                return "Message:  " + removedText + "  successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public String generateStoredReport() {
        ArrayList<String> objects = readJSONFileIntoArray();
        if (objects.isEmpty()) return "No messages saved inside JSON database storage.";

        StringBuilder report = new StringBuilder("=== STORED MESSAGES REPORT === ");
        for (String obj : objects) {
            report.append("ID: ").append(extractJSONValue(obj, "messageID")).append(" | ")
                    .append("Hash: ").append(extractJSONValue(obj, "messageHash")).append(" ")
                    .append("Recipient: ").append(extractJSONValue(obj, "recipient")).append(" ")
                    .append("Message: ").append(extractJSONValue(obj, "message")).append(" ")
                    .append("------------------------------------------ ");
        }
        return report.toString();
    }

    private String extractJSONValue(String jsonObject, String key) {
        try {
            int keyIndex = jsonObject.indexOf(" " + key + " ");
            if (keyIndex == -1) return "";
            int colonIndex = jsonObject.indexOf(":", keyIndex);
            int startQuote = jsonObject.indexOf(" ", colonIndex);
            int endQuote = jsonObject.indexOf(" ", startQuote + 1);
            return jsonObject.substring(startQuote + 1, endQuote);
        } catch (Exception e) {
            return "";
        }
    }
}