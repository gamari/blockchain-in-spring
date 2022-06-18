package github.gamari.blockchain.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.stream.Collectors;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.domain.BlockChain;
import github.gamari.blockchain.domain.Transaction;
import github.gamari.blockchain.domain.Wallet;
import github.gamari.blockchain.logic.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;

@RequestMapping("/api")
@RestController
public class BlockchainController {
	private final Logger logger = Logger.getInstance();

	@GetMapping("/chain")
	public List<?> getChain() {
		BlockChain blockchain = BlockChain.getInstance();
		return blockchain.getChain();
	}

	@GetMapping("/transactions")
	public TransactionsResponse transactions() {
		logger.info("GET /transactions");
		BlockChain bc = BlockChain.getInstance();

		logger.info(bc.getTransactionPool());

		// レスポンス用に加工
		List<?> ret = bc.getTransactionPool().stream().map(t -> {
			String template = "[recipient: %s, sender: %s, value: %s]";
			return String.format(template, t.getRecipientAddress(), t.getSenderAddress(), t.getValue());
		}).collect(Collectors.toList());

		TransactionsResponse response = new TransactionsResponse(ret, bc.getTransactionPool().size());

		return response;
	}

	@Data
	@AllArgsConstructor
	public static class TransactionsResponse {
		List<?> transactions;
		int length;
	}

	public static class TransactionRequest implements Serializable {
		private static final long serialVersionUID = 1L;
		BigDecimal value;
		String senderPublicKey;
		String senderPrivateKey;
		String senderBlockchainAddress;
		String recipientBlockchainAddress;
		
		public String toString() {
			return String.format("[\nvalue: %d\n]", value.toString());
		}
		
		// Setter Getter
		public void setValue(String value) {
			this.value = new BigDecimal(value);
		}
		
		public PrivateKey getSenderPrivateKey() throws Exception {
			KeyFactory kf = KeyFactory.getInstance("EC");
			byte[] bytes = Hex.decode(senderPrivateKey);
			KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			PrivateKey privateKey = kf.generatePrivate(keySpec);

			return privateKey;
		}
		
		public PublicKey getSenderPublicKey() throws Exception {
			KeyFactory kf = KeyFactory.getInstance("EC");
			byte[] bytes = Hex.decode(senderPublicKey);
			KeySpec keySpec = new X509EncodedKeySpec(bytes);
			PublicKey publicKey = kf.generatePublic(keySpec);

			System.out.println(new String(publicKey.getEncoded(), "utf-8"));
			return publicKey;
		}
		
		public BigDecimal getValue() {
			return value;
		}
		
		public String getSenderBlockchainAddress() {
			return senderBlockchainAddress;
		}
		
		public String getRecipientBlockchainAddress() {
			return recipientBlockchainAddress;
		}
	}

	@PostMapping("/transaction")
	public String createTransaction(@RequestBody TransactionRequest request) {
		logger.info("POST /api/transactions");
		logger.info(request.getValue());

		BlockChain bc = BlockChain.getInstance();
		
		try {
			Transaction transaction = new Transaction(
					request.getSenderBlockchainAddress(), 
					request.getRecipientBlockchainAddress(),
					request.getSenderPublicKey(),
					request.getSenderPrivateKey(),
					request.getValue()
				);
			byte[] sign = transaction.generateSignature();
			boolean isSuccess = bc.createTransaction(transaction, sign);

			if (isSuccess) {
				return "SUCCESS";
			} else {
				return "FALL";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}

	}

	private Transaction dummyTransaction() throws Exception {
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		Transaction t = new Transaction(walletA.getBlockchainAddress(), walletB.getBlockchainAddress(),
				walletA.getPublicKey(), walletA.getPrivateKey(), new BigDecimal("1.1"));
		return t;
	}

}
