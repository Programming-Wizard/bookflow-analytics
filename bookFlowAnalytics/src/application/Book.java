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

	public double getRating() {
		return rating;
	}

	private String title;
	private String author;
	private String coverUrl;



	private double rating;
	
	public Book(String title, String author, String coverUrl, double rating)
	{
		this.title = title;
		this.author = author;
		this.coverUrl = coverUrl;
		this.rating = rating;

	}
	
}
