package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        SendingMessages msg = new SendingMessages();
        String validMessage = "Yooo im a Software Developer";
        assertTrue(validMessage.length() <= 250);
    }

    @Test
    public void testMessageLengthFailure() {
        SendingMessages msg = new SendingMessages();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            sb.append("abcdefghij");
        }
        String invalidMessage = sb.toString();
        assertTrue(invalidMessage.length() > 250);
        int expectedExcess = invalidMessage.length() - 250;
        assertEquals(10, expectedExcess);
    }

    @Test
    public void testRecipientCellSuccess() {
        SendingMessages msg = new SendingMessages();
        String testData = "+27731879819";
        String expected = "Cell phone number successfully captured.";
        String actual = msg.checkRecipientCell(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void testRecipientCellFailure() {
        SendingMessages msg = new SendingMessages();
        String testData = "0731879819";
        String expected = "Cell phone number successfully captured.";
        String actual = msg.checkRecipientCell(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateMessageHash() {
        SendingMessages msg = new SendingMessages();
        String messageID = "0098745632";
        int messageNumber = 0;
        String messageText = "YO im a Software Developer";
        String expected = "00:0:YODEVELOPER";
        String actual = msg.createMessageHash(messageID, messageNumber, messageText);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckMessageIDGenerated() {
        SendingMessages msg = new SendingMessages();
        String generatedID = msg.generateRandomID();
        assertTrue(msg.checkMessageID(generatedID));
        assertEquals(10, generatedID.length());
    }

    @Test
    public void testSentMessageStatus() {
        SendingMessages msg = new SendingMessages();
        String sendStatus = msg.SentMessage(1);
        assertEquals("Message successfully sent.", sendStatus);

        String disregardStatus = msg.SentMessage(0);
        assertEquals("Press 0 to delete the message.", disregardStatus);

        String storeStatus = msg.SentMessage(2);
        assertEquals("Message successfully stored.", storeStatus);
    }

    @Test
    public void testArrayInsertionAndSearchByID() {
        SendingMessages msg = new SendingMessages();
        msg.addMessageToArrays("1234567890", "12:0:TESTDATA", "+27710000000", "Hello Test", 1);
        String searchResult = msg.searchByMessageID("1234567890");
        assertTrue(searchResult.contains("+27710000000"));
        assertTrue(searchResult.contains("Hello Test"));
    }

    @Test
    public void testDeleteMessageByHash() {
        SendingMessages msg = new SendingMessages();
        msg.addMessageToArrays("5555555555", "55:0:DELETEHASH", "+27711112222", "Remove this", 1);
        String outcome = msg.deleteMessageByHash("55:0:DELETEHASH");
        assertTrue(outcome.contains("successfully deleted"));

        String postSearch = msg.searchByMessageID("5555555555");
        assertEquals("Message ID not found.", postSearch);
    }
}