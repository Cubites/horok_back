package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.dto.FolderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folders,Integer> {
    Folders findByFolderName(String folderName);

    //즐겨찾기 folder list
    @Query("select f.folderId,  f.folderName, f.folderImg, p.folderFavor, (SELECT COUNT(p2.folderParticipantsId)  From Participants p2 WHERE p2.folder.folderId = p.folder.folderId ) AS parti, p.folderParticipantsId from Folders f join FETCH Participants p on f.folderId = p.folder.folderId where p.user.userId = :userId AND p.folderFavor = :isFavor order by f.folderName")
    List<Object[]> findFolderListByUserIdAndFavor(@Param("userId") Integer userId, @Param("isFavor") Boolean isFavor);
    //Object[] 담아야 하는 이유는 return 타입이 튜플이라 Dto에 담을 수 없음

    //all folder list
    @Query("select f.folderId,  f.folderName, f.folderImg, p.folderFavor, (SELECT COUNT(p2.folderParticipantsId) From Participants p2 WHERE p2.folder.folderId = p.folder.folderId ) AS parti, p.folderParticipantsId from Folders f join FETCH Participants p on f.folderId = p.folder.folderId where p.user.userId = :userId order by f.folderName")
    List<Object[]> findFolderListByUserId(@Param("userId") Integer userId);

    //유저가 참가하고 있는 폴더이면서 해당 리뷰가 공유되지 않은 폴더 목록
    @Query("SELECT new com.metamon.horok.dto.FolderDTO(p.folder.folderId as folderId, p.folder.folderName as folderName) " +
            "FROM Participants p " +
            "WHERE p.user.userId = :userId " +
            "AND p.folder.folderId NOT IN " +
            "(SELECT fr.folderId FROM FolderReviews fr WHERE fr.reviewId = :reviewId)")
    List<FolderDTO> findFolderIdsByUserIdAndReviewIdNotShared(
            @Param("userId") Integer userId,
            @Param("reviewId") Integer reviewId
    );

}
