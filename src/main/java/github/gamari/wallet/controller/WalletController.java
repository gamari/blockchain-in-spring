package github.gamari.wallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import github.gamari.blockchain.logic.Logger;
import github.gamari.wallet.controller.request.WalletTransactionPostRequest;
import github.gamari.wallet.controller.response.WalletPostResponse;
import github.gamari.wallet.controller.response.WalletTransactionPostResponse;
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
	private final Logger logger = Logger.getInstance();
	
	@PostMapping("")
	public WalletPostResponse createWallet() throws Exception {
		Wallet newWallet = new Wallet();

		WalletPostResponse response = new WalletPostResponse(
			newWallet.getPrivateKeyString(),
			newWallet.getPublicKeyString(), 
			newWallet.getBlockchainAddress()
		);

		return response;
	}
	

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/transaction")
	public WalletTransactionPostResponse createWalletTransaction(@RequestBody WalletTransactionPostRequest request) throws Exception {
		logger.info("", "/wallet/transaction", "POST");
		// TODO requestのバリデーションをかける

		// ブロックチェーンサーバーにトランザクション作成処理を投げる。
		RestTemplate restTemplate = new RestTemplate();
		WalletTransactionPostResponse response = restTemplate.postForObject("http://localhost:8080/chain/transaction", request, WalletTransactionPostResponse.class);
		return response;
	}
}
