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
import github.gamari.blockchain.controller.response.WalletPostResponse;
import github.gamari.blockchain.domain.BlockChain;
import github.gamari.blockchain.domain.Transaction;
import github.gamari.blockchain.domain.Wallet;
import github.gamari.blockchain.logic.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RestController
public class BlockchainController {
	private final Logger logger = Logger.getInstance();

	@GetMapping("/chain")
	public ChainResponse getChain() {
		logger.info("チェーンGET-API", "GET", "/api/chain");
		BlockChain blockchain = BlockChain.getInstance();
		return new ChainResponse(blockchain.getChain());
	}

	@GetMapping("/transaction")
	public TransactionResponse getTransaction() {
		logger.info("トランザクションGET-API", "GET", "/api/transaction");

		BlockChain bc = BlockChain.getInstance();
		TransactionResponse response = new TransactionResponse(bc.getTransactions(), bc.getTransactions().size());

		return response;
	}

	@PostMapping("/transaction")
	public TransactionPostResponse createTransaction(@RequestBody TransactionRequest request) {
		logger.info("トランザクションPOST-API", "/api/transaction", "POST");

		try {
			BlockChain bc = BlockChain.getInstance();
			Transaction transaction = new Transaction(request.getSenderBlockchainAddress(),
					request.getRecipientBlockchainAddress(), request.getSenderPublicKey(),
					request.getSenderPrivateKey(), request.getValue());

			// TODO walletサーバーに「transactionデータ」と「sign」を送る。
			// 今回はwalletサーバー無しで考える（書き換えはないので必ず通る）
			byte[] sign = transaction.generateSignature();
			boolean isSuccess = bc.createTransaction(transaction, sign);
			return new TransactionPostResponse(isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			return new TransactionPostResponse(false);
		}
	}

	@PostMapping("/wallet")
	public WalletPostResponse createWallet() throws Exception {
		// TODO ログイン処理を作る際に新規作成フローを変える
		Wallet newWallet = new Wallet();

		WalletPostResponse response = new WalletPostResponse(newWallet.getPrivateKeyString(),
				newWallet.getPublicKeyString(), newWallet.getBlockchainAddress());

		return response;
	}

	@GetMapping("/mining")
	public MiningResponse mining() {
		logger.info("マイニングAPI", "/api/mining", "GET");

		BlockChain bc = BlockChain.getInstance();
		boolean isMine = bc.mining();
		
		return new MiningResponse(isMine);
	}
	
	@GetMapping("/amount")
	public AmountResponse amount(@RequestParam("address") String address) {
		BlockChain bc = BlockChain.getInstance();
		BigDecimal amount = bc.calculateTotalAmount(address);
		
		return new AmountResponse(amount.toString());
	}
}
