package github.gamari.wallet.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import github.gamari.utils.PrintUtil;
import lombok.Data;

@Data
public class WalletTransactionPostRequest {
	@Positive(message="0より大きい数字を入れてください。")
	String value;
	
	String senderPublicKey;
	String senderPrivateKey;
	
	@NotEmpty(message="値がNULLまたは空白です。")
	@NotNull(message="値がNullです。")
	String senderBlockchainAddress;
	
	@NotEmpty(message="値がNULLまたは空白です。")
	@NotNull(message="値がNullです。")
	String recipientBlockchainAddress;
	
	@Override
	public String toString() {
		return PrintUtil.toString(this);
	}
}
