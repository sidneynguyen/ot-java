package com.sidneynguyen.otjava;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

public class IntegrationTest {

	@Test
	public void clientTest() {
		Client client = new Client("Hello", "asdasd");
        client.enqueueReceivedOp(new Message("xyz", "asdasd", new Operation(
                Arrays.asList(
                        new Component(Component.Type.RETAIN, 3, null),
                        new Component(Component.Type.INSERT, 1, "y"),
                        new Component(Component.Type.RETAIN, 2, null)
                )
        )));
		client.applyReceivedOp();
		assertEquals("Helylo", client.getDocument());

		client.applyLocalEdits("Hello");
		assertEquals("Hello", client.getDocument());
	}

	@Test
    public void serverTest() {
	    Server server = new Server("Hello", "asdasd");
	    server.enqueueReceivedOp(new Message("xyz", "asdasd", new Operation(
	            Arrays.asList(
                        new Component(Component.Type.RETAIN, 3, null),
                        new Component(Component.Type.INSERT, 1, "y"),
                        new Component(Component.Type.RETAIN, 2, null)
                )
        )));
	    server.applyReceivedOp();
	    assertEquals("Helylo", server.getDocument());
    }

    @Test
    public void twoClientTest() {
        Client client1 = new Client("abc", "s1");
        Client client2 = new Client("abc", "s1");
        Server server = new Server("abc", "s1");

        client1.applyLocalEdits("a1b2c3");
        assertEquals("a1b2c3", client1.getDocument());

        client2.applyLocalEdits("xyz");
        assertEquals("xyz", client2.getDocument());

        client1.enqueueOpToSend();
        Message m1_1 = client1.sendOp();

        server.enqueueReceivedOp(m1_1);
        server.applyReceivedOp();
        Message s1_1 = server.sendOp();
        assertEquals("a1b2c3", server.getDocument());

        client1.enqueueReceivedOp(s1_1);
        client1.applyReceivedOp();
        assertEquals("a1b2c3", client1.getDocument());

        client2.enqueueReceivedOp(s1_1);
        client2.applyReceivedOp();
        client2.enqueueOpToSend();
        Message c2_1 = client2.sendOp();
        assertEquals("1x2y3z", client2.getDocument());

        server.enqueueReceivedOp(c2_1);
        server.applyReceivedOp();
        Message s1_2 = server.sendOp();

        client1.enqueueReceivedOp(s1_2);
        client1.applyReceivedOp();

        client2.enqueueReceivedOp(s1_2);
        client2.applyReceivedOp();

        assertEquals(server.getDocument(), client1.getDocument());
        assertEquals(server.getDocument(), client2.getDocument());
    }

    @Test
    public void twoClientTest2() {
        Client client1 = new Client("abc", "s1");
        Client client2 = new Client("abc", "s1");
        Server server = new Server("abc", "s1");

        client1.applyLocalEdits("a1b2c3");
        assertEquals("a1b2c3", client1.getDocument());

        client2.applyLocalEdits("xyz");
        assertEquals("xyz", client2.getDocument());

        client1.enqueueOpToSend();
        Message m1_1 = client1.sendOp();

        server.enqueueReceivedOp(m1_1);
        server.applyReceivedOp();
        Message s1_1 = server.sendOp();
        assertEquals("a1b2c3", server.getDocument());

        client1.enqueueReceivedOp(s1_1);
        client1.applyReceivedOp();
        assertEquals("a1b2c3", client1.getDocument());

        client2.enqueueOpToSend();
        Message c2_1 = client2.sendOp();
        client2.enqueueReceivedOp(s1_1);
        client2.applyReceivedOp();
        assertEquals("1x2y3z", client2.getDocument());

        server.enqueueReceivedOp(c2_1);
        server.applyReceivedOp();
        Message s1_2 = server.sendOp();

        client1.enqueueReceivedOp(s1_2);
        client1.applyReceivedOp();

        client2.enqueueReceivedOp(s1_2);
        client2.applyReceivedOp();

        assertEquals(server.getDocument(), client1.getDocument());
        assertEquals(server.getDocument(), client2.getDocument());
    }

}
