package github.gamari.blockchain.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BlockChainTest extends BlockChain {

	@Test
	void test() {
		BlockChain bc = new BlockChain();
		
		// block1
		bc.addTransaction("A", "B", new BigDecimal(1.0));
		int nonce = bc.proofOfWork();
		bc.createBlock(nonce, bc.previousHash());

		// block2
		bc.addTransaction("C", "D", new BigDecimal(3.5));
		bc.addTransaction("X", "Y", new BigDecimal(8.9));
		nonce = bc.proofOfWork();
		bc.createBlock(nonce, bc.previousHash());
		
		// block3
		nonce = bc.proofOfWork();
		bc.createBlock(nonce, bc.previousHash());

		bc.printChain();
	}

}
