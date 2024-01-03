package de.buw.ddminanalizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;

public abstract class AbstractDdmin<T, E> {

	public static final int PASS = 1;
	public static final int FAIL = -1;
	public static final int UNRESOLVED = 0;

	public List<E> ddmin(List<E> list, Module module, Command command, A4Reporter reporter, A4Options opt, String typeCore, String pathModel, boolean printTrace) throws IOException {
		int n = 2;
		// Copy orig list to predicates bcz the list will be modified
		List<E> predicatesList = new ArrayList<E>();
		if (typeCore.equals("Predicates")) {
			predicatesList = list;
		}
		while (list.size() >= 2) {
			// Reduce the subsets (1)
			List<List<E>> subsets = split(list, n);
			boolean complementFailing = false;
			List<E> passListToCompare = new ArrayList<E>();
			for (List<E> subset : subsets) {
				List<E> complement = difference(list, subset);
				int resultCheck = check(complement, module, command, reporter, opt, typeCore, pathModel, predicatesList);
				if (printTrace) {
					System.out.println(printTrace(complement, resultCheck));
				}
				if (resultCheck == PASS) {
					passListToCompare = complement;
				}
				if (resultCheck == FAIL) {
					list = complement;
					// Reduce to complement (2)
					n = Math.max(n - 1, 2);
					complementFailing = true;
					break;
				}
				if (resultCheck == UNRESOLVED) {
					
					
					

				}
			}
			if (!complementFailing) {
				// Done (4)
				if (n == list.size()) {
					break;
				}
				// Increase granularity (3)
				n = Math.min(n * 2, list.size());
			}
		}
		return list;
	}

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

	private <E> List<E> difference(List<E> a, List<E> b) {
		List<E> result = new LinkedList<E>();
		result.addAll(a);
		result.removeAll(b);
		return result;
	}

	private Expr assemble(List<E> complement, List<E> input) throws IOException {
		List<E> cand = new ArrayList<E>(complement);
		ExprList el = ExprList.make(null, null, ExprList.Op.AND, (List<? extends Expr>) cand);
		return el;
	}

	public int check(List<E> complement, Module module, Command command, A4Reporter reporter, A4Options options, String typeCore, String pathModel, List<E> input) throws IOException {
		A4Solution ans = null;
		int result = FAIL;
		if (typeCore.equals("Facts")) {
			try {
				Command cm = command.change(assemble(complement, input));
				ans = TranslateAlloyToKodkod.execute_command(reporter, module.getAllReachableSigs(), cm, options);
			} catch (Exception e) {
				result = UNRESOLVED;
			}
		} else if (typeCore.equals("Predicates")) {
			try {
				String modelWithJustThePredicateToEvaluate = removePredicates(complement, input, pathModel);
				module = CompUtil.parseEverything_fromString(reporter, modelWithJustThePredicateToEvaluate);
				ans = TranslateAlloyToKodkod.execute_command(reporter, module.getAllReachableSigs(), module.getAllCommands().get(0), options);
			} catch (Exception e) {
				result = UNRESOLVED;
			}
		} else {
			System.out.println("Invalid kind of core.");
		}
		if (ans == null) {
			result = UNRESOLVED;
		} else if (ans.satisfiable()) {
			result = PASS;
		}
		return result;
	}

	public String removePredicates(List<E> partListPredicates, List<E> originalListPredicates, String pathModel) throws IOException {
		String originalModel = new String(Files.readAllBytes(Paths.get(pathModel)));
		StringBuilder modifiedModel = new StringBuilder();
		List<E> listPredicatesToDelete = difference(originalListPredicates, partListPredicates);
		String[] lines = originalModel.split("\n");
		for (String line : lines) {
			boolean lineIsPredicateToDelete = lineIsPredicateToDelete(listPredicatesToDelete, line);
			if (!lineIsPredicateToDelete) {
				modifiedModel.append(line).append("\n");
			}
		}
		return modifiedModel.toString();
	}

	public boolean lineIsPredicateToDelete(List<E> predicates, String line) {
		boolean isPredicateToDelete = false;
		for (int i = 0; i < predicates.size(); i++) {
			Func funcPredicate = (Func) predicates.get(i);
			String[] elementsNamePredicate = funcPredicate.label.toString().split("/");
			String namePredicate = elementsNamePredicate[elementsNamePredicate.length - 1];
			String regex = "^pred\\s+" + namePredicate + "\\s*(?:\\[[^\\]]*\\])?\\s*\\{[^{}]*}$";
			if (line.matches(regex)) {
				isPredicateToDelete = true;
			}
		}
		return isPredicateToDelete;
	}

	public String printTrace(List<E> core, int ddminResult) {
		return "List : " + core + " Result: " + ((ddminResult == 1) ? "PASS" : (ddminResult == 0) ? "UNRESOLVED" : "FAIL");
	}
}