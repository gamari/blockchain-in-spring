package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
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
