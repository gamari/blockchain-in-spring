package github.gamari.blockchain.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Hex;

import github.gamari.utils.PrintUtil;

public class TransactionRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	BigDecimal value;
	String senderPublicKey;
	String senderPrivateKey;
	String senderBlockchainAddress;
	String recipientBlockchainAddress;
	
	@Override
	public String toString() {
		return PrintUtil.toString(this);
	}

	// Setter Getter
	public void setValue(String value) {
		this.value = new BigDecimal(value);
	}
	
	public PrivateKey getSenderPrivateKey() throws Exception {
		KeyFactory kf = KeyFactory.getInstance("EC");
		byte[] bytes = Hex.decode(senderPrivateKey);
		KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		PrivateKey privateKey = kf.generatePrivate(keySpec);

		return privateKey;
	}
	
	public PublicKey getSenderPublicKey() throws Exception {
		KeyFactory kf = KeyFactory.getInstance("EC");
		byte[] bytes = Hex.decode(senderPublicKey);
		KeySpec keySpec = new X509EncodedKeySpec(bytes);
		PublicKey publicKey = kf.generatePublic(keySpec);

		System.out.println(new String(publicKey.getEncoded(), "utf-8"));
		return publicKey;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public String getSenderBlockchainAddress() {
		return senderBlockchainAddress;
	}
	
	public String getRecipientBlockchainAddress() {
		return recipientBlockchainAddress;
	}
}