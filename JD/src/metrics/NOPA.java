package metrics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.*;

public class NOPA {
	private Map<String, Double> classesMap;

	public NOPA(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		for (ClassObject classObject : classes) {
			double NumOfAPublicAttributes = computeNOPA(classObject);
			if (NumOfAPublicAttributes != -1) {
				classesMap.put(classObject.getName(), NumOfAPublicAttributes);
			}
		}

	}

	private double computeNOPA(ClassObject classObject) {
		double NumOfAPublicAttributes = 0;
		List<FieldObject> attributes = classObject.getFieldList();
		Iterator<FieldObject> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			FieldObject f1 = (FieldObject) iterator.next();
			String s = f1.getAccess().toString();
			if (s == "public") {
				NumOfAPublicAttributes++;
			}

		}
		return NumOfAPublicAttributes;
	}

	public Map<String, Double> resultSet() {
		return classesMap;
	}
}