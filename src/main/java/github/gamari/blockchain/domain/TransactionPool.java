package github.gamari.blockchain.domain;

import java.util.ArrayList;
import java.util.List;

public class TransactionPool {
	private List<Transaction> pool;
	
	public TransactionPool() {
		this.pool = new ArrayList<Transaction>();
	}
	
	/**
	 * プールにトランザクションを追加する。
	 * サインの判定も行う。
	 * 
	 * @param transaction
	 * @param signature
	 * @return
	 */
	public boolean addTransaction(Transaction transaction, byte[] signature, boolean isMiner) {
		if (isMiner) {
			this.pool.add(transaction);
			return true;
		}

		if (transaction.verifyTransactionSignature(signature)) {
			// TODO マイナスを判定する処理
//			if (calculateTotalAmount(senderAddress).compareTo(transaction.getValue())  < 0) {
//				System.out.println("MINUS");
//				return false;
//			}

			this.pool.add(transaction);
			return true;
		}

		return false;
	}
	
	public boolean addTransaction(Transaction transaction, byte[] signature) {
		return this.addTransaction(transaction, signature, false);
	}
	
	public void add(Transaction transaction) {
		this.pool.add(transaction);
	}
	
	public int size() {
		return pool.size();
	}
	
	public void clear() {
		this.pool = new ArrayList<Transaction>();
	}

	// GetterSetter
	public List<Transaction> getTransactions() {
		return pool;
	}
}
