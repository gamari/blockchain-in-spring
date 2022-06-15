package github.gamari.blockchain.domain;

import java.util.Date;

import javax.persistence.Entity;

import github.gamari.blockchain.logic.Algorithm;
import github.gamari.blockchain.logic.Sha256Algorithm;

/**
 * データと書名を格納する場所。
 */
@Entity
public class Block {
	/** 実データ。 */
	private String data;
	
	/** 書名を格納。 */
	private String hash;
	
	/**  */
	private String previousHash;
	
	private long timestamp;
	
	/** PoW用。 */
	private int nonce;
	
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timestamp = new Date().getTime();
		this.hash = hash();
	}
	
	/**
	 * データ、前回のハッシュ値、時間を格納する。
	 */
	public String hash() {
		Algorithm algorithm = new Sha256Algorithm();
		String hashValue = algorithm.createHash(previousHash + data + Long.toString(timestamp) + nonce);
		return hashValue;
	}
	
	public void mineBlock(int difficulty) {
		String targetHash = new String(new char[difficulty]).replace('\0', '0');
		while (!hash.substring(0, difficulty).equals(targetHash)) {
			nonce++;
			hash = hash();
		}
	}
	
	
	// getter setter

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
}
