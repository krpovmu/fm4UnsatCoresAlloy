package de.buw.fm4se;

import java.util.List;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.translator.A4Options;

public abstract class TruthValueTest<E> {

	public static final int PASS = 1;
	public static final int UNRESOLVED = 0;
	public static final int FAIL = -1;

	/**
	 * Returns true if the test passes and false if the test fails
	 * 
	 * @param <E>
	 * @param complement
	 * @return
	 */
//	public abstract int check(List<Expr> part, Module module, Command command, A4Reporter reporter, A4Options options);

	public abstract int check(List<Expr> part, Module module, Command command, A4Reporter reporter, A4Options options);
	// {
//		// TODO Auto-generated method stub
//		return 0;
//	}
}
