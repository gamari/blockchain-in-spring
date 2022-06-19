package github.gamari.blockchain.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AmountResponse {
	@JsonProperty("amount")
	String amount;
}
