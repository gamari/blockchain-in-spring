package github.gamari.blockchain.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;

import github.gamari.blockchain.domain.Block;

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
		// 適当にテストしてみる
		List<Block> blockchain = new ArrayList<>();
		
		Block block1 = new Block("1", "0");
		block1.mineBlock(3);
		
		// genesis block
		blockchain.add(block1);
		blockchain.add(new Block("2", blockchain.get(blockchain.size() -1).getHash()));
		blockchain.add(new Block("3", blockchain.get(blockchain.size() -1).getHash()));
		
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(json);
		
		
	}
}
