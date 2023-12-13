package de.buw.ddminalloy;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import de.buw.ddminanalizer.AnalysisIntegrator;
import de.buw.ddminanalizer.ModelAnalyzer;
import de.buw.fm4se.FactCores;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.translator.A4Options;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class DDminAlloyCores {

	public static void main(String[] args) {
		ArgumentParser parser = ArgumentParsers.newFor("FileLoader").build()
				.description("Load and print file names from console");
		parser.addArgument("-f", "--files").metavar("FILE").nargs("+")
				.type(Arguments.fileType().verifyExists().verifyIsFile()).required(true).help("List of files to load");
		parser.addArgument("-m", "--mode").metavar("MODE").choices("single", "full", "legacy").setDefault("single")
				.help("Specify the mode for printing result of analysis: 'single' (default), legacy (teacher's code), or 'full' which print all stack");

		try {
			A4Options options = new A4Options();
			A4Reporter reporter = new A4Reporter();
			Namespace ns = parser.parseArgs(args);
			List<File> files = ns.getList("files");
			String kindOfAnalysis = ns.getString("mode");

			for (File file : files) {
				// Object from model breaker
				// get all cores from the model
				// LinkedHashMap<String, Object> coresToAnalize =
				// allModel.modelSeparator(file.getPath(), options, reporter, 0);
				// Integrator

				// in this structure we are going to keep all results from ddmin analysis

				switch (kindOfAnalysis) {
				case "legacy":
					FactCores fc = new FactCores();
					List<Expr> core = fc.findCore(file.getPath(), options, reporter);
					System.out.println(fc.printCore(core));
					break;
//				case "full":
//
//					MUSCores = fcm.findMinimalUnsatisfableSubset(file.getPath(), options, reporter, 1);
//					// System.out.println("Path: " + file.getPath());
//					break;
				case "single":
				default:
					ModelAnalyzer allModel = new ModelAnalyzer();
					LinkedHashMap<String, Object> ac = allModel.modelSeparator(file.getPath());
					AnalysisIntegrator integrator = new AnalysisIntegrator();
					integrator.findMinimalUnsatisfableSubset(ac);
					
//					AnalysisIntegrator integrator = new AnalysisIntegrator();
//					LinkedHashMap<String, Object> resultDdmin = new LinkedHashMap<String, Object>();
//					MUSCores = fcm.findMinimalUnsatisfableSubset(file.getPath(), options, reporter, 0);
//					System.out.println(fcm.printCore(MUSCores));
					break;
				}

//				FactsMUSCores fcm = new FactsMUSCores();
//				List<Object> MUSCores = psc.findMUS(file.getPath(), options, reporter, 0);
//				System.out.println(MUSCores);
//			}

//			for (File file : files) {
//				FactsMUSCores fcm = new FactsMUSCores();
//				A4Options options = new A4Options();
//				A4Reporter reporter = new A4Reporter();
//				List<Expr> MUSCores = new ArrayList<Expr>();
			}
		} catch (Exception e) {
			parser.handleError((ArgumentParserException) e);
			System.exit(1);
		}
	}
}
