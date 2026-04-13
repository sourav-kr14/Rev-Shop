import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.ReviewDAO;
import dao.ReviewDAOImpl;
import model.Review;
import org.junit.jupiter.api.*;
import service.ReviewService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewServiceTest {
    private ReviewService reviewService;


    @BeforeEach
    void begin()
    {
        ReviewDAO reviewDAO=new ReviewDAOImpl();
        OrderDAO orderDAO= new OrderDAOImpl();
        reviewService= new ReviewService(reviewDAO,orderDAO);
    }
    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Review Service Test Suite ====");
    }

    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Review Service Test Suite ====");

    }


    @Test
    @Order(1)
    @DisplayName("Test to check the success to add review")
    void testSuccessAddReview()
    {
        int productId=4;

        reviewService.addReview(3,productId,4,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);

    }
    @Test
    @Order(2)
    @DisplayName("Test to check review with rating 0")
    void testFailAddReview()
    {
        int productId=1;
        reviewService.addReview(3,productId,0,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);
    }
    @Test
    @Order(3)
    @DisplayName("Test to check review with rating greater than 5")
    void testAddRatingGreaterThan5()
    {
        int productId=4;
        reviewService.addReview(3,productId,9,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);
        boolean foundInvalid = reviewList.stream()
                .anyMatch(r -> r.getRating() == 6);

        assertFalse(foundInvalid);
    }

    @Test
    @Order(4)
    @DisplayName("Test to get reviews")
    void successGetReviews()
    {
        List<Review> reviewList=reviewService.getReviews(4);
        assertNotNull(reviewList);
    }

    @Test
    @Order(5)
    @DisplayName("Test to get average rating")
    void testAverageRating()
    {
        int userid=4;
        int productId=4;
        reviewService.addReview(userid,productId,4,"Best");
        reviewService.addReview(userid,productId,4,"Worst");
        double avg_Rating=reviewService.getAverageRating(4);
        assertTrue(avg_Rating >=0 && avg_Rating<=5);

    }

}
