package sut.extraTestsMutationCov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

public class MutationCoverageAddedTests {
	
	
	private static TST<Integer> i;
	
	@BeforeEach
	void init() {
		i = new TST<>();
		
		String[] data = {"o","ola","odado","dado","dador","dardo","palha","pala","porta","pae",
				"data","dita","oleo","oleado","dadora","ditado","da","foca", "faca", "fola"};
		
		for (int j = 0; j < data.length; j++) {
			i.put(data[j],j+1);
		}
	}
	
	@Test
	public void test666() {
		assertEquals((Integer)9,i.get("porta"));
	}
	
	@Test
	public void test66() {
		assertEquals("da",i.longestPrefixOf("dar"));
	}
	
	@Test
    public void test6666() {
		//pala -> pa.a -> 3 subreviveram
    	
    	ArrayList<String> a = new ArrayList<>();
    	ArrayList<String> b = new ArrayList<>(Arrays.asList("pala"));
        
    	
        i.keysThatMatch("pa.a").forEach(e -> a.add(e));
        
        for (String key : b) {
			if (!a.contains(key)) {
				fail("dont contain the same elements!");
			}
		}
		
		assertEquals(b.size(),a.size());
		
        
    }
	
	@Test
    public void test66666() {
		
		TST<Integer> iaux = new TST<>();
		iaux.put("ola", 1);
		iaux.put("fola", 2);
		
		
    	ArrayList<String> a = new ArrayList<>();
    	ArrayList<String> b = new ArrayList<>(Arrays.asList("ola"));
        
    	
        iaux.keysThatMatch("ola").forEach(e -> a.add(e));
        
        for (String key : b) {
			if (!a.contains(key)) {
				fail("dont contain the same elements!");
			}
		}
		
		assertEquals(b.size(),a.size());
		
        
    }
	
}
