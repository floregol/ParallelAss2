package ca.mcgill.ecse420.a2;

import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class UnsafeIncrementTask implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			int numberOfSpaces = LockTester.globalIndex++;
			System.out.println("Thread " + ThreadID.get() + ":" + String.format("%" + numberOfSpaces + "s", "") + "|");

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				System.out.println("Didn't work");
				e.printStackTrace();
			}

		}

	}

}