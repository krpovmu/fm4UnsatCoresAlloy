package de.buw.fm4se;

import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class FactsMUSCores<E, T> extends AbstractDdminMUS<T, E>{

	private Command command;
	private Module module;
	private A4Options opt;
	private A4Reporter rep;


	/**
	 * @param 
	 * @return
	 */
	public List<Expr> findMUS(String fileName, A4Options opt, A4Reporter rep) {
		this.opt = opt;
		this.rep = rep;
		this.module = CompUtil.parseEverything_fromFile(rep, null, fileName);
		this.command = module.getAllCommands().get(0);

		ExprList el = (ExprList) command.formula;
//		int numFacts = module.getAllFacts().size();

		List<Expr> listMUSCores = el.args;
		TruthValueTest<Expr> tv = null;
		
		return ddmin(listMUSCores, tv, module, command, rep, opt);
	}

//
//	@Override
//	public int check(List<Expr> part, Module module, Command command, A4Reporter reporter, A4Options options) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
