package github.gamari.wallet.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WalletPostResponse {
	private String privateKey;
	private String publicKey;
	private String blockchainAddress;
}
