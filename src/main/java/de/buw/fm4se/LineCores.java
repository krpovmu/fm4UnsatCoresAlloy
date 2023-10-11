package de.buw.fm4se;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;

public class LineCores extends AbstractDdmin<String>{

	private A4Options opt;
	private A4Reporter rep;


	/**
	 * Check if the given Alloy module with the current subset of facts is unsat.
	 */
	@Override
	protected boolean check(List<String> part) {
		try {
			Module m = CompUtil.parseEverything_fromString(rep, String.join("\n", part));
			Command cmd = m.getAllCommands().get(0);
			A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, m.getAllReachableSigs(), cmd, opt);
			return !ans.satisfiable();
		} catch (Exception e) {
			// inconclusive, treat as sat
			// TODO this means that we will overlook some cores that are subsets of this part
			return false;
		}
	}

	/**
	 * Given an Alloy module with an unsat command (only checks the first command) find a minimal subset of the facts to be still unsat.
	 * 
	 * @param fileName
	 * @param opt
	 * @param rep
	 * @return
	 * @throws IOException
	 */
	public List<String> findCore(String fileName, A4Options opt, A4Reporter rep) throws IOException {
		this.opt = opt;
		this.rep = rep;

		List<String> part;
		
		part = Files.readAllLines(Paths.get(fileName));
		return minimize(part);
    }

	/**
	 * Print the core line numbers and expressions.
	 * 
	 * @param core
	 * @return
	 */
	public String printCore(List<String> core) {
		return String.join("\n", core);
	}


}
