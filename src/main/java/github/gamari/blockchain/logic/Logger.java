package github.gamari.blockchain.logic;

/**
 * カスタムロガー。
 * とりあえず、Sysoutする。
 */
public class Logger {

	private static Logger logger;
	
	private Logger() {}
	
	public static Logger getInstance() {
		if (logger == null) {
			logger = new Logger();
		}
		
		return logger;
	}
	
	public void info(Object o) {
		System.out.println(o);
	}
}
