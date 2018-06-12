package operative;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;


/**
 * 
 * 
 * @author Pucariello Giovanni
 *
 */
public class CoreNLPObj {

	private Properties props;
	private StanfordCoreNLP pipeline;
	
	private Dizionario terms;
	private List<String> reviews;
	private Set<Term> listForTerm;
	
	private HashMap<Term, List<String>> mapTermWithPositiveReviews;
	private HashMap<Term, List<String>> mapTermWithVeryPositiveReviews;
	private HashMap<Term, List<String>> mapTermWithNegativeReviews;
	private HashMap<Term, List<String>> mapTermWithVeryNegativeReviews;
	
	/**
	 * Constructor class.
	 * 
	 * @param terms
	 * @param item
	 */
	public CoreNLPObj(Dizionario terms, Item item) {
		this.terms = terms;
		listForTerm = new HashSet<>();
		reviews = item.getReviewsFilteredById();
		
		mapTermWithPositiveReviews = new HashMap<>();
		mapTermWithNegativeReviews = new HashMap<>();
		mapTermWithVeryNegativeReviews = new HashMap<>();
		mapTermWithVeryPositiveReviews = new HashMap<>();
	}

	
	/**
	 * creates a StanfordCoreNLP object, with 
	 * POS tagging, lemmatization, NER, parsing, and sentiment
	 */
	public void initialize() {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}
	
	
	
	
	/**
	 * 
	 */
	public void excractTerms() {
		initialize();
		for (String text: reviews) {
			Annotation document = new Annotation(text);
			pipeline.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			for (CoreMap sentence : sentences) {
				String sentimentRecensione = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				if (!sentimentRecensione.equals("Neutral")) {
					for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
						// this is the text of the token
						Term word = new Term(token.get(TextAnnotation.class));
						String pos = token.get(PartOfSpeechAnnotation.class);
						if (pos.equals("NN") || pos.equals("NNS")) {
							listForTerm.add(word);
						}
					}
				}
			}
		}
		
		// remove special characters (like a filter)
		removeSpecialCharacters();
		
	} // end extractTerms() method

	
	
	/**
	 * This method id used to filter and remove not-relevant
	 * terms into the listForTerm list (HashSet<Term>)
	 */
	public void removeSpecialCharacters() {
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
				for (Term term : terms.getNotRelevantTerms()) {
					if (t.getWordForm().equals(term.getWordForm())) {
						it.remove();
					}
				}
			}
		}
		
	} // end removeSpecialCharacters() method

	

	/**
	 * First version Positive
	 */
	public void createMaps() {
		// Populate the map
		for (Term t : listForTerm) {
			mapTermWithPositiveReviews.put(t, new ArrayList<String>());
			mapTermWithVeryPositiveReviews.put(t, new ArrayList<String>());
			mapTermWithNegativeReviews.put(t, new ArrayList<String>());
			mapTermWithVeryNegativeReviews.put(t, new ArrayList<String>());
		}

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
				String frase = sentence.get(TextAnnotation.class);
				String sentimentRecensione = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

				if (sentimentRecensione.equals("Positive")) {
					for (Term term : mapTermWithPositiveReviews.keySet()) {
						if (frase.contains(" " + term.getWordForm() + " ")) {
							mapTermWithPositiveReviews.get(term).add(frase);
						}
					}
				} else if (sentimentRecensione.equals("Very positive")) {
					for (Term term : mapTermWithVeryPositiveReviews.keySet()) {
						if (frase.contains(" " + term.getWordForm() + " ")) {
							mapTermWithVeryPositiveReviews.get(term).add(frase);
						}
					}
				} else if (sentimentRecensione.equals("Negative")) {
					for (Term term : mapTermWithNegativeReviews.keySet()) {
						if (frase.contains(" " + term.getWordForm() + " ")) {
							mapTermWithNegativeReviews.get(term).add(frase);
						}
					}
				} else if (sentimentRecensione.equals("Very negative")) {
					for (Term term : mapTermWithVeryNegativeReviews.keySet()) {
						if (frase.contains(" " + term.getWordForm() + " ")) {
							mapTermWithVeryNegativeReviews.get(term).add(frase);
						}
					}
				}
			}
		}
		
	} // end method

	
	
	/**
	 * Second version Positive
	 */
	public void createMapForPositiveTerm2() {
		
		// creates a StanfordCoreNLP object
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		for (Term t : listForTerm) {
			mapTermWithPositiveReviews.put(t, new ArrayList<String>());
		}

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
				String frase = sentence.get(TextAnnotation.class);
				String sentimentRecensione = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	
				for (Term t : mapTermWithPositiveReviews.keySet()) {
					if (frase.contains(" " + t.getWordForm() + " ") && sentimentRecensione.equals("Positive")) {
						mapTermWithPositiveReviews.get(t).add(frase);
					}
				}

			}

		}
	
	} // end method
	
	
	
	/**
	 * First version Negative
	 */
	public void createMapForNegativeTerm2() {
		
		// creates a StanfordCoreNLP object
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		for (Term t : listForTerm) {
			this.mapTermWithNegativeReviews.put(t, new ArrayList<String>());
		}

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
				String frase = sentence.get(TextAnnotation.class);
				String sentimentRecensione = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				
				for (Term t : mapTermWithNegativeReviews.keySet()) {
					if (frase.contains(" " + t.getWordForm() + " ") && sentimentRecensione.equals("Negative")) {
						this.mapTermWithNegativeReviews.get(t).add(frase);
					}
				}
			}

		}

	} // end method

	
	
	/**
	 * 
	 * @return
	 */
	public HashMap<Term, List<String>> getMapTermWithPositiveReviews() {
		return mapTermWithPositiveReviews;
	}

	
	
} // end Class



