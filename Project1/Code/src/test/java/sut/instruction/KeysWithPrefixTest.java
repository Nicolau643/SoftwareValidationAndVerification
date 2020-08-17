package sut.instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sut.TST;

/**
 * Returns all of the keys in the set that start with {@code prefix}.
 * @param prefix the prefix
 * @return all of the keys in the set that start with {@code prefix},
 *     as an iterable
 * @throws IllegalArgumentException if {@code prefix} is {@code null}
 */
/*public Iterable<String> keysWithPrefix(String prefix) {
    if (prefix == null)
        throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
    Queue<String> queue = new LinkedList<>();
    Node<T> x = get(root, prefix, 0);
    if (x == null) 
    	return queue;
    if (x.val != null) 
    	queue.add(prefix);
    collect(x.mid, new StringBuilder(prefix), queue);
    return queue;
}*/

public class KeysWithPrefixTest {

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
        assertThrows(IllegalArgumentException.class, () ->{i.keysWithPrefix(null);});
    }
    
    /*
     * Requisitos de teste: I1, I3, I4, I5, I7, I9, I10
     */
    @Test
    void test2() {
        
    	ArrayList<String> a = new ArrayList<>();
    	ArrayList<String> b = new ArrayList<>(Arrays.asList("ola","oleo","oleado"));
        
    	
        i.keysWithPrefix("ol").forEach(e -> a.add(e));
        
        for (String key : b) {
			if (!a.contains(key)) {
				fail("dont contain the same elements!");
			}
		}
		
		assertEquals(b.size(),a.size());
		
    	
    }

    /*
     * Requisitos de teste: I1, I3, I4, I5, I6
     */
    @Test
    void test3() {
    	
    	ArrayList<String> a = new ArrayList<>();
    	ArrayList<String> b = new ArrayList<>(Arrays.asList());
        
    	i.keysWithPrefix("aka").forEach(e -> a.add(e));
		
		assertEquals(b.size(),a.size());
		
    }
    
    /*
     * Requisitos de teste: I1, I3, I4, I5, I7, I8, I9, I10
     */
    @Test
    void test4() {
    	
    	ArrayList<String> a = new ArrayList<>();
    	ArrayList<String> b = new ArrayList<>(Arrays.asList("dado","dador","dadora"));
        
    	i.keysWithPrefix("dado").forEach(e -> a.add(e));
		
    	for (String key : b) {
			if (!a.contains(key)) {
				fail("dont contain the same elements!");
			}
		}
    	
		assertEquals(b.size(),a.size());
		
    }
}
