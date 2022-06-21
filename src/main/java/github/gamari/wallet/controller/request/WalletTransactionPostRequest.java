package github.gamari.wallet.controller.request;

import java.io.Serializable;

import github.gamari.utils.PrintUtil;
import lombok.Data;

@Data
public class WalletTransactionPostRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String value;
	String senderPublicKey;
	String senderPrivateKey;
	String senderBlockchainAddress;
	String recipientBlockchainAddress;
	
	@Override
	public String toString() {
		return PrintUtil.toString(this);
	}
}
