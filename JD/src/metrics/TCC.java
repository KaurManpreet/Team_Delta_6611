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

		for (ClassObject classObject : classes) {
			double cohesion = computeCohesion(classObject, system);
			cohesionMap.put(classObject.getName(), cohesion);
		}

	}

	private double computeCohesion(ClassObject classObject, SystemObject system) {

		List<MethodObject> methods = classObject.getMethodList();
		int n = methods.size();
		double p = 0;
		double q = (n * (n - 1)) / 2;

		if (methods.size() < 2) {
			return -1;
		}
		List<FieldInstructionObject> attributesI1 = new ArrayList<FieldInstructionObject>();
		List<FieldInstructionObject> attributesJ1 = new ArrayList<FieldInstructionObject>();
		for (int i = 0; i < methods.size() - 1; i++) {
			MethodObject mI = methods.get(i);
			if (mI != null) {
				attributesI1 = mI.getFieldInstructions();
				Set<FieldInstructionObject> setI = new HashSet<FieldInstructionObject>(
						attributesI1);
				setI.addAll(getRemoteFieldInstructions(mI, system));
				// attributesI.addAll(getRemoteFieldInstructions(mI,system));
				List<FieldInstructionObject> attributesI = new ArrayList<FieldInstructionObject>(
						setI);

				for (int j = i + 1; j < methods.size(); j++) {
					MethodObject mJ = methods.get(j);
					if (mJ != null) {

						attributesJ1 = mJ.getFieldInstructions();
						Set<FieldInstructionObject> setJ = new HashSet<FieldInstructionObject>(
								attributesJ1);
						setJ.addAll(getRemoteFieldInstructions(mJ, system));
						List<FieldInstructionObject> attributesJ = new ArrayList<FieldInstructionObject>(
								setJ);

						Set<FieldInstructionObject> intersection = commonAttributes(
								attributesI, attributesJ, classObject.getName());
						if (!intersection.isEmpty()) {
							p++;
						}
						// attributesJ.clear();
					}
				}
				// attributesI.clear();
			}
		}
		double tcc = 0;
		if (q != 0) {
			tcc = (p / q);
		}
		return tcc;
	}

	private Set<FieldInstructionObject> commonAttributes(
			List<FieldInstructionObject> attributesI,
			List<FieldInstructionObject> attributesJ, String className) {

		Set<FieldInstructionObject> commonAttributes = new HashSet<FieldInstructionObject>();
		for (FieldInstructionObject instructionI : attributesI) {
			if (instructionI.getOwnerClass().equals(className)
					&& attributesJ.contains(instructionI)) {
				commonAttributes.add(instructionI);
			}
		}
		return commonAttributes;

	}

	public Map<String, Double> resultSet() {
		return cohesionMap;
	}

	private static List<FieldInstructionObject> getRemoteFieldInstructions(
			MethodObject mI, SystemObject s1) {
		Set<FieldInstructionObject> attributesNew = new HashSet<FieldInstructionObject>();
		Set<MethodInvocationObject> methodsI = mI
				.getInvokedMethodsThroughThisReference();
		Iterator<MethodInvocationObject> i1 = methodsI.iterator();
		while (i1.hasNext()) {
			MethodObject newM = s1.getMethod(i1.next());
			if (newM != null) {
				attributesNew.addAll(newM.getFieldInstructions());
			}
		}
		List<FieldInstructionObject> attributesList = new ArrayList<FieldInstructionObject>(
				attributesNew);
		return attributesList;
	}
}
