package github.gamari.blockchain.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Sha256AlgorithmTest extends Sha256Algorithm {

	@Test
	void 違う文字は違うハッシュ値になる() {
		Algorithm algorithm = new Sha256Algorithm();
		String str1 = algorithm.createHash("test");
		String str2 = algorithm.createHash("test1");
		
		// 9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08
		System.out.println(str1);
		
		assertNotEquals(str1, str2);
	}
	
	@Test
	void ハッシュ値は必ず同じになる() {
		Algorithm algorithm = new Sha256Algorithm();
		String str1 = algorithm.createHash("test");
		
		assertEquals(str1, "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
	}

	@Test
	void test() {
		
		
	}
}
