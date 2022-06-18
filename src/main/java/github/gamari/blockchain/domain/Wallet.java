package github.gamari.blockchain.domain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Wallet {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private String blockchainAddress;

	public Wallet() throws Exception {
		// 1. 楕円曲線でキーを作る
		KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
		SecureRandom secureRandom = new SecureRandom();
		generator.initialize(256, secureRandom);
		KeyPair keyPair = generator.generateKeyPair();
		publicKey = keyPair.getPublic();
		privateKey = keyPair.getPrivate();
		
		this.blockchainAddress = generateBlockchainAddress();

	}

	/**
	 * 擬似的なアドレスを生成する。
	 * 若干、ビットコインのフロートは違うことに注意。
	 * 
	 * @return ウォレットアドレス。
	 */
	public String generateBlockchainAddress()
			throws NoSuchAlgorithmException, NoSuchProviderException, DecoderException {
		// 2. pubkeyをsha256で変換する
		// pubkey -> byte[] ->sha256 -> byte[]-digest
		byte[] publicKeyBytes = this.publicKey.getEncoded();
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(publicKeyBytes);
		byte[] sha256PublicKeyDigest = md.digest();

		// 3.
		// byte[] -> ripemd160 -> byte[] -> hex-string
		Security.addProvider(new BouncyCastleProvider());
		md = MessageDigest.getInstance("RIPEMD160", "BC");
		md.update(sha256PublicKeyDigest);
		byte[] ripemd160PublicKeyDigest = md.digest();

		// 4.
		byte[] networkByte = new byte[] { 0, 0 };
		byte[] networkBitcoinPublicKey = new byte[networkByte.length + ripemd160PublicKeyDigest.length];
		System.arraycopy(networkByte, 0, networkBitcoinPublicKey, 0, networkByte.length);
		System.arraycopy(ripemd160PublicKeyDigest, 0, networkBitcoinPublicKey, networkByte.length,
				ripemd160PublicKeyDigest.length);

		// 5.
		md = MessageDigest.getInstance("SHA-256");
		byte[] sha256NetworkBitcoinDigest = md.digest(networkBitcoinPublicKey);
		byte[] sha256NetworkBitcoinDigest2 = md.digest(sha256NetworkBitcoinDigest);
		String sha256NetworkBitcoinHex = new String(Hex.encodeHex(sha256NetworkBitcoinDigest2));

		// 6. check sum
		char[] checkSum = sha256NetworkBitcoinHex.substring(0, 8).toCharArray();

		// 7.
		// networkBitcoinPublicKey-byte[] + checkSum
		// byte -> string
		char[] left = Hex.encodeHex(networkBitcoinPublicKey);
		char[] right = checkSum;
		char[] addressChars = new char[left.length + right.length];
		System.arraycopy(left, 0, addressChars, 0, left.length);
		System.arraycopy(right, 0, addressChars, left.length, right.length);
		byte[] addressHex = Hex.decodeHex(addressChars);

		// 8.
		// ビットコインではBas58を利用している
		String blockchainAddress = Base64.getEncoder().encodeToString(addressHex);

		return blockchainAddress;
	}

	// Getter Setter
	public String getBlockchainAddress() {
		return blockchainAddress;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

}
