package operative;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to read and storage input data from files.
 * 
 * @author Plantone Vincenzo
 * @author Pucariello Giovanni
 *
 */
public class Lettore {

	private String idItem;
	private String titoloItem;
	private ArrayList<String> reviewFilteredById;
	private ArrayList<String> termNotRelevant;
	private ArrayList<String> termRelevant;
	private int counterReviewId;
	private Scanner s;

	/**
	 * Class constructor.
	 */
	public Lettore(String idItem) {
		this.idItem = idItem;
		titoloItem = null;
		reviewFilteredById = new ArrayList<>();
		termNotRelevant = new ArrayList<>();
		termRelevant = new ArrayList<>();
		counterReviewId = 0;
		s = null;
	}

	/**
	 * Get method of the variable "counterReviewId".
	 * 
	 * @return the integer value of the variable counterReviewId.
	 */
	public int getCounterReviewId() {
		return counterReviewId;
	}

	/**
	 * Get method of the variable "idItem".
	 * 
	 * @return a string that represent the Item's id.
	 */
	public String getIdItem() {
		return idItem;
	}

	/**
	 * Set method of the variable "idItem".
	 * 
	 * @param idItem
	 */
	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}

	/**
	 * Get method of the variable "titoloItem".
	 * 
	 * @return a string that represent the Item's title.
	 */
	public String getTitoloItem() {
		return titoloItem;
	}

	/**
	 * Set method of the variable "titoloItem".
	 * 
	 * @param titoloItem
	 */
	public void setTitoloItem(String titoloItem) {
		this.titoloItem = titoloItem;
	}

	/**
	 * Get method of the list "reviewFilteredById".
	 * 
	 * @return a list that contains reviews filtered by id.
	 */
	public ArrayList<String> getReviewFilteredById() {
		return reviewFilteredById;
	}

	/**
	 * Set method of the list "reviewFilteredById".
	 * 
	 * @param reviewFilteredById
	 */
	public void setReviewFilteredById(ArrayList<String> reviewFilteredById) {
		this.reviewFilteredById = reviewFilteredById;
	}

	/**
	 * Get method of the list "termNotRelevant".
	 * 
	 * @return a list that contains not relevant terms
	 */
	public ArrayList<String> getTermNotRelevant() {
		return termNotRelevant;
	}

	/**
	 * Set method of the list "termNotRelevant".
	 * 
	 * @param termNotRelevant
	 */
	public void setTermNotRelevant(ArrayList<String> termNotRelevant) {
		this.termNotRelevant = termNotRelevant;
	}

	/**
	 * Get method of the list "termRelevant".
	 * 
	 * @return a list that contains relevant terms.
	 */
	public ArrayList<String> getTermRelevant() {
		return termRelevant;
	}

	/**
	 * Set method of the list "termRelevant".
	 * 
	 * @param termRelevant
	 */
	public void setTermRelevant(ArrayList<String> termRelevant) {
		this.termRelevant = termRelevant;
	}

	/**
	 * Get method that takes idItem and sets the list of reviews for item
	 */
	public void getReviewId() {
		String review = null;
		String line = null;
		counterReviewId = 0;

		/*
		 * try-block that controls the access to the input file "recensioni.txt".
		 */
		try {
			s = new Scanner(new File("recensioni.txt"));
			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] parts = line.split("\t");
				if (parts[0].equals(idItem)) {
					review = parts[3];
					reviewFilteredById.add(review);
					counterReviewId++;
				}
			}
		} catch (IOException e) {
			System.out.println("Error accessing input file!");
		} finally {
			if (s != null) {
				s.close();
			}
		}

	} // end getReviewId() method

	/**
	 * Take idItem and set the title
	 */
	public void getTitleId() {
		String line = null;
		/*
		 * try-block that controls the access to the input file
		 * "list_items_book(reduced).txt".
		 */
		try {
			s = new Scanner(new File("list_items_book(reduced).txt"));
			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] parts = line.split("\t");
				if (parts[0].equals(idItem)) {
					titoloItem = parts[1];
				}
			}
		} catch (IOException e) {
			System.out.println("Error accessing input file!");
		} finally {
			s.close();
		}

	} // end getTitleId() method

	/**
	 * Read Relevant and Not Relevant terms from file and add them in
	 * termNotRelevant and termRelevant (ArrayList<String>)
	 * 
	 * @param f
	 */
	public void readTerms(File f) {

		/*
		 * try-block that controls the access to the input file "termNotRelevant.txt".
		 */
		try {
			if (f.getName().equals("termNotRelevant.txt")) {
				s = new Scanner(f);
				while (s.hasNext()) {
					this.termNotRelevant.add(s.next());
				}
			} else {
				s = new Scanner(f);
				while (s.hasNext()) {
					this.termRelevant.add(s.next());
				}
			}
		} catch (IOException e) {
			System.out.println("Error accessing input file!");
		} finally {
			if (s != null) {
				s.close();
			}
		}

	} // end readTerms() method

} // end Class
