package github.gamari.blockchain.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BlockChainTest extends BlockChain {

	@Test
	void test() {
		BlockChain bc = new BlockChain();
		bc.addTransaction("A", "B", new BigDecimal(1.0));
		bc.createBlock(2, bc.previousHash());
		bc.printChain();

		bc.addTransaction("C", "D", new BigDecimal(3.5));
		bc.addTransaction("X", "Y", new BigDecimal(8.9));
		bc.createBlock(3, bc.previousHash());
		bc.printChain();

	}

}
