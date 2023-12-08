package de.buw.fm4se;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.Pair;
import edu.mit.csail.sdg.alloy4.SafeList;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.ast.Sig;
import edu.mit.csail.sdg.parser.CompModule;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class ModelAnalyzerMUS<T, E> {

	public List<Object> modelSeparator(String fileName, A4Options options, A4Reporter reporter, int printOption) {

		// this list will keep the result of the separation
		List<Object> listSigPredFacts = new ArrayList<Object>();
		// this regex is for run command
		String run_regex = "\\brun\\w*\\$";
		Pattern pattern = Pattern.compile(run_regex);
		CompModule module = CompUtil.parseEverything_fromFile(null, null, fileName);
		Command command = module.getAllCommands().get(0);
		SafeList<Sig> signatures = module.getAllSigs();
		HashMap<String, Object> predicates = new HashMap<String, Object>();
		HashMap<String, Expr> facts = new HashMap<String, Expr>();

		try {
			for (Sig sigName : signatures) {
				signatures.add(sigName);
			}
			// id o - add signatures
			listSigPredFacts.add(signatures);
			ExprList el = (ExprList) command.formula;
			List<Expr> facsList = el.args;
			for (Pair<String, Expr> facsito : module.getAllFacts()) {
				for (Expr facExpr : facsList) {
					facts.put(facsito.a, facExpr);
				}
			}
			// id 1 - add facts
			SafeList<Expr> predicatesList = new SafeList<Expr>();
			SafeList<Expr> runPred = new SafeList<Expr>();
			listSigPredFacts.add(facts);
			for (Func predicate : module.getAllFunc()) {
				Matcher match = pattern.matcher(predicate.label);
				if (!match.find()) {
					predicatesList.add(predicate.getBody());
				} else {
					runPred.put(predicate.label, predicate.getBody());
				}
				predicates.put(predicate.label, predicatesList);
			}
			// id 2 - add predicates
			listSigPredFacts.add(predicates);
			// id 3 - add run predicates
			listSigPredFacts.add(runPred);
		} catch (Err e) {
			e.printStackTrace();
		}
		return listSigPredFacts;
	}
}
