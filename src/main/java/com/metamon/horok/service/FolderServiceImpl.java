package com.metamon.horok.service;

import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.repository.ParticipantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final ParticipantsRepository partRepo;
    @Override
    public List<PartFolderDTO> getFolderListByUserId(Integer userId){
        return partRepo.findByUserIdWithFolderName(userId);
    }
}
