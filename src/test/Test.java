package test;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import operative.CoreNLPObj;
import operative.Dizionario;
import operative.Item;
import operative.Lettore;
import operative.Term;

/**
 * Class used for testing.
 * 
 * @author Pucariello Giovanni
 * @author Plantone Vincenzo
 *
 */
public class Test {

	/**
	 * Default private constructor.
	 */
	private Test() {
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Creating input files
		File termsRel = new File("termRelevant.txt"); // Input file that contains relevant terms
		File termsNotRel = new File("termNotRelevant.txt"); // Input file that contains not relevant terms
		File itemsBookRed = new File("list_items_book(reduced).txt"); // Input file that contains a list of items with
																		// relative title
		File recensioni = new File("recensioni.txt"); // Input file that contains reviews
		// File listaFilmAsin = new File("lista_film_asin_numreviews.txt");

		// Create a new Item identified by id
		Item item = new Item("I:1034");

		// Dictionary that contains relevant and not relevant terms
		Dizionario terms = new Dizionario();

		// Create a Lettore object that reads and stores data from input files
		Lettore lettore = new Lettore(item, terms);
		lettore.readTerms(termsNotRel);
		lettore.readTerms(termsRel);
		lettore.readTitle(itemsBookRed);
		lettore.readReviews(recensioni);

		// Create a CoreNLPObj
		CoreNLPObj o = new CoreNLPObj(terms, item);

		// Extracting terms
		o.excractTerms();

		// Create a map for positive terms
		o.createMaps();

		// Print each terms of the map with relative list of reviews
		for (Entry<Term, List<String>> e : o.getMapTermWithPositiveReviews().entrySet()) {
			System.out.println(
					"===============================================================================================");
			System.out.println(e.getKey() + " " + e.getValue());
			for (String frase : e.getValue()) {
				System.out.println("\nReview: " + frase);
				o.getSentimentResult(frase);
			}
		}

		// Print item's title
		System.out.println("\n" + item.getTitle());

		// Print a list of relevant terms
		System.out.println("Term Relevant: ------->" + terms.getRelevantTerms().toString());

		// Print a list of not relevant terms
		System.out.println("Term Not Relevant: ------->" + terms.getNotRelevantTerms().toString());

		// Print the number of reviews
		System.out.println("Num of reviews:-------->" + item.getReviewsFilteredById().size());

	}

}
