package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.domain.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants,Integer> {
    Participants findByFolder(Folders folderName);
    Integer countByUser_UserId(Integer userId);

}
