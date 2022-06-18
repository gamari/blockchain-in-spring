package github.gamari.blockchain.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class TransactionTest {

	@Test
	void トランザクションの署名を同一のトランザクションがVerifyできる() throws Exception {
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		Transaction t = new Transaction(
				walletA.getBlockchainAddress(), 
				walletB.getBlockchainAddress(),
				walletA.getPublicKey(), 
				walletA.getPrivateKey(), 
				new BigDecimal("1.1"));
		
		byte[] sign = t.generateSignature();
		boolean isVerify = t.verifyTransactionSignature(sign);
		
		assertTrue(isVerify);
	}
	
	@Test
	void トランザクションの署名を別のトランザクションはVerify不可() throws Exception {
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		Transaction t = new Transaction(
				walletA.getBlockchainAddress(), 
				walletB.getBlockchainAddress(),
				walletA.getPublicKey(), 
				walletA.getPrivateKey(), 
				new BigDecimal("1.1"));
		
		Transaction t2 = new Transaction(
				walletA.getBlockchainAddress(), 
				walletB.getBlockchainAddress(),
				walletA.getPublicKey(), 
				walletA.getPrivateKey(), 
				new BigDecimal("1000000000"));
		
		byte[] sign = t2.generateSignature();
		boolean isVerify = t.verifyTransactionSignature(sign);
		
		assertFalse(isVerify);
	}

	
}












