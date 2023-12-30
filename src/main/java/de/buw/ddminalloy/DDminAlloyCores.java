package de.buw.ddminalloy;

import java.io.File;
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

    public static <E> void main(String[] args) {
	ArgumentParser parser = ArgumentParsers.newFor("FileLoader").build()
		.description("Load and print file names from console");
	parser.addArgument("-f", "--files").metavar("FILE").nargs("+")
		.type(Arguments.fileType().verifyExists().verifyIsFile()).required(true).help("List of files to load");
	parser.addArgument("-m", "--mode").metavar("MODE").choices("single", "full", "legacy").setDefault("single")
		.help("Specify the mode for printing result of analysis: 'single' (default), legacy (teacher's code), or 'full' which print all stack");

	try {
	    Namespace ns = parser.parseArgs(args);
	    List<File> files = ns.getList("files");
	    String kindOfAnalysis = ns.getString("mode");

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

//				case "full":
//					MUSCores = fcm.findMinimalUnsatisfableSubset(file.getPath(), options, reporter, 1);
//					// System.out.println("Path: " + file.getPath());
//					break;

		case "optional":

		    break;

		case "single":
		default:
		    AlloyCores sc = new AlloyCores();
		    core = sc.findMinimalUnsatisfableSubset(file.getPath(), options, reporter);
		    System.out.println(sc.printCore(core));
		    break;
		}
	    }
	} catch (Exception e) {
	    parser.handleError((ArgumentParserException) e);
	    System.exit(1);
	}
    }
}
