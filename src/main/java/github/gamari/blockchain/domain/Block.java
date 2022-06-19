package github.gamari.blockchain.domain;

import java.util.Date;
import java.util.List;

import github.gamari.blockchain.logic.Algorithm;
import github.gamari.blockchain.logic.Sha256Algorithm;
import github.gamari.utils.PrintUtil;

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
		return PrintUtil.toString(this);
	}

	// Getter Setter
	public List<Transaction> getTransactions() {
		return transactions;
	}

}
