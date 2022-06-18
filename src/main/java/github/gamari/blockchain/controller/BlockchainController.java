package github.gamari.blockchain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.controller.request.TransactionRequest;
import github.gamari.blockchain.controller.response.TransactionPostResponse;
import github.gamari.blockchain.controller.response.TransactionResponse;
import github.gamari.blockchain.domain.BlockChain;
import github.gamari.blockchain.domain.Transaction;
import github.gamari.blockchain.logic.Logger;

@RequestMapping("/api")
@RestController
public class BlockchainController {
	private final Logger logger = Logger.getInstance();

	@GetMapping("/chain")
	public List<String> getChain() {
		BlockChain blockchain = BlockChain.getInstance();

		List<String> result = blockchain.getChain().stream().map(item -> {
			return item.toString();
		}).collect(Collectors.toList());

		return result;
	}

	@GetMapping("/transaction")
	public TransactionResponse transactions() {
		logger.info("トランザクションGET-API", "GET", "/api/transaction");

		BlockChain bc = BlockChain.getInstance();

		// レスポンス用に加工
		List<String> ret = bc.getTransactions().stream().map(t -> {
			String template = "[recipient: %s, sender: %s, value: %s]";
			return String.format(template, t.getRecipientAddress(), t.getSenderAddress(), t.getValue());
		}).collect(Collectors.toList());

		TransactionResponse response = new TransactionResponse(ret, bc.getTransactions().size());

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

	@GetMapping("/mining")
	public String mining() {
		logger.info("マイニングAPI", "/api/mining", "GET");

		BlockChain bc = BlockChain.getInstance();
		boolean isMine = bc.mining();

		if (isMine) {
			return "Success";
		} else {
			return "Fail";
		}
	}
}
