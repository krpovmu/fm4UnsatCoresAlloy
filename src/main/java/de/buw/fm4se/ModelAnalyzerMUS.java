package de.buw.fm4se;

import java.util.LinkedHashMap;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.SafeList;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.parser.CompModule;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class ModelAnalyzerMUS<T, E> {

	public LinkedHashMap<String, Object> modelSeparator(String fileName, A4Options options, A4Reporter reporter,
			int printOption) {
		LinkedHashMap<String, Object> listSigPredFacts = new LinkedHashMap<String, Object>();

		try {
			CompModule module = CompUtil.parseEverything_fromFile(null, null, fileName);
			Command command = module.getAllCommands().get(0);
			listSigPredFacts.put("Sigs", module.getAllSigs());
			listSigPredFacts.put("Facts", module.getAllFacts());

			SafeList<Object> predicates = new SafeList<Object>();
			SafeList<Object> Functions = new SafeList<Object>();

			for (Func func : module.getAllFunc()) {
				// Ignore run commands which were added before
				if (!(func.explain() == null)) {
					// It's a predicate
					if (func.isPred) {
						predicates.add(func);
					}
					// otherwise is a Function
					else {
						Functions.add(func);
					}
				}
			}
			listSigPredFacts.put("Predicates", predicates);
			listSigPredFacts.put("Functions", Functions);
			listSigPredFacts.put("Run", command);

		} catch (Err e) {
			e.printStackTrace();
		}
		return listSigPredFacts;
	}
}
