package operative;

public class Review implements Comparable<Review>{

	private String textReview;
	private String sentiment;
	
	public Review(String textReview) {
		this.textReview = textReview;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getTextReview() {
		return textReview;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((textReview == null) ? 0 : textReview.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (textReview == null) {
			if (other.textReview != null)
				return false;
		} else if (!textReview.equals(other.textReview))
			return false;
		return true;
	}

	@Override
	public int compareTo(Review o) {
		return this.getSentiment().compareTo(o.getSentiment());
	}
	
	
	
}
