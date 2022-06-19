package github.gamari.blockchain.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import github.gamari.blockchain.domain.Block;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChainResponse {
	@JsonProperty("chain")
	List<Block> chain;
}
