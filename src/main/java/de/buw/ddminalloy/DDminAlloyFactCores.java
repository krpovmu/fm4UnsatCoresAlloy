package de.buw.ddminalloy;

import java.util.List;

import de.buw.fm4se.FactCores;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.translator.A4Options;

public class DDminAlloyFactCores {


	public static void main(String[] args) {
		
		FactCores fc = new FactCores();
		String filename = "/home/shuma/Documents/Bauhaus/02-Formal-Methods/08-NewFinalProject/FinalProject/fm4UnsatCoresAlloy/src/main/resources/houses.als";
		
		A4Reporter rep = new A4Reporter() {
			@Override
			public void warning(ErrorWarning msg) {
				System.out.print("Relevance Warning:\n" + (msg.toString().trim()) + "\n\n");
				System.out.flush();
			}
		};
		
		A4Options options = new A4Options();
		options.solver = A4Options.SatSolver.SAT4J;
//
//		for (String filename : args) {
//			Module world = CompUtil.parseEverything_fromFile(rep, null, filename);
//		}
		
		List<Expr> listExpr = fc.findCore(filename, options, rep);
		System.out.println(fc.printCore(listExpr));
	}

}
