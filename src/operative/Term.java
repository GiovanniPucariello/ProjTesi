package operative;

import java.util.List;

/**
 * This class is used to represent terms
 * and store their attributes.
 * 
 * @author Pucariello Giovanni
 *
 */
public class Term {

	public enum Relevance {Relevant, NotRelevant}
	
	public Relevance getRelevance() {
		return relevance;
	}

	public void setRelevance(Relevance relevance) {
		this.relevance = relevance;
	}

	private Relevance relevance;
	private String wordForm;
	private int numReviews;
	private int df;
	private double tf;
	private double idf;
	private double tfIdf;
	private double score;
	private double scoreIdf;


	/**
	 * 
	 * @param wordform
	 */
	public Term(String wordform) {
		this(wordform, null);
		numReviews = 0;
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param wordForm
	 * @param relevance
	 */
	public Term(String wordForm, Relevance relevance) {
		this.wordForm = wordForm;
		this.relevance = relevance;
		numReviews = 0;
		score = 0;
		tfIdf = 0;
		scoreIdf = 0;
	}
	
	
	
	public String getWordForm() {
		return wordForm;
	}

	/**
	 * This method calculates the score using the value of the total sentiment.
	 * 
	 * @param totalSentiment
	 */
	public void calculateScore(int totalSentiment) {
	//	score = totalSentiment / counter;
	}

	/**
	 * This method calculates the Idf (Inverse Document Frequency).
	 * 
	 * @param reader
	 */
	public void calculateIdf(Lettore reader) {
		//idf = Math.log10((reader.getCounterReviewId() / counter));

	}

	/**
	 * This method calculates the TfIdf (Term Frequency_Inverse Document Frequency).
	 */
	public void calculateTfIdf() {
		tfIdf = tf * idf;
	}

	/**
	 * This method calculates the tf (Term Frequency)
	 * 
	 * @param reviewForTerm
	 */
	public void calculateTf(List<String> reviewForTerm) {
		int numParole = 0;
		int countTerm = 0;

		for (String s : reviewForTerm) {
			String[] splitter = s.split(" ");
			numParole = splitter.length + numParole;
		}
		for (String s : reviewForTerm) {
			if (s.contains(wordForm)) {
				countTerm++;
			}
		}
		tf = countTerm / numParole;

	}

	/**
	 * This method calculates the final score.
	 */
	public void calculateFinalScore() {
		scoreIdf = score * tfIdf;

	}

	/**
	 * Override of hashCode() method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordForm == null) ? 0 : wordForm.hashCode());
		return result;
	}

	/**
	 * Override of equals() method
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term) obj;
		if (wordForm == null) {
			if (other.wordForm != null)
				return false;
		} else if (!wordForm.equals(other.wordForm))
			return false;
		return true;
	}
	
	/**
	 * Overrided toString() method
	 */
	@Override
	public String toString() {
		return "Term [Word Form= " + wordForm + "]";
	}

} //end Class
