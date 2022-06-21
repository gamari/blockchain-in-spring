package github.gamari.blockchain.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.controller.request.TransactionRequest;
import github.gamari.blockchain.controller.response.AmountResponse;
import github.gamari.blockchain.controller.response.ChainResponse;
import github.gamari.blockchain.controller.response.MiningResponse;
import github.gamari.blockchain.controller.response.TransactionPostResponse;
import github.gamari.blockchain.controller.response.TransactionResponse;
import github.gamari.blockchain.domain.BlockChain;
import github.gamari.blockchain.domain.Transaction;
import github.gamari.blockchain.logic.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/chain")
@RestController
public class BlockchainController {
	private final Logger logger = Logger.getInstance();

	@GetMapping("")
	public ChainResponse getChain() {
		logger.info("チェーンGET-API", "GET", "/chain");
		BlockChain blockchain = BlockChain.getInstance();
		System.out.println(blockchain.getChain());
		return new ChainResponse(blockchain.getChain());
	}

	@GetMapping("/transaction")
	public TransactionResponse getTransaction() {
		logger.info("トランザクションGET-API", "GET", "/chain/transaction");

		BlockChain bc = BlockChain.getInstance();
		TransactionResponse response = new TransactionResponse(bc.getTransactions(), bc.getTransactions().size());

		return response;
	}

	@PostMapping("/transaction")
	public TransactionPostResponse createTransaction(@RequestBody TransactionRequest request) {
		logger.info("トランザクションPOST-API", "/chain/transaction", "POST");

		try {
			BlockChain bc = BlockChain.getInstance();
			Transaction transaction = new Transaction(
					request.getSenderBlockchainAddress(),
					request.getRecipientBlockchainAddress(), 
					request.getSenderPublicKey(),
					request.getSenderPrivateKey(), 
					request.getValue());

			byte[] sign = transaction.generateSignature();
			boolean isSuccess = bc.createTransaction(transaction, sign);
			logger.info(isSuccess, "/chain/transaction", "POST");
			logger.info("トランザクションPOST-API END", "/chain/transaction", "POST");
			return new TransactionPostResponse(isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			return new TransactionPostResponse(false);
		}
	}

	@GetMapping("/mining")
	public MiningResponse mining() {
		logger.info("マイニングAPI", "/chain/mining", "GET");

		BlockChain bc = BlockChain.getInstance();
		boolean isMine = bc.mining();
		
		return new MiningResponse(isMine);
	}
	
	@GetMapping("/amount")
	public AmountResponse amount(@RequestParam("address") String address) {
		logger.info("残高API", "/chain/amount", "GET");
		
		BlockChain bc = BlockChain.getInstance();
		BigDecimal amount = bc.calculateTotalAmount(address);
		
		return new AmountResponse(amount.toString());
	}
}
