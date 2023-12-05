package de.buw.ddminalloy;

import java.io.File;
import java.util.List;

import de.buw.fm4se.FactsMUSCores;
import de.buw.fm4se.PredSigMUSCores;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.translator.A4Options;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class DDminAlloyFactCores {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArgumentParser parser = ArgumentParsers.newFor("FileLoader").build()
				.description("Load and print file names from console");
		parser.addArgument("-f", "--files").metavar("FILE").nargs("+")
				.type(Arguments.fileType().verifyExists().verifyIsFile()).required(true).help("List of files to load");
		parser.addArgument("-m", "--mode").metavar("MODE").choices("single", "full", "legacy").setDefault("single")
				.help("Specify the mode for printing result of analysis: 'single' (default), legacy (teacher's code), or 'full' which print all stack");

		try {
			Namespace ns = parser.parseArgs(args);
			List<File> files = ns.getList("files");
			String mode = ns.getString("mode");

			for (File file : files) {
				FactsMUSCores fcm = new FactsMUSCores();
				PredSigMUSCores psc = new PredSigMUSCores();
				A4Options options = new A4Options();
				A4Reporter reporter = new A4Reporter();
				List<Expr> MUSCores = psc.findMUS(file.getPath(), options, reporter, 0);
			}

//			for (File file : files) {
//				FactsMUSCores fcm = new FactsMUSCores();
//				A4Options options = new A4Options();
//				A4Reporter reporter = new A4Reporter();
//				List<Expr> MUSCores = new ArrayList<Expr>();
//				switch (mode) {
//				case "full":
//					MUSCores = fcm.findMUS(file.getPath(), options, reporter, 1);
//					// System.out.println("Path: " + file.getPath());
//					break;
//				case "legacy":
//					FactCores fc = new FactCores();
//					List<Expr> core = fc.findCore(file.getPath(), options, reporter);
//					System.out.println(fc.printCore(core));
//					break;
//				case "single":
//				default:
//					MUSCores = fcm.findMUS(file.getPath(), options, reporter, 0);
//					System.out.println(fcm.printCore(MUSCores));
//					break;
//				}
//			}
		} catch (Exception e) {
			parser.handleError((ArgumentParserException) e);
			System.exit(1);
		}
	}
}
