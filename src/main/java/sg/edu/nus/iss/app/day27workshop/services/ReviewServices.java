package sg.edu.nus.iss.app.day27workshop.services;


import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.day27workshop.models.EditedReview;
import sg.edu.nus.iss.app.day27workshop.models.Review;
import sg.edu.nus.iss.app.day27workshop.repository.ReviewRepository;


@Service
public class ReviewServices {
    
    @Autowired
    private ReviewRepository reviewRepo;

    // Inserts a review into the database
    public Review insertReview(Review review) {
        return reviewRepo.insertReview(review);
    }

    // Updates the edited review and saves it to the database
    public Review updateEdits(ObjectId id, EditedReview comment) {
        return reviewRepo.updateEdits(id, comment);
    }

    // Gets a review by its ID
    public Review getReview(ObjectId id) {
        
        Review review = reviewRepo.getReviewById(id);
        if (review.getEdited() != null) {
            // If there are edited reviews for this review, set the isEdited flag to true
            List<EditedReview> list = (List<EditedReview>) review.getEdited();
            System.out.println(list.size());
            if (list.size() > 0)
                review.setIsEdited(Boolean.valueOf(true));
            else
                review.setIsEdited(Boolean.valueOf(false));
        }
        // Set the timestamp to the current time and return the review
        review.setTimestamp(LocalDateTime.now());
        return review;
    }
}
