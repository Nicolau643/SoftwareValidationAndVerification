package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * Inserts the key-value pair into the symbol table, overwriting the old value
 * with the new value if the key is already in the symbol table.
 * If the value is {@code null}, this effectively deletes the key from the symbol table.
 * @param key the key
 * @param val the value
 * @throws IllegalArgumentException if {@code key} is {@code null}
 */
/*public void put(String key, T val) {
    if (key == null)
        throw new IllegalArgumentException("calls put() with null key");
    if (!contains(key)) 
    	n++;
    root = put(root, key, val, 0);
}*/

public class PutTest {
	
	private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		
		String[] data = {"ola","odado","dado","dador","dardo","palha","pala","porta",
				"data","dita","oleo","oleado","dadora","ditado"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	/*
	 * Requisitos de teste: I1, I2
	 */
	@Test
	void test1() {
		assertThrows(IllegalArgumentException.class, () ->{i.put(null, 2);});
	}
	
	/*
	 * Requisitos de teste: I1, I3, I4, I5
	 */
	@Test
	void test2() {
		i.put("bye", 2);
		
		assertEquals((Integer)2,i.get("bye"));
		
	}
	
	
}
