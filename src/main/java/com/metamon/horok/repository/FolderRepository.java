package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folders,Integer> {
    Folders findByFolderName(String folderName);

    //즐겨찾기 folder list
    @Query("select f.folderId,  f.folderName, f.folderImg, p.folderFavor, (SELECT COUNT(p2.folderParticipantsId)  From Participants p2 WHERE p2.folder.folderId = p.folder.folderId ) AS parti, p.folderParticipantsId from Folders f join FETCH Participants p on f.folderId = p.folder.folderId where p.user.userId = :userId AND p.folderFavor = :isFavor")
    List<Object[]> findFolderListByUserIdAndFavor(@Param("userId") Integer userId, @Param("isFavor") Boolean isFavor);
    //Object[] 담아야 하는 이유는 return 타입이 튜플이라 Dto에 담을 수 없음

    //all folder list
    @Query("select f.folderId,  f.folderName, f.folderImg, p.folderFavor, (SELECT COUNT(p2.folderParticipantsId) From Participants p2 WHERE p2.folder.folderId = p.folder.folderId ) AS parti, p.folderParticipantsId from Folders f join FETCH Participants p on f.folderId = p.folder.folderId where p.user.userId = :userId")
    List<Object[]> findFolderListByUserId(@Param("userId") Integer userId);

}
