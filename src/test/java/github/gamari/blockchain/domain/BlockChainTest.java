package github.gamari.blockchain.domain;

import org.junit.jupiter.api.Test;

class BlockChainTest {

	@Test
	void test() {
		String minerAddress = "MINER_A";
		BlockChain bc = new BlockChain(minerAddress);
		
		// block1
		bc.mining();

		bc.printChain();
		
		System.out.println(bc.calculateTotalAmount(minerAddress));
		System.out.println(bc.calculateTotalAmount("C"));
	}

}
