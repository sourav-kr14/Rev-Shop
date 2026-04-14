import dao.*;
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
        ProductDAO productDAO=new ProductDAOImpl();
        reviewService= new ReviewService(reviewDAO,orderDAO,productDAO );
    }
    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Review Service Test Suite ====");
    }
    @Test
    @Order(1)
    @DisplayName("Test to check the success to add review")
    void givenCorrectInput_WhenUserAddReview_ThenSuccess()
    {
        int productId=4;
        reviewService.addReview(3,productId,4,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);
        assertFalse(reviewList.isEmpty());
        Review review=reviewList.getLast();
        assertEquals(3,review.getUserId());
        assertEquals(4,review.getRating());
        assertEquals("Best Product",review.getComment());
    }
    @Test
    @Order(2)
    @DisplayName("Test to check review with rating 0")
    void givenInvalidRating_WhenUserAddReview_ThenAddReviewFailed()
    {
        int productId=1;
        int size_before=reviewService.getReviews(productId).size();
        reviewService.addReview(3,productId,0,"BestProduct");
        int size_after=reviewService.getReviews(productId).size();
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);
        assertEquals(size_before,size_after);
    }
    @Test
    @Order(3)
    @DisplayName("Test to check review with rating greater than 5")
    void givenInvalidRating_WhenUserAddReview_ThenThrowsException()
    {
        int productId=4;
        int size_before=reviewService.getReviews(productId).size();
        reviewService.addReview(3,productId,9,"BestProduct");
        List<Review> reviewList= reviewService.getReviews(productId);
        assertNotNull(reviewList);
        boolean foundInvalid = reviewList.stream()
                .anyMatch(r -> r.getRating() == 6);

        assertFalse(foundInvalid);
        assertEquals(size_before,reviewList.size());
    }

    @Test
    @Order(4)
    @DisplayName("Test to get reviews")
    void givenCorrectInput_ThenShouldReturnReviews()
    {
        List<Review> reviewList=reviewService.getReviews(4);
        assertNotNull(reviewList);
        for(Review review:reviewList)
        {
            assertTrue(review.getRating()>=1&&review.getRating()<=5);
            assertNotNull(review.getComment());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test to get average rating")
    void givenCorrectInput_ThenShouldReturnAverageRating()
    {
        int userid=4;
        int productId=4;
        reviewService.addReview(userid,productId,4,"Best");
        reviewService.addReview(userid,productId,2,"Worst");
        double avg_Rating=reviewService.getAverageRating(4);
        assertTrue(avg_Rating >=0 && avg_Rating<=5);
        assertEquals(3.0,avg_Rating);
    }
    @Test
    @Order(6)
    @DisplayName("Test if product id not present")
    void givenNoReviews_WhenGetAverageRating_ThenReturnZero()
    {
        double avg=reviewService.getAverageRating(100000);
        assertEquals(0,avg);
    }

    @Test
    @Order(7)
    @DisplayName("Test if product id not present")
    void givenMultipleReviews_WhenUserAddReview_ThenAllStored()
    {
        int productId=2;
        int userid=1;
        reviewService.addReview(userid,productId,4,"Best");
        reviewService.addReview(userid,productId,2,"Worst");

        List<Review> reviewList=reviewService.getReviews(2);
        assertTrue(reviewList.size()>=2);
    }
    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Review Service Test Suite ====");

    }

}
