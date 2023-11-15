package de.buw.fm4se;

import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprConstant;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;

public class FactCores extends AbstractDdmin<Expr> {

	private Command c;
	private Module m;

	private A4Options opt;
	private A4Reporter rep;

	private Expr predicate;

	/**
	 * Check if the given Alloy module with the current subset of facts is unsat.
	 */
	@Override
	protected boolean check(List<Expr> part) {
		Command cmd = c.change(assemble(part));
		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, m.getAllReachableSigs(), cmd, opt);
		return !ans.satisfiable();
	}

	/**
	 * create a conjunction from candidate facts and the predicate of the command
	 * 
	 * @param part
	 * @return
	 */
	private Expr assemble(List<Expr> part) {
		List<Expr> cand = new java.util.ArrayList<Expr>(part);
		cand.add(predicate);
		ExprList el = ExprList.make(predicate.pos, predicate.span(), ExprList.Op.AND, cand);
		return el;
	}

	/**
	 * Given an Alloy module with an unsat command (only checks the first command)
	 * find a minimal subset of the facts to be still unsat.
	 * 
	 * @param fileName
	 * @param opt
	 * @param rep
	 * @return
	 */
	public List<Expr> findCore(String fileName, A4Options opt, A4Reporter rep) {
		this.opt = opt;
		this.rep = rep;
		this.m = CompUtil.parseEverything_fromFile(rep, null, fileName);
		
		// If you always get the first fact why do you validate size 
		this.c = m.getAllCommands().get(0);

		ExprList el = (ExprList) c.formula;
		int numFacts = m.getAllFacts().size();

		// same number of conjuncts as facts means there is no run predicate
		List<Expr> part;
		if (numFacts == el.args.size()) {
			this.predicate = ExprConstant.TRUE;
			part = el.args;
		} else {
			this.predicate = el.args.get(el.args.size() - 1);
			part = el.args.subList(0, el.args.size() - 1);
		}
		return minimize(part);
	}

	/**
	 * Print the core line numbers and expressions.
	 * 
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