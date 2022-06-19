package github.gamari.blockchain.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WalletPostResponse {
	private String privateKey;
	private String publicKey;
	private String blockchainAddress;
}
