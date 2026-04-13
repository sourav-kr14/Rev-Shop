package dao;

import model.Review;

import java.util.List;

public interface ReviewDAO {
    boolean addReview(Review review);
    List<Review> getReviewByProductId(int productId);
    double getAverageRating(int productId);



}
