package operative;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.EmptyListException;
import exceptions.ObjectAlreadyAddedException;
import operative.Term.Relevance;

/**
 * This class is used to store relevant and not-relevant terms into two
 * separated lists.
 * 
 * @author Pucariello Giovanni
 *
 */
public class Dizionario {

	ArrayList<Term> relevantTerms;
	ArrayList<Term> notRelevantTerms;

	/**
	 * Class constructor.
	 */
	public Dizionario() {
		relevantTerms = new ArrayList<>();
		notRelevantTerms = new ArrayList<>();
	}

	/**
	 * Get method that return a list of relevant terms.
	 * 
	 * @return relevantTerms
	 */
	public ArrayList<Term> getRelevantTerms() {
		return relevantTerms;
	}

	/**
	 * Set method that modifies the list of relevant terms.
	 * 
	 * @param relevantTerms
	 */
	public void setRelevantTerms(ArrayList<Term> relevantTerms) {
		this.relevantTerms = relevantTerms;
	}

	/**
	 * Get method that return a list of not-relevant terms.
	 * 
	 * @return notRelevantTerms
	 */
	public ArrayList<Term> getNotRelevantTerms() {
		return notRelevantTerms;
	}

	/**
	 * Set method that modifies the list of not-relevant terms.
	 * 
	 * @param notRelevantTerms
	 */
	public void setNotRelevantTerms(ArrayList<Term> notRelevantTerms) {
		this.notRelevantTerms = notRelevantTerms;
	}

	/**
	 * This method adds relevant and not-relevant terms.
	 * 
	 * @param term
	 * @throws ObjectAlreadyAddedException
	 */
	public void addTerm(Term term) throws ObjectAlreadyAddedException {
		if (term.getRelevance().equals(Relevance.NotRelevant)) {
			if (notRelevantTerms.contains(term)) {
				throw new ObjectAlreadyAddedException();
			} else {
				notRelevantTerms.add(term);
			}
		} else if (term.getRelevance().equals(Relevance.Relevant)) {
			if (relevantTerms.contains(term)) {
				throw new ObjectAlreadyAddedException();
			} else {
				relevantTerms.add(term);
			}
		}
	} // end addTerm() method

	/**
	 * This method removes a relevant or not-relevant term.
	 * 
	 * @param term
	 * @throws EmptyListException
	 */
	public void removeTerm(Term term) throws EmptyListException {
		if (term.getRelevance().equals(Relevance.NotRelevant)) {
			if (notRelevantTerms.isEmpty()) {
				throw new EmptyListException("Error: the list of not-relevant terms is empty!");
			} else {
				if (notRelevantTerms.contains(term)) {
					notRelevantTerms.remove(term);
				}
			}
		} else if (term.getRelevance().equals(Relevance.Relevant)) {
			if (relevantTerms.isEmpty()) {
				throw new EmptyListException("Error: the list of relevant terms is empty!");
			} else {
				if (relevantTerms.contains(term)) {
					relevantTerms.remove(term);
				}
			}
		}
	} // end removeTerm() method

	/**
	 * This method returns a listIterator of the relevant terms
	 * 
	 * @return relevantTerms.listIterator()
	 */
	public Iterator<Term> relevantTermsIterator() {
		return relevantTerms.listIterator();
	}

	/**
	 * This method returns a listIterator of the not-relevant terms
	 * 
	 * @return notRelevantTerms.listIterator();
	 */
	public Iterator<Term> notRelevantTermsIterator() {
		return notRelevantTerms.listIterator();
	}

	/**
	 * Override of toString() method
	 * 
	 * @return Dizionario.toString()
	 */
	@Override
	public String toString() {
		return "Dizionario [Termini Rilevanti: " + getRelevantTerms() + "\nTermini Non Rilevanti: "
				+ getNotRelevantTerms() + "]";
	}

} // end Class
