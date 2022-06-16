package github.gamari.blockchain.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BlockChainTest {

	@Test
	void test() {
		String minerAddress = "MINER_A";
		BlockChain bc = new BlockChain(minerAddress);
		
		// block1
		bc.addTransaction("A", "B", new BigDecimal("1.0"));
		bc.mining();

		// block2
		bc.addTransaction("C", "D", new BigDecimal("3.5"));
		bc.addTransaction("X", "Y", new BigDecimal("8.8"));
		bc.mining();
		
		// block3
		bc.mining();

		bc.printChain();
		
		System.out.println(bc.calculateTotalAmount(minerAddress));
		System.out.println(bc.calculateTotalAmount("C"));
	}

}
