package ca.mcgill.ecse420.a2;

import ca.mcgill.ecse420.a2.Lock.Lock;
import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class SafeIncrementTask implements Runnable {
	Lock lock;

	public SafeIncrementTask(Lock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			lock.lock();
			int numberOfSpaces = LockTester.globalIndex++;
			System.out.println("Thread " + ThreadID.get() + ":" + String.format("%" + numberOfSpaces + "s", "") + "|");
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				System.out.println("Didn't work");
				e.printStackTrace();
			}
			lock.unlock();

		}

	}

}
