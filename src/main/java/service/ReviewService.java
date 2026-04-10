package service;

import dao.OrderDAO;
import dao.ReviewDAO;
import model.Review;

import java.sql.Timestamp;
import java.util.List;

public class ReviewService {
    private ReviewDAO reviewDAO;
    private OrderDAO orderDAO;

    public ReviewService(ReviewDAO reviewDAO, OrderDAO orderDAO) {
        this.reviewDAO = reviewDAO;
        this.orderDAO = orderDAO;
    }
    public void addReview(int userId,int productId,int rating ,String  comment)
    {
        if(rating <1 || rating >5)
        {
            System.out.println("Rating must be between 1 and 5");
            return;
        }
        Review review= new Review(0,userId,productId,rating , comment,new Timestamp(System.currentTimeMillis()));
        reviewDAO.addReview(review);
    }


    public List<Review> getReviews(int productId)
    {
        return reviewDAO.getReviewByProductId(productId);
    }

    public double getAverageRating(int productId)
    {
        return reviewDAO.getAverageRating(productId);
    }
}
