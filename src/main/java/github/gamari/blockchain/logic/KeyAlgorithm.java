package github.gamari.blockchain.logic;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Hex;

/**
 * キー作成のアルゴリズムに関して。
 *
 */
public class KeyAlgorithm {
	public static PublicKey convertStringToPublicKey(String publicKeyString) throws Exception {
		KeyFactory kf = KeyFactory.getInstance("EC");
		byte[] bytes = Hex.decode(publicKeyString);
		KeySpec keySpec = new X509EncodedKeySpec(bytes);
		PublicKey publicKey = kf.generatePublic(keySpec);
		return publicKey;
	}

	public static PrivateKey convertStringToPrivateKey(String privateKeyString) {
		try {

			KeyFactory kf = KeyFactory.getInstance("EC");
			byte[] bytes = Hex.decode(privateKeyString);
			KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			PrivateKey privateKey = kf.generatePrivate(keySpec);

			return privateKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
