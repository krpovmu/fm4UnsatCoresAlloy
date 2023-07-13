package de.buw.fm4se;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

class AlloyCheckerMinScopeTest {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	void checkMinScopesList() {
		String fileName = "src/main/resources/list.als";
		Map<String, Integer> minScope = AlloyChecker.findMinScope(fileName, opt, rep);
		assertEquals(0, minScope.get("this/Node"));
		assertEquals(1, minScope.get("this/List"));
	}

	@Test
	void checkDeadSigsDead1() {
		String fileName = "src/main/resources/dead1.als";
		Map<String, Integer> minScope = AlloyChecker.findMinScope(fileName, opt, rep);
		assertEquals(0, minScope.get("this/Node"));
		assertEquals(0, minScope.get("this/List"));
	}

	@Test
	void checkDeadSigsDead2() {
		String fileName = "src/main/resources/dead2.als";
		Map<String, Integer> minScope = AlloyChecker.findMinScope(fileName, opt, rep);
		assertEquals(0, minScope.get("this/Node"));
		assertEquals(0, minScope.get("this/List"));
	}

	@Test
	void checkDeadSigsDreadbury() {
		String fileName = "src/main/resources/dreadbury.als";
		Map<String, Integer> minScope = AlloyChecker.findMinScope(fileName, opt, rep);
		assertEquals(0, minScope.get("this/Person"));
		assertEquals(1, minScope.get("this/Agatha"));
		assertEquals(1, minScope.get("this/Butler"));
		assertEquals(1, minScope.get("this/Charles"));
	}

	@Test
	void checkDeadSigsHouses() {
		String fileName = "src/main/resources/houses.als";
		Map<String, Integer> minScope = AlloyChecker.findMinScope(fileName, opt, rep);
		assertEquals(0, minScope.get("this/House"));
		assertEquals(1, minScope.get("this/H1"));
		assertEquals(1, minScope.get("this/H2"));
		assertEquals(1, minScope.get("this/H3"));
		assertEquals(1, minScope.get("this/Math"));
		assertEquals(1, minScope.get("this/White"));

	}

}
