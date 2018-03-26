package com.sidneynguyen.otjava;

import static org.junit.Assert.*;

import org.junit.Test;

public class GraphTest {

	@Test
	public void insertRightTest() {
		Transformer transformer = new DummyTransformer();
		Composer composer = new DummyComposer();
		KeyGenerator generator = new SimpleKeyGenerator();
		Graph graph = new Graph("0", transformer, composer, generator);
		graph.insertRight("1", "0", null);
		assertEquals("0\t1\t\n", graph.toString());
		
		graph.insertRight("2", "1", null);
		graph.insertRight("3", "2", null);
		assertEquals("0\t1\t2\t3\t\n", graph.toString());
	}

	@Test
	public void insertLeftTest() {
		Transformer transformer = new DummyTransformer();
		Composer composer = new DummyComposer();
		KeyGenerator generator = new SimpleKeyGenerator();
		Graph graph = new Graph("0", transformer, composer, generator);
		graph.insertLeft("1", "0", null);
		assertEquals("0\t\n1\t\n", graph.toString());

		graph.insertLeft("2", "1", null);
		graph.insertLeft("3", "2", null);
		assertEquals("0\t\n1\t\n2\t\n3\t\n", graph.toString());
	}

	@Test
	public void applyRightTest() {
		Transformer transformer = new DummyTransformer();
        Composer composer = new DummyComposer();
        KeyGenerator generator = new SimpleKeyGenerator();
        Graph graph = new Graph(generator.generateKey(), transformer, composer, generator);
        graph.insertLeft(generator.generateKey(), "0", null);
		graph.insertRight(generator.generateKey(), "0", null);
        assertEquals("0\t2\t\n1\t3\t\n", graph.toString());

        graph.insertRight(generator.generateKey(), "2", null);
        assertEquals("0\t2\t4\t\n1\t3\t5\t\n", graph.toString());
	}
}
