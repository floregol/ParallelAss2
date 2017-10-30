package ca.mcgill.ecse420.a2.Lock;

import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class FilterLock implements Lock {

	private int n;
	private volatile int[] threadLevels; // keeps track of the level
	private volatile int[] victim;

	public FilterLock(int numOfThreads) {
		n = numOfThreads;
		threadLevels = new int[numOfThreads];
		victim = new int[numOfThreads];
	}

	// DOESNT WORK
	@Override
	public void lock() {
		int thisThreadID = ThreadID.get();

		for (int levelIndex = 1; levelIndex < n; levelIndex++) { // try to reach highest level
			threadLevels[thisThreadID] = levelIndex;
			victim[levelIndex] = thisThreadID; // assigns himself to be the victim of this level

			while (thereIsThreadWithHigherLevelThan(thisThreadID) && victim[levelIndex] == thisThreadID) {
				// wait
			}
		}

	}

	public boolean thereIsThreadWithHigherLevelThan(int thisThreadID) {
		int thisThreadLevel = threadLevels[thisThreadID];
		for (int id = 0; id < n; id++) {
			if (id != thisThreadID) {
				if (threadLevels[id] > thisThreadLevel) {
					return true;

				} else if (threadLevels[id] == thisThreadLevel && id < thisThreadID) {

					return true;
				}

			}

		}
		return false;
	}

	@Override
	public void unlock() {
		int thisThreadID = ThreadID.get();
		threadLevels[thisThreadID] = 0;
	}

}
