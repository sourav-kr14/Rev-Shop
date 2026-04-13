package service;

import dao.OrderDAO;
import dao.ReviewDAO;
import exception.InvalidRatingException;
import exception.ReviewException;
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
           throw new InvalidRatingException("Rating must be between 1 and 5 only");
        }
        Review review= new Review(0,userId,productId,rating , comment,new Timestamp(System.currentTimeMillis()));
        boolean success=reviewDAO.addReview(review);
        if(!success)
        {
            throw new ReviewException("Unable to add review");
        }
    }


    public List<Review> getReviews(int productId)
    {
        List<Review> reviewList= reviewDAO.getReviewByProductId(productId);
        if(reviewList == null)
        {
            throw  new ReviewException("Failed to get reviews with product id"+productId);
        }
        return reviewList;
    }

    public double getAverageRating(int productId)
    {
        double avg= reviewDAO.getAverageRating(productId);
        if(avg<0)
        {
            throw new ReviewException("Invalid Average rating");
        }
        return avg;
    }
}
