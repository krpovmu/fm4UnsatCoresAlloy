package de.buw.fm4se;

import java.util.List;
import java.util.Map;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.CommandScope;
import edu.mit.csail.sdg.ast.Sig;
import edu.mit.csail.sdg.translator.A4Options;

public class AlloyChecker {

	public static List<String> findDeadSignatures(String fileName, A4Options options, A4Reporter rep) {
		// TODO Task 1
		return null;
	}

	public static List<String> findCoreSignatures(String fileName, A4Options options, A4Reporter rep) {
		// TODO Task 2
		return null;
	}

	/**
	 * Computes for each user-defines signature a minimal scope for which the model
	 * is still satisfiable. Note that the scopes will be independent, i.e., minimum
	 * 0 for sig A and 0 for sig B does not mean that both can be 0 together.
	 * 
	 * @param fileName
	 * @param options
	 * @param rep
	 * @return map from signature names to minimum scopes
	 */
	public static Map<String, Integer> findMinScope(String fileName, A4Options options, A4Reporter rep) {
		// TODO Task 3
		return null;

	}

	/**
	 * Computes the maximum scope for a signature in a command. This is either the
	 * default of 4, the overall scope, or the specific scope for the signature in
	 * the command. 
	 * 
	 * @param sig
	 * @param cmd
	 * @return
	 */
	public static int getMaxScope(Sig sig, Command cmd) {
		int scope = 4; // Alloy's default
		if (cmd.overall != -1) {
			scope = cmd.overall;
		}
		CommandScope cmdScope = cmd.getScope(sig);
		if (cmdScope != null) {
			scope = cmdScope.endingScope;
		}
		return scope;
	}

}
