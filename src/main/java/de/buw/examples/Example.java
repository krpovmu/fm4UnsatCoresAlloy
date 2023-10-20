package de.buw.examples;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.ast.Browsable;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class Example {

//	private static final String ALSFILE = "/home/shuma/Documents/Bauhaus/02-Formal-Methods/08-NewFinalProject/FinalProject/fm4UnsatCoresAlloy/src/main/resources/houses.als";

	public static void main(String[] args) {

//		System.out.println(ALSFILE);

		A4Reporter rep = new A4Reporter() {
			@Override
			public void warning(ErrorWarning msg) {
				System.out.print("Relevance Warning:\n" + (msg.toString().trim()) + "\n\n");
				System.out.flush();
			}
		};

		for (String filename : args) {
//			System.out.println("=========== Parsing+Typechecking " + filename + " =============");

			Module world = CompUtil.parseEverything_fromFile(rep, null, filename);
			A4Options options = new A4Options();
			options.solver = A4Options.SatSolver.SAT4J;

//			System.out.println(world.getAllAssertions());
//			System.out.println(world.getAllFacts());
//			System.out.println(world.getAllFunc());
//			System.out.println(world.getAllSigs());
//			System.out.println(world.getAllCommands());

//			for (Pair<String, Expr> fact : world.getAllFacts()) {
//				System.out.println(fact.b);
//			}

			for (Command com : world.getAllCommands()) {
//				System.out.println(com.formula.getSubnodes());
				for (Browsable formula : com.formula.getSubnodes()) {
					System.out.println(formula);
				}
			}

//			for (Sig signature : world.getAllReachableSigs()) {
//				System.out.println(signature.explain());
//			}

//			for (Command command : world.getAllCommands()) {
//				System.out.println("============ Command " + command + ": ============");
//				A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), command,
//						options);
//				System.out.println(ans.satisfiable());
//
//				System.out.println(ans);
//
//			}

		}
	}
}
