package sut.prime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

public class Prime {
	
private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		String[] data = {"ola","dado","dador","dardo","palha","pala","porta",
				"daco","dita","oleo","oleado","dadora","ditado","outro","d",
				"foca","faca","da"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	/**
	 * 
	 */
	@Test
	void test1() {
		assertThrows(IllegalArgumentException.class, () ->{i.longestPrefixOf(null);});
	}
	@Test
	void test2() {
		assertEquals(null, i.longestPrefixOf(""));
	}
	@Test
	void test3() {
		assertEquals("",new TST<>().longestPrefixOf("abc"));	
	}
	@Test
	void test4() {
		assertEquals("",i.longestPrefixOf("zoo"));	
	}
	@Test
	void test5() {
		assertEquals("",i.longestPrefixOf("ol"));	
	}
	@Test
	void test6() {
		assertEquals("",i.longestPrefixOf("pa"));
	}
	@Test
	void test7() {
		assertEquals("",i.longestPrefixOf("ou"));
	}
	@Test
	void test8() {
		assertEquals("d",i.longestPrefixOf("d"));
	}
	@Test
	void test9() {
		i.put("o", 17);
		assertEquals("o",i.longestPrefixOf("obra"));
	}
	@Test
	void test10() {
		i.put("p", 30);
		assertEquals("p",i.longestPrefixOf("paralelo"));
	}
	@Test
	void test11() {
		i.put("o", 17);
		assertEquals("o",i.longestPrefixOf("ova"));
	}
	@Test
	void test12() {
		assertEquals("",i.longestPrefixOf("fato"));
	}
	@Test
	void test13() {
		i.put("ol", 21);
		assertEquals("ola",i.longestPrefixOf("ola"));
	}
	@Test
	void test14() {
		assertEquals("da",i.longestPrefixOf("daa"));
	}
	
}
