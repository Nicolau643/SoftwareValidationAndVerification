package sut.auc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

public class AllUsesCoverage {
	
private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		String[] data = {"om", "mj", "omj", "om", "omo", "da", 
				"moda", "ol", "la", "ola","ov", "va", "ova"};
		
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
	void test3() {
		assertEquals("",new TST<>().longestPrefixOf("ola"));	
	}
	@Test
	void test4() {
		i.put("ola",2);
		i.put("moda",4);
		assertEquals("",i.longestPrefixOf("m"));	
	}
	@Test
	void test5() {
		assertEquals("",i.longestPrefixOf("o"));	
	}
	@Test
	void test6() {
		assertEquals("",i.longestPrefixOf("v"));
	}	
	@Test
	void test7() {
		i.put("j",3);
		assertEquals("",i.longestPrefixOf("m"));
	}
	@Test
	void test8() {
		assertEquals("",i.longestPrefixOf("n"));
	}
	@Test
	void test9() {
		i.put("o", 17);
		assertEquals("ol",i.longestPrefixOf("ol"));
	}
	@Test
	void test10() {
		assertEquals("",i.longestPrefixOf("ox"));
	}
	@Test
	void test11() {
		i.put("va", 6);
		assertEquals("",i.longestPrefixOf("v"));
	}
	@Test
	void test12() {
		assertEquals("",i.longestPrefixOf("x"));
	}
	@Test
	void test13() {
		i.put("o", 1);
		assertEquals("o",i.longestPrefixOf("o"));
	}
	@Test
	void test14() {
		i.put("j",9);
		assertEquals("j",i.longestPrefixOf("j"));
	}
	@Test
	void test15() {
		i.put("m", 2);
		assertEquals("m",i.longestPrefixOf("m"));
	}
	@Test
	void test16() {
		assertEquals("",i.longestPrefixOf("oi"));
	}
	@Test
	void test17() {
		assertEquals("",i.longestPrefixOf("o"));
	}
	@Test
	void test18() {
		i.put("v",2);
		assertEquals("ov",i.longestPrefixOf("ov"));
	}
	@Test
	void test19() {
		assertEquals("",i.longestPrefixOf("p"));
	}
	@Test
	void test20() {
		assertEquals("",i.longestPrefixOf("v"));
	}
	
	
	
	

}
