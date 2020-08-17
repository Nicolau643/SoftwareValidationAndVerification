package sut.properties;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.TST;

public class TreeGenerator extends Generator<TST<Integer>> {

	public TreeGenerator(Class<TST<Integer>> type) {
		super(type);
	}

	@Override
	public TST<Integer> generate(SourceOfRandomness random, GenerationStatus status) {
		
		TST<Integer> struct = new TST<>();
		
		int sizeOfTree = random.nextInt(200, 300);
		
		for (int i = 0; i < sizeOfTree/3; i++) {
			struct.put(extracted(random), i);
		}
		
		//por subs
		struct.put("subscritor", 301);
		struct.put("subtrair", 302);
		struct.put("sueco", 303);
		struct.put("sugar", 304);
		struct.put("sumo", 305);
		struct.put("suba", 306);
		struct.put("sufixo", 307);
		struct.put("suficiente", 308);
		struct.put("suborno", 309);
		struct.put("sugestao", 310);
		
		
		for (int i = sizeOfTree/3; i < (2*sizeOfTree)/3; i++) {
			struct.put(extracted(random), i);
		}
		
		//por subs
		struct.put("sufocou", 311);
		struct.put("sufixou", 312);
		struct.put("sucumbe", 313);
		struct.put("sucesso", 314);
		struct.put("sucedeu", 315);
		struct.put("subsolo", 316);
		struct.put("submete", 317);
		struct.put("sudoeste", 318);
		struct.put("submersa", 319);
		struct.put("sublinhe", 320);
		
		for (int i = (2*sizeOfTree)/3; i < sizeOfTree; i++) {
			struct.put(extracted(random), i);
		}
		
		
		return struct;
	}

	private String extracted(SourceOfRandomness random) {
		int sizeInput;
		StringBuilder str = new StringBuilder();
		sizeInput = random.nextInt(15) + 1;
		for (int j = 0; j < sizeInput; j++) {
			str.append(random.nextChar('a', 'z'));
		}
		
		return str.toString();
	}

}
