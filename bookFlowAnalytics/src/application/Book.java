package application;

public class Book {
//	getters for the private variables
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

	public String getPubDate() {
		return publicationDate;
	}
	
	public int getRatingsCount() {
		return ratingsCount;
	}
	
	public String getDescription() {
		return description;
	}
	
	private String title;
	private String author;
	private String coverUrl;
	private String publicationDate;
	private int ratingsCount;
	private String description;
	private double rating;
	
//	creating a parameterized constructor
	public Book(String title, String author, String coverUrl, double rating, String publicationDate, int ratingsCount, String description)
	{
//		passing the fetched values to the variables and then passing those values to the class wide defined variables (instance variables)
		this.title = title;
		this.author = author;
		this.coverUrl = coverUrl;
		this.rating = rating;
		this.ratingsCount = ratingsCount;
		this.publicationDate = publicationDate;
		this.description = description;
	}
}