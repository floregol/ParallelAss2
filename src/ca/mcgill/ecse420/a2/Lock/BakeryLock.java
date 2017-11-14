package ca.mcgill.ecse420.a2.Lock;

import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class BakeryLock implements Lock {

	private static volatile boolean[] flag;
	private static volatile int[] number;

	public BakeryLock(int threadSize) {
		flag = new boolean[threadSize];
		number = new int[threadSize];

		for (int i = 0; i < threadSize; i++) {
			flag[i] = false;
			number[i] = 0;
		}
	}

	public void lock() {
		int id = ThreadID.get();
		int max = 0;
		flag[id] = true; // signal that this thread is interested to acquire the lock
		max = this.getMax(number) + 1; //
		number[id] = max;

		flag[id] = false;

		for (int i = 0; i < number.length; i++) {
			if (i == id) {
				continue; // skip ourself
			}
			// Wait for thread that's being set
			while (flag[i]) {
			}
			// Wait for thread with smaller number or with smaller pid
			while (number[i] != 0 && (number[i] < number[id] || number[i] == number[id] && i < id)) {
				// System.out.println("stuck");
			}
		}
	}

	public void unlock() {
		int id = ThreadID.get();
		number[id] = 0;
	}

	private int getMax(int[] list) {
		int max = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] > max) {
				max = list[i];
			}
		}
		return max;
	}
}
