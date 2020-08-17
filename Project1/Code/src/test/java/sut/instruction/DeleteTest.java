package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

public class DeleteTest {

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
	 * I1 I2 I3
	 */
	@Test
	public void test1() {
		assertThrows(IllegalArgumentException.class, () -> {i.delete("");});

	}
	/*
	 * I1 I2 I4 I5
	 */
	@Test
	public void test2() {
		assertThrows(IllegalArgumentException.class, () -> {i.delete("zeca");});

	}
	/*
	 * I1 I2 I4 I6 I7
	 */
	@Test
	public void test3() {
		i.delete("oleado");
		
		assertEquals(null,i.get("oleado"));
	}
	
	
}
