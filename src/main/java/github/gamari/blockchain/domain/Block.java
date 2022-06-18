package github.gamari.blockchain.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import github.gamari.blockchain.logic.Algorithm;
import github.gamari.blockchain.logic.Sha256Algorithm;

/**
 * データと書名を格納する場所。
 */
public class Block {
	private List<Transaction> transactions;
	private String previousHash;
	private Date timestamp;

	/** PoW用。 */
	private int nonce;

	public Block(String previousHash, int nonce, Date timestamp, List<Transaction> transactions) {
		this.timestamp = timestamp;
		this.transactions = transactions;
		this.previousHash = previousHash;
		this.nonce = nonce;
	}

	public String hash() {
		Algorithm algorithm = new Sha256Algorithm();
		String hashValue = algorithm.createHash(previousHash + nonce + this.timestamp + this.transactions.toString());
		return hashValue;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{'timestamp': " + this.timestamp);
		sb.append(", 'nonce': " + this.nonce);
		sb.append(", 'previousHash': " + this.previousHash);
		sb.append(
				", 'transactions': " + this.transactions.stream().map(t -> t.toString()).collect(Collectors.joining()));
		sb.append("}");
		return sb.toString();
	}

	// Getter Setter
	public List<Transaction> getTransactions() {
		return transactions;
	}

}
