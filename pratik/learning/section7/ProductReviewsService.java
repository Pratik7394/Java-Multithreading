package pratik.learning.section7;
import java.util.*;
import java.util.concurrent.locks.*;

public class ProductReviewsService {
    private final HashMap<Integer, List<String>> productIdToReviews;

    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    Lock readLock = rwLock.readLock();
    Lock writeLock = rwLock.writeLock();

    /********* DO NOT MODIFY THIS SECTION **************/

    public ProductReviewsService() {
        this.productIdToReviews = new HashMap<>();
    }

    /**
     * Adds a product ID if not present
     */
    public void addProduct(int productId) {
        Lock lock = getLockForAddProduct();

        lock.lock();

        try {
            if (!productIdToReviews.containsKey(productId)) {
                productIdToReviews.put(productId, new ArrayList<>());
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a product by ID if present
     */
    public void removeProduct(int productId) {
        Lock lock = getLockForRemoveProduct();

        lock.lock();

        try {
            if (productIdToReviews.containsKey(productId)) {
                productIdToReviews.remove(productId);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds a new review to a product
     * @param productId - existing or new product ID
     * @param review - text containing the product review
     */
    public void addProductReview(int productId, String review) {
        Lock lock = getLockForAddProductReview();

        lock.lock();

        try {
            if (!productIdToReviews.containsKey(productId)) {
                productIdToReviews.put(productId, new ArrayList<>());
            }
            productIdToReviews.get(productId).add(review);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns all the reviews for a given product
     */
    public List<String> getAllProductReviews(int productId) {
        Lock lock = getLockForGetAllProductReviews();

        lock.lock();

        try {
            if (productIdToReviews.containsKey(productId)) {
                return Collections.unmodifiableList(productIdToReviews.get(productId));
            }
        } finally {
            lock.unlock();
        }

        return Collections.emptyList();
    }

    /**
     * Returns the latest review for a product by product ID
     */
    public Optional<String> getLatestReview(int productId) {
        Lock lock = getLockForGetLatestReview();

        lock.lock();

        try {

            if (productIdToReviews.containsKey(productId) && !productIdToReviews.get(productId).isEmpty()) {
                List<String> reviews = productIdToReviews.get(productId);
                return Optional.of(reviews.get(reviews.size() - 1));
            }
        } finally {
            lock.unlock();
        }

        return Optional.empty();
    }

    /**
     * Returns all the product IDs that contain reviews
     */
    public Set<Integer> getAllProductIdsWithReviews() {
        Lock lock = getLockForGetAllProductIdsWithReviews();

        lock.lock();

        try {
            Set<Integer> productsWithReviews = new HashSet<>();
            for (Map.Entry<Integer, List<String>> productEntry : productIdToReviews.entrySet()) {
                if (!productEntry.getValue().isEmpty()) {
                    productsWithReviews.add(productEntry.getKey());
                }
            }
            return productsWithReviews;
        } finally {
            lock.unlock();
        }
    }

    /********* END OF UNMODIFIABLE SECTION **************/


    Lock getLockForAddProduct() {
        return writeLock;
    }

    Lock getLockForRemoveProduct() {
        return writeLock;
    }

    Lock getLockForAddProductReview() {
        return writeLock;
    }

    Lock getLockForGetAllProductReviews() {
        return readLock;
    }

    Lock getLockForGetLatestReview() {
        return readLock;
    }

    Lock getLockForGetAllProductIdsWithReviews() {
        return readLock;
    }
}
