package github.gamari.blockchain.controller.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
	List<?> transactions;
	int length;
}