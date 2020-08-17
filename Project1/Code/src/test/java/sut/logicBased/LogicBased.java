package sut.logicBased;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

public class LogicBased {
	
	private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		String[] data = {"om", "ome", "omen"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	@Test
	void test1() {
		assertThrows(IllegalArgumentException.class, () ->{i.longestPrefixOf(null);});
	}
	@Test
	void test2() {
		assertEquals(null, i.longestPrefixOf(""));
	}
	@Test
	void test3() {  //1ª clause não passa, 2ªpassa
		assertEquals("",new TST<>().longestPrefixOf("ola"));	
	}
	@Test
	void test4() {
		assertEquals("",i.longestPrefixOf("a"));	
	}
	@Test
	void test5() {
		assertEquals("",i.longestPrefixOf("r"));	
	}
	@Test
	void test6() {
		assertEquals("om",i.longestPrefixOf("om"));	
	}
}
