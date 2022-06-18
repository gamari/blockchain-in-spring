package github.gamari.blockchain.domain;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

class WalletTest  {

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
            	for (Service s: services) {
            		System.out.println(s.getAlgorithm());
            	}
            }
            
        }
        
	}

}
