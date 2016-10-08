package com.bstek.dorado.sample.touch;

import java.util.Collection;

public class TouchExample {
	private int id;
	private String label;
	private String url;
	private String summary;
	private Collection<TouchExample> examples;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Collection<TouchExample> getExamples() {
		return examples;
	}

	public void setExamples(Collection<TouchExample> examples) {
		this.examples = examples;
	}
}
