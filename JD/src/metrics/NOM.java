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
	
	private Map<String, Integer> classesMap;

	public NOM(SystemObject system) {
		classesMap = new HashMap<String, Integer>();
		
		Set<ClassObject> classes = system.getClassObjects();
		
		for(ClassObject classObject : classes) {
			int methods = computeMethods(classObject);
			if(methods != -1) {
				classesMap.put(classObject.getName(), methods);
			}
		}
		
	}
	
	private int computeMethods(ClassObject classObject) {
		
		List<MethodObject> methods = classObject.getMethodList();
		return (methods.size());
		
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
