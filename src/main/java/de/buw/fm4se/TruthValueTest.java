package de.buw.fm4se;

import java.util.List;

public abstract class TruthValueTest<E> {

	public static final int PASS = 1;
	public static final int UNRESOLVED = 0;
	public static final int FAIL = -1;

	/**
	 * Returns true if the test passes and false if the test fails
	 * 
	 * @param <E>
	 * @param complement
	 * @return
	 */
	public abstract int run(List<E> input);
}
