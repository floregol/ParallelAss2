package ca.mcgill.ecse420.a2.Lock;

import ca.mcgill.ecse420.a2.ThreadId.ThreadID;

public class FilterLock implements Lock { //Both locks implement the same lock interface
	private int n;
	private volatile int[] threadLevels; // keeps track of the levels
	private volatile int[] victim;

	public FilterLock(int numOfThreads) {
		n = numOfThreads;
		threadLevels = new int[numOfThreads];
		victim = new int[numOfThreads];
	}

	@Override
	public void lock() {
		int thisThreadID = ThreadID.get(); // use the code provided by the TA to get the thread's ID

		for (int levelIndex = 1; levelIndex < n; levelIndex++) { // try to reach highest level
			threadLevels[thisThreadID] = levelIndex; //update this thread's level
			victim[levelIndex] = thisThreadID; // assigns himself to be the victim of this level

			while (thereIsThreadWithHigherLevelThan(thisThreadID) && victim[levelIndex] == thisThreadID) {
				// wait while there is a higher or equal thread and this thread is the victim
			}
		}
	}

	public boolean thereIsThreadWithHigherLevelThan(int thisThreadID) {
		int thisThreadLevel = threadLevels[thisThreadID];
		for (int id = 0; id < n; id++) {
			if (id != thisThreadID) { // skip yourself
				if (threadLevels[id] >= thisThreadLevel) {
					return true;	
				}
			}
		}
		return false;
	}

	@Override
	public void unlock() {
		int thisThreadID = ThreadID.get();
		threadLevels[thisThreadID] = 0; // to release the lock assign yourself a 0 level
	}

}