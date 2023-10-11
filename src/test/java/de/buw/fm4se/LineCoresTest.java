package de.buw.fm4se;

import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

class LinesCoreTest {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	void checkLineSigsList() throws IOException {
		String fileName = "src/main/resources/list.als";
		LineCores fc = new LineCores();
		List<String> core = fc.findCore(fileName, opt, rep);
		System.out.println(fc.printCore(core));
		assertEquals(6, core.size());
	}

	@Test
	void checkLineSigsCore() throws IOException {
		String fileName = "src/main/resources/core.als";
		LineCores fc = new LineCores();
		List<String> core = fc.findCore(fileName, opt, rep);
		System.out.println(fc.printCore(core));
		assertEquals(9, core.size());
	}

}
