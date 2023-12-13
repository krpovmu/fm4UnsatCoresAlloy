package de.buw.ddminanalizer;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.mit.csail.sdg.alloy4.SafeList;

public class AnalysisIntegrator<E> { // extends AbstractDdminMUS {

//	private Command command;
//	private Module module;
//	private A4Options options;
//	private A4Reporter reporter;

//	public LinkedHashMap<String, Object> findMinimalUnsatisfableSubset(String fileName, A4Options options, A4Reporter reporter, LinkedHashMap<String, Object> model, int printOption) {
	public <T> void findMinimalUnsatisfableSubset(LinkedHashMap<String, Object> model) {

//		this.options = options;
//		this.reporter = reporter;
//		this.module = CompUtil.parseEverything_fromFile(reporter, null, fileName);
//		this.command = module.getAllCommands().get(0);
//		ModelAnalyzerMUS separatedModel = new ModelAnalyzerMUS();
//		LinkedHashMap<String, Object> partsModel = separatedModel.modelSeparator(fileName);
//
		for (Map.Entry<String, Object> elementModel : model.entrySet()) {
//			System.out.println(elementModel.getKey() + " -> " + elementModel.getValue().getClass());

			if (elementModel.getKey().contains("Fact")) { // equals("Facts")) {
				SafeList<T> facts = (SafeList<T>) elementModel.getValue();
				for (int i = 0; i < facts.size(); i++) {
					System.out.println(elementModel.getKey() + " -> " + facts.get(i));

				}
			}
		}

//		return ddmin(listMUSCores, module, command, reporter, options, printOption);
//		return partsModel;

	}
}
