package github.gamari.wallet.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.gamari.blockchain.controller.response.WalletPostResponse;
import github.gamari.wallet.domain.Wallet;


/**
 * ウォレットサーバーの代用。
 * 
 * 禁止
 * BlockChainクラス -> チェーンサーバーが保持するもの。
 * 
 */
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/wallet")
@RestController
public class WalletController {
	@PostMapping("")
	public WalletPostResponse createWallet() throws Exception {
		// TODO ログイン処理を作る際に新規作成フローを変える
		Wallet newWallet = new Wallet();

		WalletPostResponse response = new WalletPostResponse(
			newWallet.getPrivateKeyString(),
			newWallet.getPublicKeyString(), 
			newWallet.getBlockchainAddress()
		);

		return response;
	}
	

	// TODO ウォレットサーバーからチェーンサーバーに送信する。
//	@PostMapping("/transaction")
//	public WalletPostResponse createWalletTransaction() throws Exception {
//
//		return response;
//	}
}
