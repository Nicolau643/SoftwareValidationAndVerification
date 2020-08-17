package sut.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import sut.TST;


@RunWith(JUnitQuickcheck.class)
public class Properties {

	
	@Property
	public void test1(
			@From(TreeGenerator.class) TST<Integer> tree) {
			
		
		ArrayList<String> al = iteratorToArray(tree.keys().iterator());
		
		Collections.shuffle(al);
		
		TST<Integer> other = new TST<>();
		al.forEach(e -> {
			other.put(e, tree.get(e));
		});
		
		assertEquals("not equal",tree,other);
	}
	
	@Property
	public <T> void test2(
			@From(TreeGenerator.class) TST<T> tree) {
		
		TST<T> tree2 = tree;
		ArrayList<String> al = iteratorToArray(tree.keys().iterator());
		
		al.forEach(e -> {
			tree2.delete(e);
		});
		
		assertEquals("not empty",0,tree2.size());
		
	}
	
	@Property
	public <T> void test3(
			@From(TreeGenerator.class) TST<T> tree) {
		
		Iterator<String> key = tree.keys().iterator();
		
		TST<T> other = new TST<>();
		
		String s = "";
		while (key.hasNext()) {
			s = key.next();
			other.put(s, tree.get(s));
		}
		
		other.delete(s);
		
		other.put(s, tree.get(s));
		
		assertEquals("not equal",tree,other);
		
		
	}
	
	@Property
	public <T> void test4(
			@From(TreeGenerator.class) TST<T> tree) {
		
		boolean res = true;
		
		ArrayList<String> a1 = iteratorToArray(tree.keysWithPrefix("sub").iterator());
		ArrayList<String> b1 = iteratorToArray(tree.keysWithPrefix("su").iterator());
		
		System.out.println(a1);
		System.out.println(b1);
		
		for (String s : a1) {
			if (!b1.contains(s)) {
				res = false;
				break;
			}
		}
		
		assertTrue(res);
		
	}
	
	
	/**
	 * 
	 * @param it
	 * @return
	 */
	private ArrayList<String> iteratorToArray(Iterator<String> it){
		
		ArrayList<String> a = new ArrayList<>();
		
		while(it.hasNext()) {
			a.add(it.next());	
		}
		
		return a;
	}
	
	
}
