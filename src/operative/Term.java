package operative;

import java.util.List;

public class Term {

	private String wordForm;
	private int counter;
	private double score;
	private double tfIdf;
	private double scoreIdf;
	private double tf;
	private double idf;
	private int numDocs;

	public Term(String wordForm) {
		this.wordForm = wordForm;
		this.counter = 0;
		this.score = 0;
		this.tfIdf = 0;
		this.scoreIdf = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordForm == null) ? 0 : wordForm.hashCode());
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
		Term other = (Term) obj;
		if (wordForm == null) {
			if (other.wordForm != null)
				return false;
		} else if (!wordForm.equals(other.wordForm))
			return false;
		return true;
	}

	public String getWordForm() {
		return wordForm;
	}

	public void setWordForm(String wordForm) {
		this.wordForm = wordForm;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public double getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public double getTfIdf() {
		return tfIdf;
	}

	public void setTfIdf(float tfIdf) {
		this.tfIdf = tfIdf;
	}

	public double getScoreIdf() {
		return scoreIdf;
	}

	public void setScoreIdf(float scoreIdf) {
		this.scoreIdf = scoreIdf;
	}

	public double getTf() {
		return tf;
	}

	public double getIdf() {
		return idf;
	}

	public void incCounter() {
		this.counter++;
	}

	public void calculateScore(int totalSentiment) {
		this.score = totalSentiment / counter;
	}

	public void calculateIdf(Lettore reader) {
		this.idf = Math.log10((reader.getCounterReviewId() / counter));

	}

	public void calculateTfIdf() {
		this.tfIdf = this.tf * this.idf;
	}

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

	public void calculateFinalScore() {

		this.scoreIdf = this.score * this.tfIdf;

	}

	public int getNumDocs() {
		return numDocs;
	}

	public void setNumDocs(List<String> reviewForTerm) {
		this.numDocs = reviewForTerm.size();
	}

	@Override
	public String toString() {
		return "Term [Word Form= " + wordForm + "]";
	}

}
