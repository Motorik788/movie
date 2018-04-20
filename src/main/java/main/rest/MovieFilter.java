package main.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dryzu on 20.04.2018.
 */
@XmlRootElement
public class MovieFilter {
	@XmlElement
	public boolean isAdult;
	@XmlElement
	public String name;
	@XmlElement
	public int fromYear;
	@XmlElement
	public int toYear;
	@XmlElement
	public float fromRating;
	@XmlElement
	public float toRating;
}
