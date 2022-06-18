package github.gamari.blockchain.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.controller.response.Greeting;
import github.gamari.blockchain.domain.BlockChain;
import github.gamari.blockchain.domain.Transaction;
import github.gamari.blockchain.logic.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;

@RequestMapping("/api")
@RestController
public class BlockchainController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final Logger logger = Logger.getInstance();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/chain")
	public String getChain() {
		BlockChain blockchain = BlockChain.getInstance();
		return "";
	}

	@GetMapping("/transactions")
	public TransactionsResponse transactions() {
		BlockChain bc = BlockChain.getInstance();

		TransactionsResponse response = new TransactionsResponse(bc.getTransactionPool(),
				bc.getTransactionPool().size());

		return response;
	}

	@Data
	@AllArgsConstructor
	public static class TransactionsResponse {
		List<Transaction> transactions;
		int length;
	}
}
