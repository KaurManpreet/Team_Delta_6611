package metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.decomposition.CompositeStatementObject;
import ast.decomposition.MethodBodyObject;
import ast.decomposition.StatementObject;
import ast.decomposition.TryStatementObject;

public class WMC {

	private Map<String, Integer> classesMap;

	public WMC(SystemObject system) {
		classesMap = new HashMap<String, Integer>();
		Set<ClassObject> classes = system.getClassObjects();
		for (ClassObject classObject : classes) {
			int complexity = computeComplexity(classObject);
			if (complexity != -1) {
				classesMap.put(classObject.getName(), complexity);
			}
		}
	}

	private int computeComplexity(ClassObject classObject) {
		List<MethodObject> methods = classObject.getMethodList();
		int complexity=0;
		for(int i=0; i<methods.size()-1; i++) {
			MethodObject methodObject = methods.get(i);
			int count =0;
			if(methodObject.getMethodBody() != null) {
				CompositeStatementObject compositeStatementObject;
				if(methodObject.getMethodBody().getCompositeStatement() != null)
				{
					compositeStatementObject = methodObject.getMethodBody().getCompositeStatement();	
					List<CompositeStatementObject> ifStatements = compositeStatementObject.getIfStatements();
					List<CompositeStatementObject> switchStatements = compositeStatementObject.getSwitchStatements();
					List<CompositeStatementObject> forStatements = compositeStatementObject.getForStatements();
					List<CompositeStatementObject> whileStatements = compositeStatementObject.getWhileStatements();
					List<CompositeStatementObject> doStatements = compositeStatementObject.getDoStatements();
					count = ifStatements.size()+switchStatements.size()+forStatements.size()
							+whileStatements.size()+doStatements.size();
					complexity+=count;
				}
			}
		}
		return complexity+1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : classesMap.keySet()) {
			sb.append(key).append("\t").append(classesMap.get(key))
			.append("\n");
		}
		return sb.toString();
	}
}
