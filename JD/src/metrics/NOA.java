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

	private Map<String, Integer> classesMap;

	public NOA(SystemObject system) {
		classesMap = new HashMap<String, Integer>();
		
		Set<ClassObject> classes = system.getClassObjects();
		
		for(ClassObject classObject : classes) {
			int attributes = computeAttributes(classObject);
			if(attributes != -1) {
				classesMap.put(classObject.getName(), attributes);
			}
		}
		
	}
	
	private int computeAttributes(ClassObject classObject) {
		
		List<FieldObject> attributes = classObject.getFieldList();
		return (attributes.size());
		
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : classesMap.keySet()) {
			sb.append(key).append("\t").append(classesMap.get(key)).append("\n");
		}
		return sb.toString();
	}
}
