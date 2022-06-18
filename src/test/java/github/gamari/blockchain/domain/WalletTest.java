package github.gamari.blockchain.domain;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

class WalletTest {

	@Test
	void test() throws Exception {
		Wallet wallet = new Wallet();
		String address = wallet.generateBlockchainAddress();
		System.out.println(address);
	}

//	@Test
	void providerTest() {
		// 暗号化に関するプロバイダーとアルゴリズム名を確認したいときに利用する
		Security.addProvider(new BouncyCastleProvider());
		Provider[] providers = Security.getProviders();
		for (int i = 0; i != providers.length; i++) {
			Provider provider = providers[i];

			if (provider.getName().equals("BC")) {
				Set<Service> services = providers[i].getServices();
				for (Service s : services) {
					System.out.println(s.getAlgorithm());
				}
			}

		}

	}

	@Test
	void testKeyGenerator() throws Exception {
		try {
			Wallet wallet = new Wallet();

			KeyFactory kf = KeyFactory.getInstance("EC");
			
			byte[] bytes = Hex.decode(wallet.getPrivateKeyString());
			System.out.println(new String(bytes, "utf-8"));
			
			// key.encoded <=これを入れれば良い
			KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			PrivateKey privateKey = kf.generatePrivate(keySpec);

			System.out.println(new String(privateKey.getEncoded(), "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
