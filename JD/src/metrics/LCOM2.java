package metrics;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.MethodObject;
import ast.SystemObject;

public class LCOM2 {
	private static final int NOT_APPLICABLE = -1;
	private Map<String,Integer> metricMap;
	public LCOM2(SystemObject system){
		this.metricMap = new LinkedHashMap<String,Integer>();

		Set<ClassObject> classes = system.getClassObjects();
		for(ClassObject classObject : classes){
			int value = computeLCOM(classObject);
			if(value != NOT_APPLICABLE){
				metricMap.put(classObject.getName(), value);
			}
		}
	}

	private int computeLCOM(ClassObject classObject){
		int p=0;
		int q=0;
		List<MethodObject> methods = classObject.getMethodList();
		if(methods.size() < 2){
			return NOT_APPLICABLE;
		}
		for(int i=0;i<methods.size();i++){
			MethodObject methodI= methods.get(i);
			for(int j=i+1;j<methods.size();j++){
				MethodObject methodJ=methods.get(j);
				if(commonlyUsedAttributes(methodI,methodJ,classObject.getName())){
					q++;
				}else{
					p++;
				}
			}
		}
		if(p>q){
			return p-q;
		}else{
			return 0;
		}
	}

	private boolean commonlyUsedAttributes(MethodObject methodI,MethodObject methodJ, String className){
		List<FieldInstructionObject> fieldsI = methodI.getFieldInstructions();
		List<FieldInstructionObject> fieldsJ = methodJ.getFieldInstructions();
		
		for(FieldInstructionObject fieldI:fieldsI){
			if(fieldI.getOwnerClass().equals(className) && fieldsJ.contains(fieldI)){
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : metricMap.keySet()) {
			sb.append(key).append("\t").append(metricMap.get(key)).append("\n");
		}
		return sb.toString();
	}
}
