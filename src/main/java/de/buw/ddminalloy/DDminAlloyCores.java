package de.buw.ddminalloy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.buw.ddminanalizer.AlloyCores;
import de.buw.fm4se.FactCores;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.translator.A4Options;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class DDminAlloyCores {

	public static <E> void main(String[] args) throws IOException, ArgumentParserException {
		ArgumentParser parser = ArgumentParsers.newFor("FileLoader").build().description("Load and print file names from console");
		parser.addArgument("-i", "--inputfiles").metavar("FILE").nargs("+").type(Arguments.fileType().verifyExists().verifyIsFile()).required(true).help("List of files to load");
		parser.addArgument("-m", "--mode").metavar("MODE").choices("single", "legacy").setDefault("single").help("Specify the mode of analysis: 'single' (default out code), legacy (teacher's code)");
		parser.addArgument("-t", "--trace").action(Arguments.storeTrue()).help("print all analisis stack");
		parser.addArgument("-f", "--facts").action(Arguments.storeTrue()).help("Analize errors in the model related with facts");
		parser.addArgument("-p", "--predicates").action(Arguments.storeTrue()).help("Analize errors in the model related with predicates");

		Namespace ns = parser.parseArgs(args);
		List<File> files = ns.getList("inputfiles");
		String kindOfAnalysis = ns.getString("mode");
		boolean trace = ns.getBoolean("trace");
		boolean isFactsOn = ns.getBoolean("facts");
		boolean isPredicatesOn = ns.getBoolean("predicates");

		A4Reporter reporter = new A4Reporter();
		A4Options options = new A4Options();
		List<E> core = null;

		for (File file : files) {
			switch (kindOfAnalysis) {
			case "legacy":
				FactCores fc = new FactCores();
				core = fc.findCore(file.getPath(), options, reporter);
				System.out.println(fc.printCore(core));
				break;
			case "single":
			default:
				AlloyCores sc = new AlloyCores();
				sc.findMinimalUnsatisfableSubset(file.getPath(), options, reporter, trace, isFactsOn, isPredicatesOn);
				break;
			}
		}
	}
}
