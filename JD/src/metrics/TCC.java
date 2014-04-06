package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.MethodInvocationObject;
import ast.MethodObject;
import ast.SystemObject;

public class TCC {
	private Map<String, Double> cohesionMap;

	public TCC(SystemObject system) {
		cohesionMap = new HashMap<String, Double>();

		Set<ClassObject> classes = system.getClassObjects();

		for(ClassObject classObject : classes) {
			double cohesion = computeCohesion(classObject,system);
			if(cohesion != -1) {
				cohesionMap.put(classObject.getName(), cohesion);
			}
		}

	}

	private double computeCohesion(ClassObject classObject,SystemObject system) {

		List<MethodObject> methods = classObject.getMethodList();
		int n= methods.size();
		double p = 0;
		double q = (n*(n-1))/2;

		if(methods.size() < 2) {
			return -1;
		}

		for(int i=0; i<methods.size()-1; i++) {
			MethodObject mI = methods.get(i);

			List<FieldInstructionObject> attributesI = mI.getFieldInstructions();
			attributesI.addAll(getRemoteFieldInstructions(mI,system));

			for(int j=i+1; j<methods.size(); j++) {
				MethodObject mJ = methods.get(j);
				List<FieldInstructionObject> attributesJ = mJ.getFieldInstructions();
				attributesJ.addAll(getRemoteFieldInstructions(mJ,system));
				Set<FieldInstructionObject> intersection = commonAttributes(attributesI, attributesJ, classObject.getName());
				if(!intersection.isEmpty()) {
					p++;
				} 
			}
		}
		return (p/q);
	}

	private Set<FieldInstructionObject> commonAttributes(List<FieldInstructionObject> attributesI,
			List<FieldInstructionObject> attributesJ, String className) {

		Set<FieldInstructionObject> commonAttributes = new HashSet<FieldInstructionObject>();
		for (FieldInstructionObject instructionI : attributesI) {
			if(instructionI.getOwnerClass().equals(className) && attributesJ.contains(instructionI)) {
				commonAttributes.add(instructionI);
			}
		}
		return commonAttributes;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : cohesionMap.keySet()) {
			sb.append(key).append("\t").append(cohesionMap.get(key)).append("\n");
		}
		return sb.toString();
	}

	private static List<FieldInstructionObject> getRemoteFieldInstructions(MethodObject mI,SystemObject s1)
	{
		List<FieldInstructionObject> attributesNew= new ArrayList<FieldInstructionObject>();
		Set<MethodInvocationObject> methodsI = mI.getInvokedMethodsThroughThisReference();
		Iterator<MethodInvocationObject> i1=methodsI.iterator();
		while(i1.hasNext()){
			MethodObject newM = s1.getMethod(i1.next());
			attributesNew.addAll(newM.getFieldInstructions());	
		}	
		return attributesNew;
	}
}
