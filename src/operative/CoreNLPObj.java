package operative;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLPObj {

	private Lettore reader = null;
	private HashSet<Term> listForTerm = null;
	private HashMap<Term, List<String>> mapTermWithPositiveReviews;
	private HashMap<Term, List<String>> mapTermWithVeryPositiveReviews;
	private HashMap<Term, List<String>> mapTermWithNegativeReviews;
	private HashMap<Term, List<String>> mapTermWithVeryNegativeReviews;
	private List<String> reviews = null;

	public CoreNLPObj(Lettore reader) {
		this.reader = reader;
		this.listForTerm = new HashSet<>();
		this.mapTermWithPositiveReviews = new HashMap<>();
		this.mapTermWithNegativeReviews = new HashMap<>();
		this.mapTermWithVeryNegativeReviews = new HashMap<>();
		this.mapTermWithVeryPositiveReviews = new HashMap<>();
		this.reviews = reader.getReviewFilteredById();

	}

	public void excractTerm() {

		// creates a StanfordCoreNLP object
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		for (int i = 0; i < reviews.size(); i++) {
			String text = reviews.get(i);
			// create an empty Annotation just with the given text
			Annotation document = new Annotation(text);
			// run all Annotators on this text
			pipeline.annotate(document);
			// these are all the sentences in this document
			// a CoreMap is essentially a Map that uses class objects as keys and has values
			// with custom types
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for (CoreMap sentence : sentences) {

				// inizio prova
				// System.out.println("\nSentiment of sentence\n");
				String sentimentRecensione = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				// System.out.println(mamma + "\n\n");
				// fine prova

				if (sentimentRecensione.equals("Positive") || sentimentRecensione.equals("Very positive")
						|| sentimentRecensione.equals("Negative") || sentimentRecensione.equals("Very Negative")) {

					// traversing the words in the current sentence
					// a CoreLabel is a CoreMap with additional token-specific methods

					for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

						// this is the text of the token
						String word = token.get(TextAnnotation.class);
						// this is the POS tag of the token
						String pos = token.get(PartOfSpeechAnnotation.class);
						// this is the NER label of the token
						// String ne = token.get(NamedEntityTagAnnotation.class);

						// inizio prova hashmap
						if (pos.equals("NN") || pos.equals("NNS")) {
							this.listForTerm.add(new Term(word));
						}

					}
				}
			}
		}

		this.removeTermsNotRelevant();
	}

	public void removeTermsNotRelevant() {

		Iterator<Term> it = listForTerm.iterator();
		while (it.hasNext()) {
			Term t = it.next();
			if (t.getWordForm().contains(".") || t.getWordForm().contains("%") || t.getWordForm().contains("'")
					|| t.getWordForm().contains("?") || t.getWordForm().contains("!") || t.getWordForm().contains("\"")
					|| t.getWordForm().contains("-") || t.getWordForm().contains("/")
					|| t.getWordForm().contains("_")) {
				it.remove();
			} else if (t.getWordForm().length() <= 2) {
				it.remove();
			} else if (t.getWordForm().matches(".*\\d+.*")) {
				it.remove();
			} else {
				for (String s : reader.getTermNotRelevant()) {
					if (t.getWordForm().equals(s)) {
						it.remove();
					}
				}
			}
		}
	}

	public void createMapForPositiveTerm() {
		// creates a StanfordCoreNLP object
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		for (int i = 0; i < reviews.size(); i++) {
			String text = reviews.get(i);
			// create an empty Annotation just with the given text
			Annotation document = new Annotation(text);
			// run all Annotators on this text
			pipeline.annotate(document);
			// these are all the sentences in this document
			// a CoreMap is essentially a Map that uses class objects as keys and has values
			// with custom types
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for (CoreMap sentence : sentences) {

				// inizio prova
				// System.out.println("\nSentiment of sentence\n");
				String sentimentRecensione = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				// System.out.println(mamma + "\n\n");
				// fine prova

				if (sentimentRecensione.equals("Positive")) {
					for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

						// this is the text of the token
						String word = token.get(TextAnnotation.class);
						// this is the POS tag of the token
						String pos = token.get(PartOfSpeechAnnotation.class);
						// this is the NER label of the token
						// String ne = token.get(NamedEntityTagAnnotation.class);

						if (pos.equals("NN") || pos.equals("NNS")) {
							for (Term t : listForTerm) {
								if (t.getWordForm().equals(word)) {

								}
							}
						}
					}
				}
			}
		}

	}

	public HashSet<Term> getListForTerm() {
		return listForTerm;
	}

	public HashMap<Term, List<String>> getMapTermWithPositiveReviews() {
		return mapTermWithPositiveReviews;
	}

	public HashMap<Term, List<String>> getMapTermWithVeryPositiveReviews() {
		return mapTermWithVeryPositiveReviews;
	}

	public HashMap<Term, List<String>> getMapTermWithNegativeReviews() {
		return mapTermWithNegativeReviews;
	}

	public HashMap<Term, List<String>> getMapTermWithVeryNegativeReviews() {
		return mapTermWithVeryNegativeReviews;
	}

	public List<String> getReviews() {
		return reviews;
	}

	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}

}
