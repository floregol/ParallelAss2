package ca.mcgill.ecse420.a2;

import java.util.Random;

import ca.mcgill.ecse420.a2.Lock.Lock;
import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class SafeIncrementTask implements Runnable {
	Lock lock;

	private static Random rand = new Random();

	public SafeIncrementTask(Lock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			lock.lock();
			int numberOfSpaces = LockTester.globalIndex+1;
			System.out.println("Thread " + ThreadID.get() + ":" + String.format("%" + numberOfSpaces + "s", "") + "|");
				
			
			LockTester.globalIndex++;
			lock.unlock();

		}

	}

}
