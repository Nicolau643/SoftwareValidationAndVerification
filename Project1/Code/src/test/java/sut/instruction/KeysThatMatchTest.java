package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * Returns all of the keys in the symbol table that match {@code pattern},
 * where . symbol is treated as a wildcard character.
 * @param pattern the pattern
 * @return all of the keys in the symbol table that match {@code pattern},
 *     as an iterable, where . is treated as a wildcard character.
 */
/*public Iterable<String> keysThatMatch(String pattern) {
    Queue<String> queue = new LinkedList<>();
    collect(root, new StringBuilder(), 0, pattern, queue);
    return queue;
}*/

public class KeysThatMatchTest {
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
    /*@Test
    void testWithNullArgument() {
        assertThrows(IllegalArgumentException.class, () ->{i.keysWithPrefix(null);});
    }
    */
    /*
     * Requisitos de teste: I1, I2, I3
     */
    @Test
    void test1() {
    	
    	ArrayList<String> a = new ArrayList<>();
    	ArrayList<String> b = new ArrayList<>(Arrays.asList("dado","data","dita"));
        
    	
        i.keysThatMatch("d...").forEach(e -> a.add(e));
        
        for (String key : b) {
			if (!a.contains(key)) {
				fail("dont contain the same elements!");
			}
		}
		
		assertEquals(b.size(),a.size());
		
        
    }
}
