package net.androidla.getarticle.bean;

public class Tag {
	private Integer id;
	private String name;
	private Integer[] articles;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer[] getArticles() {
		return articles;
	}
	public void setArticles(Integer[] articles) {
		this.articles = articles;
	}
	
}
