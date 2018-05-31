package operative;

import java.io.File;
import java.io.IOException;
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
	 * This method reads data from input files.
	 * 
	 * @param file
	 */
	public void read(File file) {
		String line = null;
		String parts[] = null;
		try {
			s = new Scanner(file);
			if(file.getName().equals("recensioni.txt")) {
				while (s.hasNextLine()) {
					line = s.nextLine();
					parts = line.split("\t");
					if (parts[0].equals(item.getId())) {
						try {
							item.addReview(parts[3]);
						} catch (ObjectAlreadyAddedException e) {
							System.out.println("Error while adding review: " + parts[3]);
						}
					}
				}
			}else if(file.getName().equals("list_items_book(reduced).txt")){
				while (s.hasNextLine()) {
					line = s.nextLine();
					parts = line.split("\t");
					if (parts[0].equals(item.getId())) {
						item.setTitle(parts[1]);
					}
				}
			} else if(file.getName().equals("termNotRelevant.txt")) {
				while (s.hasNext()) {
					try {
						terms.addTerm(new Term(s.next(), Relevance.NotRelevant));
					} catch (ObjectAlreadyAddedException e) {
						System.out.println("Error: not-relevant term already added!");
					}
				}
			} else if(file.getName().equals("termRelevant.txt")){
				while (s.hasNext()) {
					try {
						terms.addTerm(new Term(s.next(), Relevance.Relevant));
					} catch (ObjectAlreadyAddedException e) {
						System.out.println("\"Error: relevant term already added!\"");
					}
				}
			}
		}catch(IOException e) {
			System.out.println("Error accessing input file: " + file.getName());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	} // end read() method
	
	
	
} // end Class
