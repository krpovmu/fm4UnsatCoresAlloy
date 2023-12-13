package de.buw.ddminanalizer;

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

/**
 * @param <T>
 * @param <E>
 */
public abstract class AbstractDdmin<T, E> {

	public static final int PASS = 1;
	public static final int UNRESOLVED = 0;
	public static final int FAIL = -1;

	/**
	 * @param <E>
	 * @param input
	 * @param module
	 * @param command
	 * @param reporter
	 * @param opt
	 * @param printOption
	 * @return
	 */
	public static <E> List<E> ddmin(List<E> input, Module module, Command command, A4Reporter reporter, A4Options opt, int printOption) 
	{
		int n = 2;
		while (input.size() >= 2) {
			// Reduce the subsets (1)
			List<List<E>> subsets = split(input, n);
			boolean complementFailing = false;
			for (List<E> subset : subsets) {
				List<E> complement = difference(input, subset);
				// Assemble the model with the complement I got from the last operation
				int resultCheck = check((List<Expr>) complement, module, command, reporter, opt);
				// this if is just for full print option is not related with ddmin algorithm
				if (printOption == 1) {
					String result = (resultCheck == 1) ? "PASSED" : "FAILED";
					System.out.println(" EXPRESSION: " + complement + " RESULT: " + result);
				}
				if (resultCheck == FAIL) {
					input = complement;
					// Reduce to complement (2)
					n = Math.max(n - 1, 2);
					complementFailing = true;
					break;
				}
			}
			if (!complementFailing) {
				// Done (4)
				if (n == input.size()) {
					break;
				}
				// Increase granularity (3)
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
	 * @param part
	 * @return
	 */
	private static Expr assemble(List<Expr> part) {
		List<Expr> cand = new ArrayList<Expr>(part);
		ExprList el = ExprList.make(null, null, ExprList.Op.AND, cand);
		return el;
	}

	/**
	 * @param part
	 * @param module
	 * @param command
	 * @param reporter
	 * @param options
	 * @return
	 */
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

	/**
	 * @param core
	 * @return
	 */
	public String printCore(List<Expr> core) {
		String result = "";
		for (Expr e : core) {
			result += e.pos.toShortString() + ": ";
			result += e.toString() + "\n";
		}
		return result;
	}
}