package operative;

import java.util.List;

/**
 * This class is used to represent terms
 * and store their attributes.
 * 
 * @author Plantone Vincenzo
 * @author Pucariello Giovanni
 *
 */
public class Term {

	private String wordForm;
	private int counter;
	private double score;
	private double tfIdf;
	private double scoreIdf;
	private double tf;
	private double idf;
	private int numDocs;

	/**
	 * Class constructor.
	 * 
	 * @param wordForm
	 */
	public Term(String wordForm) {
		this.wordForm = wordForm;
		counter = 0;
		score = 0;
		tfIdf = 0;
		scoreIdf = 0;
	}

	/**
	 * Get method of the variable "wordForm"
	 * 
	 * @return a string that represent a wordForm
	 */
	public String getWordForm() {
		return wordForm;
	}

	/**
	 * Set method of the variable "wordForm"
	 * 
	 * @param wordForm
	 */
	public void setWordForm(String wordForm) {
		this.wordForm = wordForm;
	}

	/**
	 * Get method of the variable "counter"
	 * 
	 * @return the value of the counter variable
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Set method of the variable "counter"
	 * 
	 * @param counter
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * Get method of the variable "score"
	 * 
	 * @return the value (double) of the score variable
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Set method of the variable "score"
	 * 
	 * @param score
	 */
	public void setScore(float score) {
		this.score = score;
	}

	/**
	 * Get method of the variable "tfIdf"
	 * 
	 * @return the value of the tfIdf variable
	 */
	public double getTfIdf() {
		return tfIdf;
	}

	/**
	 * Set method of the variable "tfIdf"
	 * 
	 * @param tfIdf
	 */
	public void setTfIdf(float tfIdf) {
		this.tfIdf = tfIdf;
	}

	/**
	 * Get method of the variable "scoreIdf"
	 * 
	 * @return the value of the scoreIdf (double) variable
	 */
	public double getScoreIdf() {
		return scoreIdf;
	}

	/**
	 * Set method of the variable "scoreIdf"
	 * 
	 * @param scoreIdf
	 */
	public void setScoreIdf(float scoreIdf) {
		this.scoreIdf = scoreIdf;
	}

	/**
	 * Get method of the variable "tf"
	 * 
	 * @return the value of the tf variable
	 */
	public double getTf() {
		return tf;
	}
	
	/**
	 * Get method of the variable "idf"
	 * 
	 * @return the value of the idf variable
	 */
	public double getIdf() {
		return idf;
	}

	
	
	
	/**
	 * It increment the value of the variable counter
	 */
	public void incCounter() {
		counter++;
	}

	
	
	
	
	/**
	 * This method calculates the score using the value of the total sentiment.
	 * 
	 * @param totalSentiment
	 */
	public void calculateScore(int totalSentiment) {
		score = totalSentiment / counter;
	}

	/**
	 * This method calculates the Idf (Inverse Document Frequency).
	 * 
	 * @param reader
	 */
	public void calculateIdf(Lettore reader) {
		idf = Math.log10((reader.getCounterReviewId() / counter));

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
		int counterTerm = 0;

		for (String s : reviewForTerm) {
			String[] splitter = s.split(" ");
			numParole = splitter.length + numParole;
		}

		for (String s : reviewForTerm) {
			if (s.contains(this.getWordForm())) {
				counterTerm++;
			}
		}

		this.tf = counterTerm / numParole;

	}

	/**
	 * This method calculates the final score.
	 */
	public void calculateFinalScore() {
		scoreIdf = score * tfIdf;

	}

	/**
	 * Get method of the variable "numDocs".
	 * 
	 * @return the number of the documents
	 */
	public int getNumDocs() {
		return numDocs;
	}

	/**
	 * Set method of the variable "numDocs".
	 * 
	 * @param reviewForTerm
	 */
	public void setNumDocs(List<String> reviewForTerm) {
		this.numDocs = reviewForTerm.size();
	}

	/**
	 * Overrided hashCode() method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordForm == null) ? 0 : wordForm.hashCode());
		return result;
	}

	/**
	 * Overrided equals() method
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
