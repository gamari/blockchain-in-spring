package github.gamari.blockchain.logic;

import java.security.MessageDigest;

public class Sha256Algorithm implements Algorithm {
	@Override
	public String createHash(String input) {
		try {
			// 
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes());
			byte[ ] cipherBytes = md.digest(); 
			
			// digestを16進数に直す
			StringBuffer hexBuf = new StringBuffer();
			for (byte b: cipherBytes) {
				// System.out.println(b);
				hexBuf.append(String.format("%02x", b&0xff));
			}
			
			return hexBuf.toString();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
	}
}
