package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BlockChain {
	
	private static final String BLOCKCHAIN_NETWORK_ADDRESS = "BLOCKCHAIN_NETWORK";
	private static final BigDecimal REWORDS = new BigDecimal("1.0");

	List<Transaction> transactionPool;
	List<Block> chain;
	String minerAddress;
	
	public BlockChain(String minerAddress) {
		this.transactionPool = new ArrayList<Transaction>();
		this.chain = new ArrayList<Block>();
		this.createBlock(0, "init hash");
		this.minerAddress = minerAddress;
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
	
	public boolean mining() {
		if (this.transactionPool.size() == 0) {
			return false;
		}
		
		this.addTransaction(BLOCKCHAIN_NETWORK_ADDRESS, minerAddress, REWORDS);
		int nonce = this.proofOfWork();
		String previousHash = this.previousHash();
		this.createBlock(nonce, previousHash);
		return true;
	}
	
	public BigDecimal calculateTotalAmount(String address) {
		BigDecimal total = new BigDecimal("0.0");
		for (Block block: this.chain) {
			for (Transaction transaction: block.getTransactions()) {
				
				if (address.equals(transaction.getRecipientAddress())) {
					total = total.add(transaction.getValue());
				}
				
				if (address.equals(transaction.getSenderAddress())) {
					total = total.subtract(transaction.getValue());
				}
			}
		}
		return total;
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
