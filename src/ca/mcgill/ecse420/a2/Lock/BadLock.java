package ca.mcgill.ecse420.a2.Lock;

// A bad Lock implementation to ensure that our test can fail
public class BadLock implements Lock {

	@Override
	public void lock() {
		// this does nothing

	}

	@Override
	public void unlock() {
		// this does nothing
	}

}
