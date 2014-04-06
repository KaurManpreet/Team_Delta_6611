package metrics;

import java.util.Map;

public class ClassInfo {

	private String className;
	private Map<String, Double> metricValue;

	public ClassInfo(String className, Map<String, Double> metricValue) {
		super();
		this.className = className;
		this.metricValue = metricValue;
	}

}
