package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.domain.Participants;
import com.metamon.horok.dto.PartFolderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants,Integer> {
    Participants findByFolder(Folders folderName);


    //Object[] findByUserId(Integer userId);
    /*@Query("select new com.metamon.horok.dto.SimplePayDTO(p.payId as payId, p.credit as credit, p.payDate as payDate, p.storeName as storeName, p.storeCategory as storeCategory, p.payAddr as payAddr) from Pays p where payId = :payId")
    SimplePayDTO findByPayId(@Param("payId") Integer payId);
    @Query(value = "SELECT * FROM pays p JOIN cards c ON p.card_number = c.card_number WHERE c.user_id = :userId AND p.expiration = false", nativeQuery = true)
    List<Object[]> findPaysByUserId(@Param("userId") Integer userId);*/

    /*@Query(value = "SELECT new com.metamon.horok.dto.PartFolderDTO(p.userId as userId, f.folderId as folderId, f.folderName as folderName) FROM Participants p JOIN FETCH p.folder f ON p.folderId = f.folderId WHERE p.userId = :userId")
    List<PartFolderDTO> findParticipantsByUserIdWithFolder(@Param("userId") Integer userId);*/

    @Query("select new com.metamon.horok.dto.PartFolderDTO(p.folderParticipantsId as folderParticipantsId, u.userId as userId, f.folderId as folderId, f.folderName as folderName) from Participants p JOIN p.folder f JOIN p.user u WHERE u.userId = :userId order by p.folderFavor desc")
    List<PartFolderDTO> findByUserIdWithFolderName(@Param("userId") Integer userId);
    /*@Query("select distinct(u) from Users u join fetch u.cardsList where u.userId = :userId")
    Users findByUserIdWithCards(@Param("userId") Integer userId);*/

}
