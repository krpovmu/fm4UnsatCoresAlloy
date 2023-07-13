package de.buw.fm4se;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

class AlloyCheckerDeadTest {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	void checkDeadSigsList() {
		String fileName = "src/main/resources/list.als";
		List<String> dead = AlloyChecker.findDeadSignatures(fileName, opt, rep);
		assertEquals(0, dead.size());
	}

	@Test
	void checkDeadSigsDead1() {
		String fileName = "src/main/resources/dead1.als";
		List<String> dead = AlloyChecker.findDeadSignatures(fileName, opt, rep);
		assertEquals(1, dead.size());
	}

	@Test
	void checkDeadSigsDead2() {
		String fileName = "src/main/resources/dead2.als";
		List<String> dead = AlloyChecker.findDeadSignatures(fileName, opt, rep);
		assertEquals(2, dead.size());
	}
	
	@Test
	void checkDeadSigsDreadbury() {
		String fileName = "src/main/resources/dreadbury.als";
		List<String> dead = AlloyChecker.findDeadSignatures(fileName, opt, rep);
		assertEquals(0, dead.size());
	}

	@Test
	void checkDeadSigsHouses() {
		String fileName = "src/main/resources/houses.als";
		List<String> dead = AlloyChecker.findDeadSignatures(fileName, opt, rep);
		assertEquals(0, dead.size());
	}

}
