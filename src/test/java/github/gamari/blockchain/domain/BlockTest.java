package github.gamari.blockchain.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class BlockTest {

	@Test
	void 一文字違うだけでハッシュ値は違う() {
		Block block1 = new Block("block", 10, new Date(), new ArrayList<Transaction>());
		Block block2 =  new Block("block2", 10, new Date(), new ArrayList<Transaction>());
		Block block3 =  new Block("block", 11, new Date(), new ArrayList<Transaction>());
		
		assertNotEquals(block1.hash(), block2.hash());
		assertNotEquals(block1.hash(), block3.hash());
	}
	
	@Test
	void 同じデータのブロックからは同じハッシュ値() {
		Date d = new Date();
		Block block1 = new Block("block", 10, d, new ArrayList<Transaction>());
		Block block2 = new Block("block", 10, d, new ArrayList<Transaction>());
		
		assertEquals(block1.hash(), block2.hash());
		
	}

}
