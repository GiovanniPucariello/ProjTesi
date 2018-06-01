package operative;

import java.util.ArrayList;

import exceptions.EmptyListException;
import exceptions.ObjectAlreadyAddedException;
import exceptions.ReviewNotExistsException;

/**
 * This class is used to store informations about items.
 * 
 * @author Pucariello Giovanni
 *
 */
public class Item {

	private String id;
	private String title;
	private ArrayList<String> reviewsFilteredById;

	/**
	 * Class constructor.
	 * 
	 * @param id
	 */
	public Item(String id) {
		this.id = id;
		title = null;
		reviewsFilteredById = new ArrayList<>();
	}

	
	
	/**
	 * Get method that returns a string that represent the title of the item.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set method that modifies the item's title.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	
	
	/**
	 * Get method that returns a list of reviews filtered by id.
	 * 
	 * @return reviewsFilteredById
	 */
	public ArrayList<String> getReviewsFilteredById() {
		return reviewsFilteredById;
	}

	/**
	 * Set method that modifies the item's list of reviews.
	 * 
	 * @param reviewsFilteredById
	 */
	public void setReviewsFilteredById(ArrayList<String> reviewsFilteredById) {
		this.reviewsFilteredById = reviewsFilteredById;
	}

	
	
	/**
	 * Get method that returns a string that represent the item's id
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	
	
	/**
	 * This method adds a review.
	 * 
	 * @param review
	 * @throws ObjectAlreadyAddedException
	 */
	public void addReview(String review) throws ObjectAlreadyAddedException {
		if (reviewsFilteredById.contains(review)) {
			throw new ObjectAlreadyAddedException();
		} else {
			reviewsFilteredById.add(review);
		}

	}

	/**
	 * This method removes a review.
	 * 
	 * @param review
	 * @throws EmptyListException
	 * @throws ReviewNotExists
	 */
	public void removeReview(String review) throws EmptyListException, ReviewNotExistsException {
		if (reviewsFilteredById.isEmpty()) {
			throw new EmptyListException();
		} else if (reviewsFilteredById.contains(review)) {
			reviewsFilteredById.remove(review);
		} else {
			throw new ReviewNotExistsException();
		}
	}

	
	
	/**
	 * Override of toString() method
	 * 
	 * @return Item.toString()
	 */
	@Override
	public String toString() {
		return "Item [I: " + getId() + ",	Title:" + getTitle() + ",	Reviews:" + getReviewsFilteredById() + "]";
	}

	
	
} // end Class
