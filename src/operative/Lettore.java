package operative;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exceptions.ObjectAlreadyAddedException;
import operative.Term.Relevance;

/**
 * This class is used to read and storage input data from files.
 * 
 * @author Pucariello Giovanni
 *
 */
public class Lettore {

	private Item item;
	private Dizionario terms;
	private Scanner s;

	/**
	 * Class constructor.
	 * 
	 * @param item
	 * @param terms
	 */
	public Lettore(Item item, Dizionario terms) {
		this.item = item;
		this.terms = terms;
		s = null;
	}

	/**
	 * This method is used to initialize the Scanner object.
	 * 
	 * @param file
	 */
	public void open(File file) {
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error accessing input file: " + file.getName());
		}
	}

	/**
	 * This method is used to get the title of the item from file.
	 * 
	 * @param file
	 */
	public void readTitle(File file) {
		open(file);
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String parts[] = line.split("\t");
			if (parts[0].equals(item.getId())) {
				item.setTitle(parts[1]);
			}
		}
		close();
	}

	/**
	 * This method is used to put reviews (that are read from file) into the item's
	 * list.
	 * 
	 * @param file
	 */
	public void readReviews(File file) {
		open(file);
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String parts[] = line.split("\t");
			if (parts[0].equals(item.getId())) {
				try {
					item.addReview(parts[3]);
				} catch (ObjectAlreadyAddedException e) {
					System.out.println("Error while adding review: " + parts[3]);
				}
			}
		}
		close();
	}

	/**
	 * This method is used to read relevant and not relevant terms from file
	 * 
	 * @param file
	 */
	public void readTerms(File file) {
		open(file);
		try {
			if (file.getName().equals("termNotRelevant.txt")) {
				while (s.hasNext()) {
					terms.addTerm(new Term(s.next(), Relevance.NotRelevant));
				}
			} else if (file.getName().equals("termRelevant.txt")) {
				while (s.hasNext()) {
					terms.addTerm(new Term(s.next(), Relevance.Relevant));
				}
			}
		} catch (ObjectAlreadyAddedException e) {
			System.out.println("Error: term already added!");
		}
		close();
	}

	/**
	 * This method closes the Scanner object used to read from file.
	 */
	public void close() {
		s.close();
	}

} // end Class
