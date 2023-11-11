package de.buw.fm4se;

import java.util.ArrayList;
import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class FactsMUSCores extends AbstractDdminMUS<Expr> {

	private Command c;
	private Module m;
	private A4Options opt;
	private A4Reporter rep;
	private Expr predicate;

	/**
	 * @param fileName
	 * @param opt
	 * @param rep
	 * @return
	 */
	public List<Expr> findMUS(String fileName, A4Options opt, A4Reporter rep) {
		this.opt = opt;
		this.rep = rep;
		this.m = CompUtil.parseEverything_fromFile(rep, null, fileName);
		this.c = m.getAllCommands().get(0);

		ExprList el = (ExprList) c.formula;
		int numFacts = m.getAllFacts().size();

		List<Expr> listMUSCores = null;
		TruthValueTest<Expr> tv = null;

		return ddmin(listMUSCores, tv);
	}

	/**
	 *
	 */
	@Override
	protected boolean check(List<Expr> part) {
		// TODO Auto-generated method stub
		// here we are going to check the validity of the set of expression combine with the original model
		
		return false;
	}
}
