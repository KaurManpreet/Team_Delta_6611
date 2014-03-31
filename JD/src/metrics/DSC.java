package metrics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;

public class DSC {
	
	public DSC(SystemObject system) {
		
		
		Set<ClassObject> classes = system.getClassObjects();
		
		Iterator itr = classes.iterator();
		while(itr.hasNext()){
			System.out.println(((ClassObject)itr.next()).getName());
		}
	}
}
