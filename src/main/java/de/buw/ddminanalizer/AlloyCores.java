package de.buw.ddminanalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Browsable;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class AlloyCores<E> extends AbstractDdmin<Object, Object> {

	private Command command;
	private Module module;

	@SuppressWarnings("unchecked")
	public void findMinimalUnsatisfableSubset(String fileName, A4Options opt, A4Reporter rep, boolean printTrace, boolean isFact, boolean isPred) throws IOException {

		this.module = CompUtil.parseEverything_fromFile(rep, null, fileName);
		this.command = module.getAllCommands().get(0);
		LinkedHashMap<String, Object> listElementsAlloyModel = new LinkedHashMap<String, Object>();

		if (isFact) {
			// Facts extraction
			List<E> facts = new ArrayList<E>();
			for (int i = 0; i < module.getAllFacts().size(); i++) {
				List<? extends Browsable> listFacts = (module.getAllFacts().get(i).b).getSubnodes();
				for (Browsable fact : listFacts) {
					facts.add((E) fact);
				}
			}
			// Add facts to the list
			listElementsAlloyModel.put("Facts", facts);
		}
		if (isPred) {
			// Predicates and Functions
			List<E> functions = new ArrayList<E>();
			List<E> predicates = new ArrayList<E>();
			for (int i = 0; i < module.getAllFunc().size(); i++) {
				Func element = module.getAllFunc().get(i);
				// The idea is ignore run commands, run commands are always null in the explain
				if (!(element.explain() == null)) {
					// This validation tell me if the element is a predicate
					if (element.isPred) {
						predicates.add((E) element);
					}
					// otherwise the element is a function
					else {
						functions.add((E) element);
					}
				}
			}
			listElementsAlloyModel.put("Predicates", predicates);
			// listElementsAlloyModel.put("Function", functions);
		}

		if (!listElementsAlloyModel.isEmpty()) {
			List<E> musCores = null;
			for (Map.Entry<String, Object> element : listElementsAlloyModel.entrySet()) {
				musCores = new ArrayList<E>();
				if (((List<E>) element.getValue()).size() == 0) {
					System.out.println("Empty: " + element.getKey() + " List");
				} else {
					musCores = (List<E>) ddmin((List<Object>) element.getValue(), module, command, rep, opt, element.getKey(), fileName, printTrace);
				}
				System.out.println("=============");
				System.out.println("\n" + printCore(musCores));
				System.out.println("=============");
			}
		} else {
			System.out.println("There are not elements to analyze, have a good day :P");
		}
	}

	public String printCore(List<E> core) {
		String result = "";
		for (E e : core) {
			if (e.getClass().getSimpleName().equals("Func")) {
				Func elem = (Func) e;
				result += "Pred: " + elem.pos.toShortString() + ": ";
				result += elem.toString() + "\n";
			} else {
				Expr elem = (Expr) e;
				result += "Fact: " + elem.pos.toShortString() + ": ";
				result += elem.toString() + "\n";
			}
		}
		return result;
	}
}