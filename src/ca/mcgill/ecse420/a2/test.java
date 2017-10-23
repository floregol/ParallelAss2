package ca.mcgill.ecse420.a2;

public class test {

	private static int currentId = 0;
	private static ThreadLocal<Integer> thread = new ThreadLocal<Integer>();

	public static int get() {
		return thread.get();
	}

	public static void reset() {
		currentId = 0;
	}

	public static class ThreadLocalId extends ThreadLocal<Integer> {

		@Override
		protected synchronized Integer initialValue() {
			// TODO Auto-generated method stub
			return currentId++;
		}

	}

	static void main(String[] args) {

	}
}
