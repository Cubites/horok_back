package com.metamon.horok.service;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.domain.Participants;
import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.FolderDTO;
import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.repository.FolderRepository;
import com.metamon.horok.repository.ParticipantsRepository;
import com.metamon.horok.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepo;
    private final ParticipantsRepository partRepo;
    private final UsersRepository userRepo;

    @Override
    public List<PartFolderDTO> getFolderListByUserId(Integer userId){
        return partRepo.findByUserIdWithFolderName(userId);
    }

    @Override
    public Boolean folderParticipation(Integer userId, Integer folderId) {
        Users u = userRepo.findById(userId).orElse(null);
        Folders f = folderRepo.findById(folderId).orElse(null);

        Participants p = Participants.builder().folder(f).user(u).folderFavor(false).build();
        Participants savedParticipant = partRepo.save(p);
        if ( savedParticipant != null) {
            return true;
        } else {
            return false;  // 저장 실패
        }
    }

    @Override
    public Boolean alreadyParticipated(Integer userId, Integer folderId) {
        Participants p = partRepo.findByFolder_FolderIdAndUser_UserId(folderId,userId);
        if(p != null){
            return false;
        }else {
            return true;
        }

    }

    @Override
    public List<FolderDTO> getFolderListByUserId(Boolean isFavor, Integer userId) {
        List<Object[]> list = folderRepo.findFolderListByUserIdAndFavor(userId, isFavor);
        List<FolderDTO> listDto = new ArrayList<>();

        list.stream().forEach(o->{
            FolderDTO dto = new FolderDTO();
            dto.setFolderId(((Number) o[0]).intValue());
            dto.setFolderName((String)o[1]);
            dto.setFolderImg((String)o[2]);
            dto.setFolderFavor((Boolean)o[3]);
            dto.setFolderParticipants(((Number) o[4]).intValue());
            dto.setFolderParticipantsId(((Number)o[5]).intValue());
            listDto.add(dto);
        });
        return listDto;
    }

    @Override
    public List<FolderDTO> getFolderListAllByUserId( Integer userId) {
        List<Object[]> list = folderRepo.findFolderListByUserId(userId);
        List<FolderDTO> listDto = new ArrayList<>();

        list.stream().forEach(o->{
            FolderDTO dto = new FolderDTO();
            dto.setFolderId(((Number) o[0]).intValue());
            dto.setFolderName((String)o[1]);
            dto.setFolderImg((String)o[2]);
            dto.setFolderFavor((Boolean)o[3]);
            dto.setFolderParticipants(((Number) o[4]).intValue());
            dto.setFolderParticipantsId(((Number)o[5]).intValue());
            listDto.add(dto);
        });
        return listDto;
    }

    @Override
    @Transactional
    public String updateFolderFavor(Map map) {
        map.forEach((key, value) -> {
            Optional<Participants> participantsOptional = partRepo.findById(Integer.parseInt(String.valueOf(key)));

            if(participantsOptional.isPresent()) {
                Participants parti = participantsOptional.get();
                parti.setFolderFavor(Boolean.parseBoolean((String)value));
            }
        });
        return "true";
    }

    @Override
    @Transactional
    public Integer createFolder(Map<String, String> map, Integer userId) {
        Users u = userRepo.findById(userId).orElse(null);
        Folders f = Folders.builder().folderName(map.get("folderName")).folderImg(map.get("folderImg")).build();
        Participants p = Participants.builder().folder(f).user(u).folderFavor(false).build();
        Folders savedFolder = folderRepo.save(f);
        Participants savedParticipant = partRepo.save(p);

        return savedParticipant.getFolder().getFolderId();
    }

    @Override
    public List<FolderDTO> findFolderIdsByUserIdAndReviewIdNotShared(Integer userId, Integer reviewId){
        return folderRepo.findFolderIdsByUserIdAndReviewIdNotShared(userId, reviewId);
    }
}

