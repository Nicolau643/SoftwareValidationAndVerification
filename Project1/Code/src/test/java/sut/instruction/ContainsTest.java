package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

import sut.TST;

import org.junit.jupiter.api.Test;

/**
 * Does this symbol table contain the given key?
 * @param key the key
 * @return {@code true} if this symbol table contains {@code key} and
 *     {@code false} otherwise
 * @throws IllegalArgumentException if {@code key} is {@code null}
 */
/*public boolean contains(String key) {
    if (key == null) 
        throw new IllegalArgumentException("argument to contains() is null");
    return get(key) != null;
}*/

public class ContainsTest {
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
		
		assertThrows(IllegalArgumentException.class, () -> {i.contains(null);});
	}
	
	/*
	 * Requisitos de teste: I1, I3
	 */
	@Test
	void test2() {
		
		assertEquals(true,i.contains("dado"));
	}
}
