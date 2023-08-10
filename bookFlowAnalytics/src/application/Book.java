package application;

public class Book {
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getCoverUrl() {
		return coverUrl;
	}


	private String title;
	private String author;
	private String coverUrl;
	
	public Book(String title, String author, String coverUrl)
	{
		this.title = title;
		this.author = author;
		this.coverUrl = coverUrl;
	}
	
}
