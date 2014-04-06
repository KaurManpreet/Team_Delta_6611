package metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOAM {
	private Map<String, Double> NOAMMap;

	public NOAM(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		NOAMMap = new HashMap<String, Double>();
		for (ClassObject classObject : classes) {
			double NOAMNbr = computeNOAM(classObject, classes);
			if (NOAMNbr != -1) {
				NOAMMap.put(classObject.getName(), NOAMNbr);
			}
		}
	}

	private double computeNOAM(ClassObject classObject, Set<ClassObject> classes) {
		List<MethodObject> methods = classObject.getMethodList();
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
		return (getterCount + setterCount);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : NOAMMap.keySet()) {
			sb.append(key).append("\t").append(NOAMMap.get(key)).append("\n");
		}
		return sb.toString();
	}
}
