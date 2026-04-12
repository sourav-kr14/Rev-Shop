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
    @DisplayName("Shoud Apply 10% Discount when amount is greater than 5000")
    void testSuccessAddReview()
    {
        int productId=4;

        reviewService.addReview(3,productId,4,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);

    }
@Test
    void testFailAddReview()
    {
        int productId=1;

        reviewService.addReview(3,productId,0,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);
    }
    @Test
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
    void successGetReviews()
    {
        List<Review> reviewList=reviewService.getReviews(4);
        assertNotNull(reviewList);
    }

    @Test
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
