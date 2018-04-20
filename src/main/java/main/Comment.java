package main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by dryzu on 05.04.2018.
 */
@XmlRootElement
public class Comment {
	//@XmlElement
	public String Id;
	//@XmlElement
	private Date createDate;
	//@XmlElement
	private User author;
	@XmlElement
	public String text;
	@XmlElement
	public float rating;


	public Comment(String id, User author, String text, Date createDate, float rating) {
		this.author = author;
		this.text = text;
		this.createDate = createDate;
		this.rating = rating;
		this.Id = id;
		//this.Id = (author.hashCode() - text.hashCode()) + "";
	}

	public Comment() {
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
