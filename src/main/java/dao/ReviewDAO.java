package dao;

import model.Review;

import java.util.List;

public interface ReviewDAO {
    void addReview(Review review);
    List<Review> getReviewByProductId(int productId);
    double getAverageRating(int productId);



}
