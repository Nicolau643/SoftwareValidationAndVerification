package sut.isp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * 
 * @author 
 * Base Choice -> (F,T,F)
 */
public class InputStatePartitioning {
	
	private static TST<Integer> i;
	
	@BeforeEach
	public void init() {
		i = new TST<>();
	}

	/**
	 * (F,T,F)
	 */
	@Test
	public void test1() {
		//state
		i.put("oleado", 1);
		
		//test
		i.put("oleo", 2);
		assertEquals((Integer)2, i.get("oleo"));
	}
	/**
	 * (T,T,F)
	 */
	@Test
	public void test2() {
		//state
		i.put("oleo", 2);
		i.put("foca", 3);
		i.put("faca", 4);
		i.put("jogo", 5);
		i.put("jarra", 6);
		
		
		//test
		i.put("jarra", 7);
		assertEquals((Integer)7, i.get("jarra"));
	}
	/**
	 * (F,F,F)
	 */
	@Test
	public void test3() {
		
		//state
		i.put("oleo", 2);
		i.put("foca", 3);
		i.put("faca", 4);
		i.put("jogo", 5);
		i.put("jarra", 6);
		
		//test
		i.put("rato", 7);
		assertEquals((Integer)7, i.get("rato"));
	}
	
	
}
