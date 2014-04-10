package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.FieldObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOA {

	private Map<String, Double> classesMap;

	public NOA(SystemObject system) {
		classesMap = new HashMap<String, Double>();

		Set<ClassObject> classes = system.getClassObjects();

		for (ClassObject classObject : classes) {
			double attributes = computeAttributes(classObject);
			if (attributes != -1) {
				classesMap.put(classObject.getName(), attributes);
			}
		}

	}

	private double computeAttributes(ClassObject classObject) {

		List<FieldObject> attributes = classObject.getFieldList();
		return (attributes.size());

	}

	public Map<String, Double> resultSet() {
		StringBuilder sb = new StringBuilder();
		return classesMap;
	}
}
