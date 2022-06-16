package github.gamari.blockchain.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import github.gamari.blockchain.logic.Algorithm;
import github.gamari.blockchain.logic.Sha256Algorithm;

/**
 * データと書名を格納する場所。
 */
@Entity
public class Block {
	
	private List<Transaction> transactions;
	private String hash;
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
	
	public void mineBlock(int difficulty) {
		String targetHash = new String(new char[difficulty]).replace('\0', '0');
		while (!hash.substring(0, difficulty).equals(targetHash)) {
			nonce++;
			hash = hash();
		}
	}
	

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("timestamp: " + this.timestamp + "\n");
		sb.append("nonce: " + this.nonce + "\n");
		sb.append("previousHash: " + this.previousHash+ "\n");
		sb.append("transactions: \n" + this.transactions.stream().map(t ->  t.toString()+"\n").collect(Collectors.joining()));
		return sb.toString();
	}
	
	// Getter Setter
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
}
