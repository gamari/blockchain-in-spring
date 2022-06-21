package github.gamari.wallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import github.gamari.blockchain.logic.Logger;
import github.gamari.error.exception.ValidationException;
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
	public WalletTransactionPostResponse createWalletTransaction(@Validated @RequestBody WalletTransactionPostRequest request, BindingResult result) throws Exception {
		logger.info("", "/wallet/transaction", "POST");
		logger.info(request, "/wallet/transaction", "POST");
		
		if (result.hasErrors()) {
			// TODO AOPで共通処理を省きたい。
			// TODO エラーメッセージをバリデーション内容のものにする。
			throw new ValidationException("不正な値を入力しています。");
		}

		// ブロックチェーンサーバーにトランザクション作成処理を投げる。
		RestTemplate restTemplate = new RestTemplate();
		WalletTransactionPostResponse response = restTemplate.postForObject("http://localhost:8080/chain/transaction", request, WalletTransactionPostResponse.class);
		return response;
	}
}
