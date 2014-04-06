package metrics;

import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.MethodObject;
import ast.SystemObject;

public class WOC {
	public WOC(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		for (ClassObject classObject : classes) {
			double woc = computeWOC(classObject, classes);
			if (woc != -1) {
				System.out.println("WOC metric number for "
						+ classObject.getName() + " is " + woc);
			}
		}
	}

	private double computeWOC(ClassObject classObject, Set<ClassObject> classes) {
		List<MethodObject> methods = classObject.getMethodList();
		int nom = methods.size();

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
		int nbrOfNonAccesorMethod = nom - (getterCount + setterCount);
		System.out.println("nbrOfNonAccesorMethod" + nbrOfNonAccesorMethod);

		double woc = nbrOfNonAccesorMethod / nom;
		return woc;

	}
}
