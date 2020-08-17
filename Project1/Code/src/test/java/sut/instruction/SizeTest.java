package sut.instruction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;


/**
 * Returns the number of key-value pairs in this symbol table.
 * @return the number of key-value pairs in this symbol table
 */
/*public int size() {
    return n;
}*/

public class SizeTest {
	
	private static TST<Integer> n;
	
	@BeforeEach
	void init() {
		n = new TST<>();

		String[] data = {"ola","odado","dado","dador"};
		
		for (int j = 0; j < data.length; j++) {
			n.put(data[j],j+1);
		}
	}
	
	/*
	 * Requisitos de teste: I1
	 */
	@Test
	void test1() {
		
		assertEquals(4,n.size());
	}
}
