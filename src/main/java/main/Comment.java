package main;

import java.util.Date;

/**
 * Created by dryzu on 05.04.2018.
 */
public class Comment {
	public String Id;
	private Date createDate;
	private User author;
	private String text;
	private float rating;


	public Comment(String id, User author, String text, Date createDate, float rating) {
		this.author = author;
		this.text = text;
		this.createDate = createDate;
		this.rating = rating;
		this.Id = id;
		//this.Id = (author.hashCode() - text.hashCode()) + "";
	}

	public String getText() {
		return text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public float getRating() {
		return rating;
	}

	public User getAuthor() {
		return author;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + Id + " ");
		sb.append(author.getName() + ".");
		sb.append(" Rating: ");
		sb.append(rating);
		sb.append(" Date: ");
		sb.append(createDate.toString());
		sb.append('\n');
		sb.append(text);
		sb.append('\n');
		return sb.toString();
	}
}
