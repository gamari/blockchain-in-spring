package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import github.gamari.blockchain.logic.Logger;

public class BlockChain {

	private static final String BLOCKCHAIN_NETWORK_ADDRESS = "BLOCKCHAIN_NETWORK";
	private static final BigDecimal REWORDS = new BigDecimal("1.0");
	private static Logger logger = Logger.getInstance();

	private static BlockChain blockchain;
	
	List<Transaction> transactionPool;
	List<Block> chain;
	String minerAddress;

	private BlockChain(String minerAddress) {
		this.transactionPool = new ArrayList<Transaction>();
		this.chain = new ArrayList<Block>();
		this.createBlock(0, "init hash");
		this.minerAddress = minerAddress;
	}
	
	public static BlockChain getInstance(String minerAddress) {
		// TODO DBから取得するようにする。
		if (blockchain == null) {
			try {
				Wallet minerWallet = new Wallet();
				blockchain = new BlockChain(minerWallet.getBlockchainAddress());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info(blockchain);
		
		return blockchain;
	}

	public Block createBlock(int nonce, String previousHash) {
		Block newBlock = new Block(previousHash, nonce, new Date(), transactionPool);
		this.chain.add(newBlock);
		transactionPool = new ArrayList<Transaction>();

		return newBlock;
	}

	public String previousHash() {
		return chain.get(chain.size() - 1).hash();
	}

	/**
	 * トランザクションプールにトランザクションを追加する。
	 * 
	 * @param transaction
	 * @param signature
	 * @return
	 */
	public boolean addTransaction(Transaction transaction, byte[] signature) {
		String senderAddress = transaction.getSenderAddress();
		
		if (isMinerAddress(senderAddress)) {
			this.transactionPool.add(transaction);
			return true;
		}

		if (verifyTransactionSignature(transaction, signature)) {
			// TODO マイナスを判定する処理
//			if (calculateTotalAmount(senderAddress).compareTo(transaction.getValue())  < 0) {
//				System.out.println("MINUS");
//				return false;
//			}
			
			this.transactionPool.add(transaction);
			return true;
		}

		return false;
	}
	
	private boolean isMinerAddress(String senderAddress) {
		return senderAddress.equals(BLOCKCHAIN_NETWORK_ADDRESS);
	}

	public boolean verifyTransactionSignature(Transaction transaction, byte[] signature) {
		try {
			String originalText = transaction.getTransactionMessage();
			Signature verify = Signature.getInstance("SHA1withECDSA");
			verify.initVerify(transaction.getSenderPublicKey());
			verify.update(originalText.getBytes());
			return verify.verify(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 前回のブロック情報から解を求める。 この「解」は
	 */
	private int proofOfWork() {
		int nonce = 0;
		String previousHash = this.previousHash();
		while (!this.validProof(this.transactionPool, previousHash, nonce, 3)) {
			nonce++;
		}

		return nonce;
	}

	private boolean validProof(List<Transaction> transactions, String previousHash, int nonce, int difficulty) {
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
		
		Transaction transaction = new Transaction(BLOCKCHAIN_NETWORK_ADDRESS, minerAddress, null, null, REWORDS);
		this.addTransaction(transaction, null);
		int nonce = this.proofOfWork();
		String previousHash = this.previousHash();
		this.createBlock(nonce, previousHash);
		return true;
	}

	public BigDecimal calculateTotalAmount(String address) {
		BigDecimal total = new BigDecimal("0.0");
		for (Block block : this.chain) {
			for (Transaction transaction : block.getTransactions()) {

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
		for (Block block : chain) {
			System.out.println(String.format("----- Chain %d -----", count));
			System.out.println(block);
			System.out.println("TransactionPool: ");
			count++;
		}
		System.out.println("*********************");
		System.out.println();
	}
	
	@Override
	public String toString() {
		String template = "{chain: \n%s, \nminerAddress: \n%s}"; 
		return String.format(template, chain.toString(), minerAddress);
	}

	// Getter Setter
	public List<Block> getChain() {
		return chain;
	}

}
