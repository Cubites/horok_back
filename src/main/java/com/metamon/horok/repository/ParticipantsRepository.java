package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.domain.Participants;
import com.metamon.horok.dto.PartFolderDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants,Integer> {
    List<Participants> findByFolder(Folders folderName);
    Integer countByUser_UserId(Integer userId);

    @Query("select new com.metamon.horok.dto.PartFolderDTO(p.folderParticipantsId as folderParticipantsId, u.userId as userId, f.folderId as folderId, f.folderName as folderName) from Participants p JOIN p.folder f JOIN p.user u WHERE u.userId = :userId order by p.folderFavor desc")
    List<PartFolderDTO> findByUserIdWithFolderName(@Param("userId") Integer userId);

    Participants findByFolder_FolderIdAndUser_UserId(Integer folderId, Integer userId);

    void deleteByUser_UserIdAndFolder_FolderId(Integer userId, Integer folderId);

}
