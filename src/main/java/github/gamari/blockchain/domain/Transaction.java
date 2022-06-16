package github.gamari.blockchain.domain;

import java.math.BigDecimal;

/**
 * 取引履歴。
 *
 */
public class Transaction {
	String senderAddress;
	String recipientAddress;
	BigDecimal value;
	
	public Transaction(String senderAddress, String recipientAddress, BigDecimal value) {
		this.senderAddress = senderAddress;
		this.recipientAddress = recipientAddress;
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("  senderAddress: " + this.senderAddress + "\n");
		sb.append("  recipientAddress: " + this.recipientAddress + "\n");
		sb.append("  value: " + this.value + "\n");
		return sb.toString();
	}
}
