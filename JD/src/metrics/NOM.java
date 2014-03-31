package metrics;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOM {
	
	private Map<String, List<MethodObject>> classesMap;
	
	public NOM(SystemObject system){
		
		Set<ClassObject> classes = system.getClassObjects();
	
		for(ClassObject classObject : classes) {
			//classesMap.put(classObject.getName(), cohesion);
			List<MethodObject> methods = classObject.getMethodList();
			System.out.println();
			System.out.println("Class Name:-"+ classObject.getName());
			System.out.println("Methods are:-");
			Iterator itr = methods.iterator();
			while(itr.hasNext()){
				System.out.println(((MethodObject)itr.next()).getName());
			}
			System.out.println();
		}
		
		
	}
}
