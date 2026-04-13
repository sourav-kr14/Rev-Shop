package dao;

import model.Review;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements  ReviewDAO{

    @Override
    public boolean addReview(Review review) {
        String query="Insert into reviews(user_id,product_id,rating,comment) values(?,?,?,?)";
        try(Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,review.getUserId());
            preparedStatement.setInt(2,review.getProductId());
            preparedStatement.setInt(3,review.getRating());
            preparedStatement.setString(4,review.getComment());
            int rowsInserted=preparedStatement.executeUpdate();
            if (rowsInserted>0)
            {
                System.out.println("Your product review was successfull");
            }
            else
            {
                System.out.println("Review not added");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while adding review");
        }
        return true;
    }

    @Override
    public List<Review> getReviewByProductId(int productId) {
        List <Review> reviews= new ArrayList<>();
        String query="Select * from reviews where product_id=?";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setInt(1,productId);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next())
            {
                reviews.add(extractReviews(resultSet));

            }

        }
        catch (SQLException e)
        {
            System.out.println("Unable to fetch review for product with Product Id"+productId);
            e.printStackTrace();
        }
        return  reviews;
    }

    @Override
    public double getAverageRating(int productId) {
        String query="Select avg(rating) from reviews where product_id=?";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setInt(1,productId);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next())
            {
                return resultSet.getDouble(1);

            }

        }
        catch (SQLException e)
        {
            System.out.println("Unable to fetch average review for product with Product Id"+productId);
            e.printStackTrace();
        }
        return  0;
    }

    Review extractReviews(ResultSet resultSet) throws  SQLException
    {
        return  new Review(resultSet.getInt("review_id"),resultSet.getInt("user_id"),resultSet.getInt("product_id"),resultSet.getInt("rating"),resultSet.getString("comment"),resultSet.getTimestamp("created_at"));
    }
}
