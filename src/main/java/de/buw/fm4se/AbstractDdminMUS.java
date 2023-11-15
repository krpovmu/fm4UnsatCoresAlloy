package de.buw.fm4se;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;

public abstract class AbstractDdminMUS<T, E> {

	public static final int PASS = 1;
	public static final int UNRESOLVED = 0;
	public static final int FAIL = -1;

	public static <E> List<E> ddmin(List<E> input, Module module, Command command, A4Reporter reporter, A4Options opt) {
		int n = 2;
//		TruthValueTest<E> tvalue = null;
		while (input.size() >= 2) {
			// Reduce the subsets -- 1
			List<List<E>> subsets = split(input, n);
			boolean complementFailing = false;
			for (List<E> subset : subsets) {
				// reduce o complement -- 2
				List<E> complement = difference(input, subset);
				// --- here I have to assemble the model with the new complement
				if (check((List<Expr>) complement, module, command, reporter, opt) == TruthValueTest.FAIL) {
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
	 * This method calculate the complement
	 * 
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
	 * create a conjunction from candidate facts and the predicate of the command
	 * 
	 * @param part
	 * @return
	 */
	private static Expr assemble(List<Expr> part) {
		List<Expr> cand = new ArrayList<Expr>(part);
//		cand.add(predicate);
//		ExprList el = ExprList.make(predicate.pos, predicate.span(), ExprList.Op.AND, cand);
		ExprList el = ExprList.make(null, null, ExprList.Op.AND, cand);
		return el;
	}

//	private static TruthValueTest<Integer> harness = new TruthValueTest<Integer>() {
//		@Override
	public static int check(List<Expr> part, Module module, Command command, A4Reporter reporter, A4Options options) {
		Command cmd;
		int result = FAIL;
		cmd = command.change(assemble(part));
		A4Solution ans = TranslateAlloyToKodkod.execute_command(reporter, module.getAllReachableSigs(), cmd, options);
		if (ans.satisfiable()) {
			result = PASS;
		}
		return result;
	}

//	};

//	protected boolean check(List<Expr> part, Module module, Command command, A4Reporter reporter, A4Options options) {
//		Command cmd = command.change(assemble(part));
//		A4Solution ans = TranslateAlloyToKodkod.execute_command(reporter, module.getAllReachableSigs(), cmd, options);
//		return !ans.satisfiable();
//	}
}