package de.buw.fm4se;

import java.util.ArrayList;
import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.ast.Sig;
import edu.mit.csail.sdg.parser.CompModule;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class PredSigMUSCores<T, E> extends AbstractDdminMUS<T, E> {
	public List<Expr> findMUS(String fileName, A4Options options, A4Reporter reporter, int printOption) {
		List<Expr> listPredSigs = new ArrayList<Expr>();
		CompModule module = CompUtil.parseEverything_fromFile(null, null, fileName);
		try {
			for (Func predator : module.getAllFunc()) {
				System.out.println(predator.label + " ->  " + predator.getBody());
			}
			for (Sig predName : module.getAllSigs()) {
				System.out.println("Signature: " + predName);
			}
		} catch (Err e) {
			e.printStackTrace();
		}
		return listPredSigs;
	}
}
