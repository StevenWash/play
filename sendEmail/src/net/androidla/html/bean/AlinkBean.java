package net.androidla.html.bean;

public class AlinkBean {
	private Integer id;
	private String use;
	private String url;
	private String extractFrom;
	private String title;
	private String scheme;
	private String inputDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getExtractFrom() {
		return extractFrom;
	}
	public void setExtractFrom(String extractFrom) {
		this.extractFrom = extractFrom;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	@Override
	public String toString() {
		return "AlinkBean [id=" + id + ", use=" + use + ", url=" + url
				+ ", extractFrom=" + extractFrom + ", title=" + title
				+ ", scheme=" + scheme + ", inputDate=" + inputDate + "]";
	}

}
