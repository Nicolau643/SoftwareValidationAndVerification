package sut.edge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

class Edge {
	
	
	private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		String[] data = {"ola","dado","dador","dardo","palha","pala","porta",
				"data","dita","oleo","oleado","dadora","ditado"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	@Test
	void testInputNull() {
		
		assertThrows(IllegalArgumentException.class, () ->{i.longestPrefixOf(null);});
	}
	
	@Test
	void testInputLengthZero() {
		
		assertEquals(null, i.longestPrefixOf(""));
	}
	
	@Test
	void test1() {
		
		assertEquals("dita",i.longestPrefixOf("ditad"));
	}

}
