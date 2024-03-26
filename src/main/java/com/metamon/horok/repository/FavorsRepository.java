package com.metamon.horok.repository;

import com.metamon.horok.domain.Favors;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavorsRepository extends JpaRepository<Favors,Integer> {

    @Query("select count(f) from Favors f where f.folderReview.folderReviewId = :folderReviewId")
    int countFavorsByFolderReviewId(@Param("folderReviewId") Integer folderReviewId);

    //@Query("SELECT f.folderReview.folderReviewId, COUNT(DISTINCT f.user.userId) FROM Favors f WHERE f.folderReview.folderId = :folderId GROUP BY f.folderReview.folderReviewId ORDER BY f.folderReview.folderReviewId")
    //@Query("SELECT fr.folderReviewId, COUNT(DISTINCT f.user.userId) FROM FolderReview fr LEFT JOIN fr.favors f WHERE fr.folderId = :folderId GROUP BY fr.folderReviewId ORDER BY fr.folderReviewId")
    @Query("SELECT fr.folderReviewId, COUNT(DISTINCT f.user.userId) FROM FolderReviews fr LEFT JOIN fr.favorsList f WHERE fr.folderId = :folderId GROUP BY fr.folderReviewId ORDER BY fr.folderReviewId")
    List<Object[]> countFavorsByFolderIdGroupByFolderReviewId(@Param("folderId") Integer folderId);

//    @Query("SELECT f.folderReview.folderReviewId, COUNT(f) > 0 FROM Favors f WHERE f.user.userId = :userId AND f.folderReview.folderId = :folderId GROUP BY f.folderReview.folderReviewId ORDER BY f.folderReview.folderReviewId")
//@Query("SELECT fr.folderReviewId, COUNT(f) > 0 FROM FolderReviews fr LEFT JOIN Favors f ON fr.folderReviewId = f.folderReview.folderReviewId AND f.user.userId = :userId AND f.folderReview.folderId = :folderId WHERE f.folderReview.folderId = :folderId GROUP BY fr.folderReviewId ORDER BY fr.folderReviewId")
    @Query("SELECT fr.folderReviewId, COUNT(f.user.userId) > 0 FROM FolderReviews fr LEFT JOIN Favors f ON fr.folderReviewId = f.folderReview.folderReviewId AND f.user.userId = :userId AND fr.folderId = :folderId WHERE fr.folderId = :folderId GROUP BY fr.folderReviewId ORDER BY fr.folderReviewId")
    List<Object[]> checkIfUserLikedReview(@Param("userId") Integer userId, @Param("folderId") Integer folderId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO favors (user_id, folder_review_id) VALUES (:userId, :folderReviewId)", nativeQuery = true)
    void addFavor(@Param("userId") Integer userId,@Param("folderReviewId") Integer folderReviewId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favors WHERE user_id = :userId AND folder_review_id = :folderReviewId", nativeQuery = true)
    void removeFavor(@Param("userId") Integer userId, @Param("folderReviewId") Integer folderReviewId);

    @Modifying
    @Query(value = "DELETE FROM Favors f WHERE f.folderReview.reviewId = :reviewId")
    void deleteFavorByReviewId(@Param("reviewId") Integer reviewId);
}
