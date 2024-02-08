package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.domain.Participants;
import com.metamon.horok.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants,Integer> {
    Participants findByFolder(Folders folderName);


}
