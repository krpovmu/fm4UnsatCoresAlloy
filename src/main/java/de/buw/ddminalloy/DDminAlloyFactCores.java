package de.buw.ddminalloy;

import java.util.List;

import de.buw.fm4se.FactsMUSCores;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.translator.A4Options;

public class DDminAlloyFactCores {

//	private static TruthValueTest<Integer> harness = new TruthValueTest<Integer>() {
//		@Override
//		public int run(List<Integer> input) {
//			// TODO Auto-generated method stub
//			return PASS;
//		}
//	};

	public static void main(String[] args) {

//		FactCores fc = new FactCores();
		FactsMUSCores fcm = new FactsMUSCores();
		String filename = "/home/shuma/Documents/Bauhaus/02-Formal-Methods/08-NewFinalProject/FinalProject/fm4UnsatCoresAlloy/src/main/resources/houses.als";
		A4Options options = new A4Options();
		A4Reporter reporter = new A4Reporter();

//		List<Expr> core = fc.findCore(filename, options, reporter);
//		System.out.println(fc.printCore(core));

		List<Expr> MUSCores = fcm.findMUS(filename, options, reporter);

		for (Expr expr : MUSCores) {
			System.out.println(expr);
		}

	}
}
