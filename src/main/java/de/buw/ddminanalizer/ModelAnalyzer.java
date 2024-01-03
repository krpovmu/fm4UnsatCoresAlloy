package de.buw.ddminanalizer;

import java.util.LinkedHashMap;
import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.SafeList;
import edu.mit.csail.sdg.ast.Browsable;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.parser.CompModule;
import edu.mit.csail.sdg.parser.CompUtil;

public class ModelAnalyzer<E> {
	public <T> LinkedHashMap<String, Object> modelSeparator(String fileName, A4Reporter reporter) {
		LinkedHashMap<String, Object> listSigPredFacts = new LinkedHashMap<String, Object>();
		try {
			CompModule module = CompUtil.parseEverything_fromFile(reporter, null, fileName);
			Command command = module.getAllCommands().get(0);
			//listSigPredFacts.put("Sigs", module.getAllSigs());
			for (int i = 0; i < module.getAllFacts().size(); i++) {
				// System.out.println(module.getAllFacts().get(i).a);
				// System.out.println(module.getAllFacts().get(i).b);
				String factName = "Fact" + i;
				SafeList<T> listExprFact = new SafeList<T>();
				List<? extends Browsable> exprFact = (module.getAllFacts().get(i).b).getSubnodes();
				for (Browsable fact : exprFact) {
					listExprFact.add((T) fact);
				}
				//listSigPredFacts.put(factName, listExprFact);
			}
			SafeList<Object> predicates = new SafeList<Object>();
			SafeList<Object> functions = new SafeList<Object>();
			for (Func func : module.getAllFunc()) {
				// Ignore run commands which were added before
				if (!(func.explain() == null)) {
					// It's a predicate
					if (func.isPred) {
						predicates.add(func);
					}
					// otherwise is a Function
					else {
						functions.add(func);
					}
				}
			}
			listSigPredFacts.put("Predicates", predicates);
			//listSigPredFacts.put("Functions", functions);
			//listSigPredFacts.put("Run", command);
		} catch (Err e) {
			e.printStackTrace();
		}
		return listSigPredFacts;
	}
}
