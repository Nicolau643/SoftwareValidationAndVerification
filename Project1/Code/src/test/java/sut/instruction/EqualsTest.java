package sut.instruction;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

public class EqualsTest {

	private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		
		String[] data = {"ola","odado","dado"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	/*
	 * I1 I2
	 */
	@Test
	void test1() {
		
		assertEquals(true,i.equals(i));
		
	}
	/*
	 * I1 I3
	 */
	@Test
	void test2() {
		
		assertEquals(false,i.equals(null));
		
	}
	/*
	 * I1 I3 I5 I6
	 */
	@Test
	void test3() {
		
		assertEquals(false,i.equals("false"));
		
	}
	/*
	 * I1 I3 I5 I7 I8 I9
	 */
	@Test
	void test4() {
		
		TST<Integer> other = new TST<>();
		other.put("key", 0);
		
		assertEquals(false,i.equals(other));
		
	}
	/*
	 * I1 I3 I5 I7 I8 I10 I11 I12 I13 I14 I15 I16
	 */
	@Test
	void test5() {
		
		TST<Integer> other = new TST<>();
		
		other.put("ola", 1);
		other.put("odado", 2);
		other.put("adeus", 3);
		
		
		assertEquals(false,i.equals(other));
		
	}
	/*
	 * I1 I3 I5 I7 I8 I10 I11 I12 I13 I14 I15 I17
	 */
	@Test
	void test6() {
		
		TST<Integer> other = new TST<>();
		
		other.put("ola", 1);
		other.put("odado", 2);
		other.put("dado", 3);
		
		
		assertEquals(true,i.equals(other));
		
	}
	
	
	
	
	
	
}
