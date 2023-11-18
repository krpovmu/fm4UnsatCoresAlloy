package de.buw.fm4se;

import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprList;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class FactsMUSCores<E, T> extends AbstractDdminMUS<T, E> {

	private Command command;
	private Module module;
	private A4Options options;
	private A4Reporter reporter;

	/**
	 * @param fileName
	 * @param options
	 * @param reporter
	 * @param printOption
	 * @return
	 */
	public List<Expr> findMUS(String fileName, A4Options options, A4Reporter reporter, int printOption) {
		this.options = options;
		this.reporter = reporter;
		this.module = CompUtil.parseEverything_fromFile(reporter, null, fileName);
		this.command = module.getAllCommands().get(0);

		ExprList el = (ExprList) command.formula;
		List<Expr> listMUSCores = el.args;

		return ddmin(listMUSCores, module, command, reporter, options, printOption);
	}

}
