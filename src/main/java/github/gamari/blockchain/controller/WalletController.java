package github.gamari.blockchain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.domain.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;

@RequestMapping("/api")
@RestController
public class WalletController {

	@PostMapping("/wallet")
	public WalletResponse createWallet() throws Exception {
		Wallet wallet = new Wallet();

		WalletResponse response = new WalletResponse(
				new String(wallet.getPrivateKey().getEncoded()),
				new String(wallet.getPublicKey().getEncoded()), 
				wallet.getBlockchainAddress());

		return response;

	}

	@AllArgsConstructor
	@Data
	public static class WalletResponse {
		String privateKey;
		String publicKey;
		String blockchainAddress;
	}
}
