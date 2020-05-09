package utils;

public class Utils {

	public static int log2(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		return 31 - Integer.numberOfLeadingZeros(n);
	}

	public static int log2(double f) {
		return (int) Math.floor(Math.log(f) / Math.log(2.0));
	}
}
