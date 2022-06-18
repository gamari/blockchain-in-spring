package github.gamari.blockchain.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BlockChainTest {

	@Test
	void 合計金額が適切に計算されていること() throws Exception {
		BlockChain bc = BlockChain.getInstance();

		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		Transaction t = new Transaction(walletA.getBlockchainAddress(), walletB.getBlockchainAddress(),
				walletA.getPublicKey(), walletA.getPrivateKey(), new BigDecimal("123.123"));

		bc.addTransaction(t, t.generateSignature());
		bc.mining();
		bc.addTransaction(t, t.generateSignature());
		bc.mining();
		bc.addTransaction(t, t.generateSignature());
		bc.mining();
		
		// 3回マイニングしてるため
		assertEquals(bc.calculateTotalAmount(bc.getMinerAddress()), new BigDecimal("3.0"));
		assertEquals(bc.calculateTotalAmount(walletA.getBlockchainAddress()), new BigDecimal("-369.369"));
		assertEquals(bc.calculateTotalAmount(walletB.getBlockchainAddress()), new BigDecimal("369.369"));
	}
	
	@Test
	void ブロックの作成が成功すること() {
		BlockChain bc = BlockChain.getInstance();
	}
	
	
	@Test
	void printChainの表示確認用() throws Exception {
		BlockChain bc = BlockChain.getInstance();

		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		Transaction t = new Transaction(walletA.getBlockchainAddress(), walletB.getBlockchainAddress(),
				walletA.getPublicKey(), walletA.getPrivateKey(), new BigDecimal("1.1"));

		bc.addTransaction(t, t.generateSignature());
		bc.mining();
		bc.addTransaction(t, t.generateSignature());
		bc.mining();
		bc.addTransaction(t, t.generateSignature());
		bc.mining();
		bc.printChain();
		
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println(bc);
	}
	
	
	
	// TODO マイナス値を入れられないように修正する
//	@Test
//	void マイナス値は計算されないこと
	

}
