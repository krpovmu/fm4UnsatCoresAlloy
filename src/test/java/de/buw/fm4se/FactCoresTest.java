package de.buw.fm4se;

import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;

class FactCoreTest {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	void checkCoreSigsList() {
		String fileName = "src/main/resources/list.als";
		FactCores fc = new FactCores();
		List<Expr> core = fc.findCore(fileName, opt, rep);
		System.out.println(fc.printCore(core));
		assertEquals(1, core.size());
	}

	@Test
	void checkCoreSigsCore() {
		String fileName = "src/main/resources/core.als";
		FactCores fc = new FactCores();
		List<Expr> core = fc.findCore(fileName, opt, rep);
		System.out.println(fc.printCore(core));
		assertEquals(2, core.size());
	}

}
