package com.metamon.horok.repository;

import com.metamon.horok.domain.Folders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folders,Integer> {
    Folders findByFolderName(String folderName);
}
