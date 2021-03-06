package metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.MethodObject;
import ast.SystemObject;

public class WOC {
	private Map<String, Double> wocMap;

	public WOC(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		wocMap = new HashMap<String, Double>();
		for (ClassObject classObject : classes) {
			double wocNbr = computeWOC(classObject, classes);
			if (wocNbr != -1) {
				wocMap.put(classObject.getName(), wocNbr);
			}
		}
	}

	private double computeWOC(ClassObject classObject, Set<ClassObject> classes) {
		List<MethodObject> methods = classObject.getMethodList();
		double nom = methods.size();
		int getterCount = 0;
		int setterCount = 0;
		for (int i = 0; i < methods.size(); i++) {
			MethodObject mI = methods.get(i);
			FieldInstructionObject getter = mI.isGetter();
			FieldInstructionObject setter = mI.isSetter();
			if (getter != null) {
				getterCount++;
			}
			if (setter != null) {
				setterCount++;
			}

		}
		double nbrOfNonAccesorMethod = nom - (getterCount + setterCount);
		double wocNbr = 0;
		if (nom != 0) {
			wocNbr = nbrOfNonAccesorMethod / nom;
		}
		return wocNbr;
	}

	public Map<String, Double> resultSet() {
		return wocMap;
	}
}
