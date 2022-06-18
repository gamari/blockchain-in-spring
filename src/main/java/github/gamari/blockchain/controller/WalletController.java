package github.gamari.blockchain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.domain.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;

@RequestMapping("/api/wallet")
@RestController
public class WalletController {

	@PostMapping("/")
	public WalletResponse createWallet() throws Exception {
		Wallet wallet = new Wallet();

		WalletResponse response = new WalletResponse(
				wallet.getPrivateKeyString(),
				wallet.getPublicKeyString(), 
				wallet.getBlockchainAddress());

		return response;
	}
	
	@PostMapping("/transaction")
	public String createTransaction() {
		// TODO jsonで取得する
		// TODO 本来は、ウォレットサーバー<->チェーンサーバー間でポストを投げる
//		Transaction transaction = new Transaction();
		
		return "";
	}
	

	@AllArgsConstructor
	@Data
	public static class WalletResponse {
		String privateKey;
		String publicKey;
		String blockchainAddress;
	}
}
