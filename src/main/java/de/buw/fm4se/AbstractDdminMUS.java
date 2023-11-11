package de.buw.fm4se;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDdminMUS<T> {

	/**
	 * @param <E>
	 * @param input
	 * @param tvalue
	 * @return
	 */
	public static <E> List<E> ddmin(List<E> input, TruthValueTest<E> tvalue) {
		int n = 2;
		while (input.size() >= 2) {
			// Reduce the subsets -- 1
			List<List<E>> subsets = split(input, n);
			boolean complementFailing = false;
			for (List<E> subset : subsets) {
				// reduce o complement -- 2
				List<E> complement = difference(input, subset);
				if (tvalue.run(complement) == TruthValueTest.FAIL) {
					input = complement;
					n = Math.max(n - 1, 2);
					complementFailing = true;
					break;
				}
			}
			if (!complementFailing) {
				// done -- 4
				if (n == input.size()) {
					break;
				}
				// increase granularity -- 3
				n = Math.min(n * 2, input.size());
			}
		}
		return input;
	}

	/**
	 * @param <E>
	 * @param s
	 * @param n
	 * @return
	 */
	private static <E> List<List<E>> split(List<E> s, int n) {
		List<List<E>> subsets = new LinkedList<List<E>>();
		int position = 0;
		for (int i = 0; i < n; i++) {
			List<E> subset = s.subList(position, position + (s.size() - position) / (n - i));
			subsets.add(subset);
			position += subset.size();
		}
		return subsets;
	}

	/**
	 *  This method calculate the complement
	 * @param <E>
	 * @param a
	 * @param b
	 * @return
	 */
	private static <E> List<E> difference(List<E> a, List<E> b) {
		List<E> result = new LinkedList<E>();
		result.addAll(a);
		result.removeAll(b);
		return result;
	}

	/**
	 * check if part satisfies criterion, e.g., unsatisfiabiliy when looking for
	 * unsat core
	 * 
	 * @param part
	 * @return true if part satisfies criterion
	 */
	protected abstract boolean check(List<T> part);
}