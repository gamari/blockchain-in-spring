package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import org.junit.jupiter.api.Test;

import github.gamari.utils.PrintUtil;

class TransactionTest {

	@Test
	void testTransaction() throws Exception {
		Wallet walletM = new Wallet();
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		Transaction t = new Transaction(
				walletA.getBlockchainAddress(), walletB.getBlockchainAddress(),
				walletA.getPublicKey(), walletA.getPrivateKey(), 
				new BigDecimal("1.1"));
		
		byte[] sign = t.generateSignature();

		BlockChain blockchain = BlockChain.getInstance(walletM.getBlockchainAddress());
		boolean isAdded = blockchain.addTransaction(t, sign);

		System.out.println(isAdded);

		blockchain.mining();
		
		blockchain.printChain();
		System.out.println(blockchain.calculateTotalAmount(walletA.getBlockchainAddress()));
		System.out.println(blockchain.calculateTotalAmount(walletB.getBlockchainAddress()));
	}

	@Test
	void testSignature() {
		/**
		 * キーペアを発行
		 */
		KeyPairGenerator keyPairGenerator = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("EC");
		} catch (NoSuchAlgorithmException ex) {
			return;
		}
		SecureRandom secureRandom = new SecureRandom();
		keyPairGenerator.initialize(256, secureRandom);
		KeyPair pair = keyPairGenerator.generateKeyPair();
		Key publicKey = pair.getPublic();
		Key privateKey = pair.getPrivate();

		// とりあえず、ただの文字列で
		String hako = "Qiitaは、プログラマのための技術情報共有サービスです。";

		/**
		 * 秘密鍵で署名を行う（ECDSA 160bit）
		 */
		byte[] sign = null;
		try {
			Signature signatureSign = null;
			signatureSign = Signature.getInstance("SHA1withECDSA");
			signatureSign.initSign((PrivateKey) privateKey, secureRandom);
			signatureSign.update(
					hako.getBytes()
				);
			sign = signatureSign.sign();

			System.out.println("sign: " + new String(sign));

		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException ex) {
		}

		/**
		 * 公開鍵で署名の検証を行う
		 */
		Signature signatureVerify = null;
		try {
			signatureVerify = Signature.getInstance("SHA1withECDSA");
			signatureVerify.initVerify((PublicKey) publicKey);
			signatureVerify.update(hako.getBytes());
			System.out.println("sign: " + new String(sign));
			boolean verifyResult = signatureVerify.verify(sign);
			System.out.println(verifyResult ? "署名OK" : "署名NG");

		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
		}
	}
	
	@Test
	void testStringbyte() {
		byte[] b = new byte[] {-1, 1, 10, 5, -10};
		String s = new String(b);
		System.out.println(s);
		
		byte[] bb = s.getBytes();
		PrintUtil.printBytes(b);
	}
}












