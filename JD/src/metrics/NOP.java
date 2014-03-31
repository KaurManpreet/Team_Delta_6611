package metrics;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOP {
private Map<String, List<MethodObject>> classesMap;
	
	public NOP(SystemObject system){
		
		Set<ClassObject> classes = system.getClassObjects();
	
		for(ClassObject classObject : classes) {
			int numOfAbMethods=0;
			//classesMap.put(classObject.getName(), cohesion);
			List<MethodObject> methods = classObject.getMethodList();
			System.out.println();
			System.out.println("Class Name:-"+ classObject.getName());
			//System.out.println("Methods are:-");
			Iterator itr = methods.iterator();
			while(itr.hasNext()){
				MethodObject m1 = (MethodObject)itr.next();
				if(m1.isAbstract()){
					numOfAbMethods++;
				}
				//System.out.println(((MethodObject)itr.next()).getName());
			}
			System.out.println("Number of Abstract Methods -"+numOfAbMethods);
			System.out.println();
		}
		
		
	}
}
