package ca.mcgill.ecse420.a2;

import java.util.function.Supplier;
import ca.mcgill.ecse420.a2.Lock.BakeryLock;
import ca.mcgill.ecse420.a2.Lock.FilterLock;
import ca.mcgill.ecse420.a2.Lock.Lock;

public class LockTester {

	static int globalIndex = 1;
	static int numberOfThreads;
	static final int INCREMENTS_PER_THREADS = 15;
	static final int WAITING_MS_BOUND = 100;

	/*
	 * Function that takes the constructor of a runnable, create some and test if
	 * it's works in parallel.
	 */
	public static boolean testFunction(Supplier<Runnable> incrementRunnable, String runnableMethod) {

		Thread[] pool = new Thread[numberOfThreads];
		globalIndex = 1;

		System.out.println();
		System.out.println();
		System.out.println(runnableMethod);

		for (int i = 0; i < numberOfThreads; i++) {// Creates the thread and start them
			pool[i] = new Thread(incrementRunnable.get());
			pool[i].start();
		}

		for (Thread thread : pool) { // Wait for everyone to finish
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int expectedOutput = numberOfThreads * INCREMENTS_PER_THREADS + 1;

		System.out.println("Expected output : " + expectedOutput);
		System.out.println("Obtained output: " + globalIndex);
		System.out.println();
		System.out.println("--------------------------------------");

		return expectedOutput == globalIndex;
	}

	public static void main(String[] args) {

		numberOfThreads = 3;
		//First test the increment without locking to ensure that we can get race conditions
		testFunction(() -> new UnSafeIncrementTask(), "Unsafe methode");
		//Test with the FilterLock implementation
		Lock filterLock = new FilterLock(numberOfThreads);
		testFunction(() -> new SafeIncrementTask(filterLock), "Safe method with FilterLock");
		//Test with the BakeryLock implementation
		Lock bakeryLock = new BakeryLock(numberOfThreads);
		testFunction(() -> new SafeIncrementTask(bakeryLock), "Safe method with BakeryLock");

	}

}
