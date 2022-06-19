package github.gamari.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
	
	
	public static String toString(Object o) {
		return ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
