package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * Returns the value associated with the given key.
 * @param key the key
 * @return the value associated with the given key if the key is in the symbol table
 *     and {@code null} if the key is not in the symbol table
 * @throws IllegalArgumentException if {@code key} is {@code null}
 */
/*public T get(String key) {
    if (key == null)
        throw new IllegalArgumentException("calls get() with null argument");
    if (key.length() == 0) 
    	throw new IllegalArgumentException("key must have length >= 1");
    Node<T> x = get(root, key, 0);
    if (x == null) 
    	return null;
    return x.val;
}*/

public class GetTest {
	
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
		
		assertThrows(IllegalArgumentException.class, () ->{i.get(null);});
	}
	
	/*
	 * Requisitos de teste: I1, I3, I4
	 */
	@Test
	void test2() {
		
		assertThrows(IllegalArgumentException.class, () ->{i.get("");});
	}
	
	/*
	 * Requisitos de teste: I1, I3, I5, I6, I8
	 */
	@Test
	void test3() {
		
		assertEquals((Integer)3,i.get("dado"));
	}
	
	/*
	 * Requisitos de teste: I1, I3, I5, I6, I7
	 */
	@Test
	void test4() {
		
		assertEquals(null,i.get("adeus"));
	}
}
