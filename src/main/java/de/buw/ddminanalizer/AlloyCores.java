package de.buw.ddminanalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Browsable;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Func;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;

public class AlloyCores<E> extends AbstractDdmin<Object, Object> {

    private Command command;
    private Module module;
    
    @SuppressWarnings("unchecked")
    public List<E> findMinimalUnsatisfableSubset(String fileName, A4Options opt, A4Reporter rep) throws IOException {

	this.module = CompUtil.parseEverything_fromFile(rep, null, fileName);
	this.command = module.getAllCommands().get(0);

	LinkedHashMap<String, Object> listElementsAlloyModel = new LinkedHashMap<String, Object>();
	List<E> musCores = new ArrayList<E>();

	// facts
	List<E> facts = new ArrayList<E>();
	for (int i = 0; i < module.getAllFacts().size(); i++) {
	    List<? extends Browsable> listFacts = (module.getAllFacts().get(i).b).getSubnodes();
	    for (Browsable fact : listFacts) {
		facts.add((E) fact);
	    }
	}
	listElementsAlloyModel.put("Facts", facts);
	// Predicates and Functions
	List<E> functions = new ArrayList<E>();
	List<E> predicates = new ArrayList<E>();
	for (int i = 0; i < module.getAllFunc().size(); i++) {
	    Func element = module.getAllFunc().get(i);
	    // The idea is ignore run commands, run commands are always null in the explain
	    if (!(element.explain() == null)) {
		// This validation tell me if is a predicate
		if (element.isPred) {
		    predicates.add((E) element);
		}
		// otherwise is a function
		else {
		    functions.add((E) element);
		}
	    }
	}
//	listElementsAlloyModel.put("Predicates", predicates);
	//listElementsAlloyModel.put("Function", functions);

	for (Map.Entry<String, Object> element : listElementsAlloyModel.entrySet()) {
	    if (((List<E>) element.getValue()).size() == 0) {
		System.out.println("Empty: " + element.getKey() + " List");
	    } else {
		musCores = (List<E>) ddmin((List<Object>) element.getValue(), module, command, rep, opt, element.getKey(), fileName);
	    }
	}
	return musCores;
    }
}