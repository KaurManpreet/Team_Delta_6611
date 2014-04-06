package metrics;

import java.util.Map;

public class MetricInfo {
	private String metricName;
	private Map<String, Double> metricValue;

	public MetricInfo(String metricName, Map<String, Double> metricValue) {
		super();
		this.metricName = metricName;
		this.metricValue = metricValue;
	}

	public String getMetricName() {
		return metricName;
	}

	public Map<String, Double> getMetricValue() {
		return metricValue;
	}

}
