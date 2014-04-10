package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.VariableDeclaration;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.FieldObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.TypeObject;

public class ATFD {
	private int atfdcounter;
	private Map<String, Integer> atfdMap;

	public ATFD(SystemObject system) {
		atfdcounter = 0;
		atfdMap = new HashMap<String, Integer>();
		Set<ClassObject> classes = system.getClassObjects();
		for (ClassObject classObject : classes) {
			int aftdNbr = computeAtfd(classObject, classes);
			if (aftdNbr != -1) {
				atfdMap.put(classObject.getName(), aftdNbr);
			}
		}
	}

	private int computeAtfd(ClassObject classObject, Set<ClassObject> classes) {
		List<MethodObject> methods = classObject.getMethodList();
		Set<TypeObject> classesAccessed = new HashSet<TypeObject>();
		for (int i = 0; i < methods.size(); i++) {

			MethodObject mI = methods.get(i);
			List<FieldInstructionObject> attributesI = mI
					.getFieldInstructions();

			for (FieldInstructionObject attributesII : attributesI) {
				TypeObject tt = attributesII.getType();
			}

			for (FieldInstructionObject combinationI : attributesI) {
				for (ClassObject class1 : classes) {
					TypeObject tt = combinationI.getType();
					if (class1.getName().equalsIgnoreCase(tt.getClassType()))
						classesAccessed.add(combinationI.getType());
				}

			}
		}

		int i = classesAccessed.size();
		classesAccessed.clear();

		return i;
	}

	public Map<String, Integer> resultSet() {
		StringBuilder sb = new StringBuilder();
		return atfdMap;
	}
}
