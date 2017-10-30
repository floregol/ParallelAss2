package ca.mcgill.ecse420.a2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ca.mcgill.ecse420.a2.Lock.FilterLock;
import ca.mcgill.ecse420.a2.Lock.Lock;
import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class LockTester {
	static int globalIndex;

	public static void main(String[] args) {

		int numberOfThreads = 2;

		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		globalIndex = 1;
		System.out.println("Unsafe method");
		for (int i = 0; i < numberOfThreads; i++) {
			executor.execute(new UnsafeIncrementTask());
		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS); // wait for it to finish
		} catch (InterruptedException e) {

		}

		System.out.println("Expected output : " + (numberOfThreads * 10 + 1));
		System.out.println("Obtained output: " + globalIndex);
		System.out.println();
		System.out.println("--------------------------------------");
		System.out.println();
		System.out.println();
		System.out.println("Safe method with FilterLock");
		Lock lockFilter = new FilterLock(numberOfThreads);
		ThreadID.reset();
		globalIndex = 1;

		ExecutorService executor2 = Executors.newFixedThreadPool(numberOfThreads);
		globalIndex = 1;
		for (int i = 0; i < numberOfThreads; i++) {
			executor2.execute(new SafeIncrementTask(lockFilter));
		}

		executor2.shutdown();
		try {
			executor2.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS); // wait for it to finish
		} catch (InterruptedException e) {

		}
		System.out.println("Expected output : " + (numberOfThreads * 10 + 1));
		System.out.println("Obtained output: " + globalIndex);
		System.out.println();
		System.out.println("--------------------------------------");
		System.out.println();
	}

}
