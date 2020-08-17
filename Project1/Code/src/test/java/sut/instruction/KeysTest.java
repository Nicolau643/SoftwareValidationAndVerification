package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * Returns all keys in the symbol table as an {@code Iterable}.
 * To iterate over all of the keys in the symbol table named {@code st},
 * use the foreach notation: {@code for (Key key : st.keys())}.
 * @return all keys in the symbol table as an {@code Iterable}
 */
/*public Iterable<String> keys() {
    Queue<String> queue = new LinkedList<>();
    collect(root, new StringBuilder(), queue);
    return queue;
}*/

public class KeysTest {
	private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		
		String[] data = {"ola","odado","dado","dador","dardo","palha","pala","porta"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	/*
     * Requisitos de teste: I1, I2
     */
	/*@Test
	void testWithNullArgument() {

		assertThrows(IllegalArgumentException.class, () ->{i.keys();});

	}*/
	
	/*
     * Requisitos de teste: I1, I2, I3
     */
	@Test
	void test1() {
		
		String[] dataTest = {"ola","odado","dado","dador","dardo","palha","pala","porta"};
		ArrayList<String> a = new ArrayList<>();
		
		i.keys().forEach(e -> {a.add(e);});
		
		for (String key : dataTest) {
			if (!a.contains(key)) {
				fail("dont contain the same elements!");
			}
		}
		
		assertEquals(dataTest.length,a.size());
		
		
	}
}
