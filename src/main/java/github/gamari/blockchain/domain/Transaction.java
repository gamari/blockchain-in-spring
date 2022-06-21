package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import com.fasterxml.jackson.annotation.JsonIgnore;

import github.gamari.utils.PrintUtil;

/**
 * 取引履歴。
 *
 */
public class Transaction {
	private String senderAddress;
	private String recipientAddress;
	private BigDecimal value;
	
	@JsonIgnore
	private PublicKey senderPublicKey;
	
	@JsonIgnore
	private PrivateKey senderPrivateKey;
	

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
		return PrintUtil.toString(this);
	}


	// Getter Setter
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
