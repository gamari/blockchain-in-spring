package github.gamari.blockchain.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import github.gamari.utils.PrintUtil;

public class BlockChain {
	public static final String BLOCKCHAIN_NETWORK_ADDRESS = "BLOCKCHAIN_NETWORK";
	private static BlockChain blockchain;
	private static final BigDecimal REWORDS = new BigDecimal("1.0");

	private List<Block> chain;
	private String minerAddress;
	private Wallet wallet;
	private TransactionPool pool;

	private BlockChain(String minerAddress, Wallet wallet) {
		this.pool = new TransactionPool();
		this.chain = new ArrayList<Block>();
		this.createBlock(0, "init hash");
		this.minerAddress = minerAddress;
		this.wallet = wallet;
	}

	public static BlockChain getInstance() {
		if (blockchain == null) {
			try {
				// TODO Wallet情報をDBから取得するようにする。
				Wallet minerWallet = new Wallet();
				blockchain = new BlockChain(minerWallet.getBlockchainAddress(), minerWallet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return blockchain;
	}

	private Block createBlock(int nonce, String previousHash) {
		Block newBlock = new Block(previousHash, nonce, new Date(), this.pool.getTransactions());
		this.chain.add(newBlock);
		this.pool.clear();
		return newBlock;
	}

	private String previousHash() {
		return chain.get(chain.size() - 1).hash();
	}

	public boolean createTransaction(Transaction transaction, byte[] signature) {
		boolean isCreated = this.pool.addTransaction(transaction, signature,
				isChainAddress(transaction.getSenderAddress()));

		// TODO 他サーバーと同期処理を行う

		return isCreated;
	}

	private boolean isChainAddress(String senderAddress) {
		return senderAddress.equals(BLOCKCHAIN_NETWORK_ADDRESS);
	}

	/**
	 * 前回のブロック情報から解を求める。 この「解」は
	 */
	private int proofOfWork() {
		int nonce = 0;
		String previousHash = this.previousHash();
		while (this.inValidProof(this.pool.getTransactions(), previousHash, nonce, 3)) {
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
	
	private boolean inValidProof(List<Transaction> transactions, String previousHash, int nonce, int difficulty) {
		return this.validProof(transactions, previousHash, nonce, difficulty);
	}

	public boolean mining() {
		if (this.pool.size() == 0) {
			return false;
		}

		Transaction transaction = new Transaction(BLOCKCHAIN_NETWORK_ADDRESS, minerAddress, wallet.getPublicKey(),
				wallet.getPrivateKey(), REWORDS);
		this.pool.addTransaction(transaction, null, isChainAddress(BLOCKCHAIN_NETWORK_ADDRESS));
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
		System.out.println("---------Chain---------");
		this.chain.stream().forEach(item -> {
			System.out.println(ToStringBuilder.reflectionToString(item, ToStringStyle.MULTI_LINE_STYLE));
		});
		System.out.println("---------Chain---------\n\n");
	}

	@Override
	public String toString() {
		return PrintUtil.toString(this);
	}

	// Getter Setter
	public List<Block> getChain() {
		return chain;
	}

	public List<Transaction> getTransactions() {
		return this.pool.getTransactions();
	}

	public String getMinerAddress() {
		return minerAddress;
	}
}
