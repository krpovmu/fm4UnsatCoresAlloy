package de.buw.fm4se;

import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;

class AlloyCheckerCoreTest {
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
		List<String> core = AlloyChecker.findCoreSignatures(fileName, opt, rep);
		assertEquals(1, core.size());
	}

	@Test
	void checkCoreSigsDead1() {
		String fileName = "src/main/resources/dead1.als";
		List<String> core = AlloyChecker.findCoreSignatures(fileName, opt, rep);
		assertEquals(0, core.size());
	}

	@Test
	void checkCoreSigDead2() {
		String fileName = "src/main/resources/dead2.als";
		List<String> core = AlloyChecker.findCoreSignatures(fileName, opt, rep);
		assertEquals(0, core.size());
	}
	
	@Test
	void checkCoreSigsDreadbury() {
		String fileName = "src/main/resources/dreadbury.als";
		List<String> core = AlloyChecker.findCoreSignatures(fileName, opt, rep);
		assertEquals(4, core.size());
	}

	@Test
	void checkCoreSigsHouses() {
		String fileName = "src/main/resources/houses.als";
		List<String> core = AlloyChecker.findCoreSignatures(fileName, opt, rep);
		assertEquals(14, core.size());
	}	

}
