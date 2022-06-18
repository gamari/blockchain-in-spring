package github.gamari.utils;

/**
 * 表示関係をサポートするクラス。
 */
public class PrintUtil {

	public static void printBytes(byte[] bytes) {
		System.out.println("\n----Bytes----");
		for (byte b: bytes) {
			System.out.print(b + " ");
		}
		System.out.println("\n----Bytes----\n");
	}
	
//	public static void printBytesToHex(byte[] bytes) {
//		
//	}
}
