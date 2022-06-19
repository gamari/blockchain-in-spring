package github.gamari.blockchain.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import github.gamari.blockchain.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
	@JsonProperty("transactions")
	List<Transaction> transactions;
	
	@JsonProperty("length")
	int length;
}