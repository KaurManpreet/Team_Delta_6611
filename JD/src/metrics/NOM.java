package metrics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOM {

	private Map<String, Double> classesMap;

	public NOM(SystemObject system) {
		classesMap = new HashMap<String, Double>();

		Set<ClassObject> classes = system.getClassObjects();

		for (ClassObject classObject : classes) {
			double methods = computeMethods(classObject);
			if (methods != -1) {
				classesMap.put(classObject.getName(), methods);
			}
		}

	}

	private int computeMethods(ClassObject classObject) {

		List<MethodObject> methods = classObject.getMethodList();
		return (methods.size());

	}

	public Map<String, Double> resultSet() {
		StringBuilder sb = new StringBuilder();
		return classesMap;
	}
}
