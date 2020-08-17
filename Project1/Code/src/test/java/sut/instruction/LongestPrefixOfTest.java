package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * Returns the string in the symbol table that is the longest prefix of {@code query},
 * or {@code null}, if no such string.
 * @param query the query string
 * @return the string in the symbol table that is the longest prefix of {@code query},
 *     or {@code null} if no such string
 * @throws IllegalArgumentException if {@code query} is {@code null}
 */
/*public String longestPrefixOf(String query) {
    if (query == null)
        throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
    if (query.length() == 0) 
    	return null;
    int length = 0;
    Node<T> x = root;
    int i = 0;
    while (x != null && i < query.length()) {
        char c = query.charAt(i);
        if      (c < x.c) x = x.left;
        else if (c > x.c) x = x.right;
        else {
            i++;
            if (x.val != null) 
            	length = i;
            x = x.mid;
        }
    }
    return query.substring(0, length);
}*/

public class LongestPrefixOfTest {

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
        assertThrows(IllegalArgumentException.class, () ->{i.longestPrefixOf(null);});
    }
    
    /*
	 * Requisitos de teste: I1, I3, I4
	 */
	@Test
	void test2() {
		
		assertEquals(null,i.longestPrefixOf(""));
	}
    
    /*
     * Requisitos de teste: I1, I3, I5, I6, I7, I8, I9, I10, I11, I12, I13,
     *  I14, I15, I16, I17, I18, I19
     */
    @Test
    void test3() {
        
    	assertEquals("dardo",i.longestPrefixOf("dardo"));
    }
    
    /*
     * Requisitos de teste: I1, I3, I5, I6, I7, I8, I9, I10, I11, I13, I15, I16, I18, I19
     */
    /*@Test
    void test4() {
        
        assertEquals("",i.longestPrefixOf("ova"));
    }*/
   /*
    @Test
    void test3() {
        
        assertEquals("dardo",i.longestPrefixOf("dardo"));
    }*/
}
