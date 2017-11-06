package ca.mcgill.ecse420.a2;

import java.util.Random;
import ca.mcgill.ecse420.a2.Lock.Lock;

public class SafeIncrementTask implements Runnable {
	Lock lock;

	private static Random rand = new Random();

	public SafeIncrementTask(Lock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < LockTester.incrementsPerThreads; i++) {
			lock.lock();
			LockTester.globalIndex++;
			// To help quickly see where the global Index is
			System.out.println(String.format("%" + LockTester.globalIndex + "s", "") + "|");
			// Wait for a random amount of time
			try {
				Thread.sleep(rand.nextInt(LockTester.WAITING_MS_BOUND));
			} catch (InterruptedException e) {
				System.out.println("Didn't work");
				e.printStackTrace();
			}
			lock.unlock();

		}

	}

}
