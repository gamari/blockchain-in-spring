package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * 取引履歴。
 *
 */
public class Transaction {
	private String senderAddress;
	private String recipientAddress;
	private PublicKey senderPublicKey;
	private PrivateKey senderPrivateKey;
	private BigDecimal value;

	public Transaction(String senderAddress, String recipientAddress, PublicKey senderPublicKey,
			PrivateKey senderPrivateKey, BigDecimal value) {
		this.senderAddress = senderAddress;
		this.recipientAddress = recipientAddress;
		this.senderPublicKey = senderPublicKey;
		this.senderPrivateKey = senderPrivateKey;
		this.value = value;
	}

	public byte[] generateSignature() {
		try {
			// messageにサインする
			Signature sig = Signature.getInstance("SHA1withECDSA");
			sig.initSign(senderPrivateKey);
			sig.update(getTransactionMessage().getBytes());
			byte[] sign = sig.sign();

			return sign;
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[] {};
		}
	}
	
	public boolean verifyTransactionSignature(byte[] signature) {
		try {
			String originalText = this.getTransactionMessage();
			Signature verify = Signature.getInstance("SHA1withECDSA");
			verify.initVerify(this.getSenderPublicKey());
			verify.update(originalText.getBytes());
			return verify.verify(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 取引履歴を識別するもの。
	 * 署名時に利用する。
	 */
	public String getTransactionMessage() {
		return senderAddress + recipientAddress + value;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("  senderAddress: " + this.senderAddress + "\n");
		sb.append("  recipientAddress: " + this.recipientAddress + "\n");
		sb.append("  value: " + this.value + "\n");
		return sb.toString();
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public PublicKey getSenderPublicKey() {
		return senderPublicKey;
	}
}
