package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BlockChain {

	List<Transaction> transactionPool;
	List<Block> chain;
	
	public BlockChain() {
		this.transactionPool = new ArrayList<Transaction>();
		this.chain = new ArrayList<Block>();
		this.createBlock(0, "init hash");
	}
	
	public Block createBlock(int nonce, String previousHash) {
		Block newBlock = new Block(previousHash, nonce, new Date(), transactionPool);
		this.chain.add(newBlock);
		transactionPool = new ArrayList<Transaction>();
		
		return newBlock;
	}
	
	public String previousHash() {
		return chain.get(chain.size()-1).hash();
	}
	
	public boolean addTransaction(String sender, String recipient, BigDecimal value) {
		Transaction transaction = new Transaction(sender, recipient, value);
		this.transactionPool.add(transaction);
		return true;
	}
	
	/**
	 * 前回のブロック情報から解を求める。
	 * この「解」は 
	 */
	public int proofOfWork() {
		int nonce = 0;
		String previousHash = this.previousHash();
		while (!this.validProof(this.transactionPool, previousHash, nonce, 3)) {
			nonce++;
		}
		
		return nonce;
	}
	
	public boolean validProof(List<Transaction> transactions, String previousHash, int nonce, int difficulty) {
		Block guessBlock = new Block(previousHash, nonce, null, transactions);
		String guessHash = guessBlock.hash();
		
		// TODO ロジックを変更する
		String diff = guessHash.substring(0, difficulty);
		boolean ret = Arrays.stream(diff.split("")).allMatch(s -> s.equals("0"));
		
		return ret;
	}
	
	public void printChain() {
		int count = 0;
		System.out.println("*********************");
		for (Block block: chain) {
			System.out.println(String.format("----- Chain %d -----", count));
			System.out.println(block);
			System.out.println("TransactionPool: ");
			count++;
		}
		System.out.println("*********************");
		System.out.println();
	}
	
	// Getter Setter
	public List<Block> getChain() {
		return chain;
	}
	
	
}
