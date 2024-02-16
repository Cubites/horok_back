package com.metamon.horok.service;

import com.metamon.horok.domain.Participants;
import com.metamon.horok.dto.FolderDTO;
import com.metamon.horok.repository.FolderRepository;
import com.metamon.horok.repository.ParticipantsRepository;
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
public class FolderServiceImple implements FolderService {

    private final FolderRepository folderRepo;
    private final ParticipantsRepository partiRepo;

    @Override
    public List<FolderDTO> getFolderListByUserId(Boolean isFavor, Integer userId) {
        List<Object[]> list = folderRepo.findFolderListByUserIdAndFavor(userId, isFavor);
        List<FolderDTO> listDto = new ArrayList<>();

        list.stream().forEach(o->{
//            System.out.println(o[0]+","+o[1]+","+o[2]+","+o[3]+","+o[4]);
            FolderDTO dto = new FolderDTO();
            dto.setFolderId(((Number) o[0]).intValue()); //dto.setFolderId(((BigInteger)o[0]).intValue());
            dto.setFolderName((String)o[1]);
            dto.setFolderImg((String)o[2]);
            dto.setFolderFavor((Boolean)o[3]);
            dto.setFolderParticipants(((Number) o[4]).intValue());//dto.setFolderParticipants(((BigInteger)o[4]).intValue());
            dto.setFolderParticipantsId(((Number)o[5]).intValue());
            listDto.add(dto);
        });

        //listDto.sort(Comparator.comparing(FolderDTO::getFolderName));
        return listDto;

    }

    @Override
    public List<FolderDTO> getFolderListAllByUserId( Integer userId) {
        List<Object[]> list = folderRepo.findFolderListByUserId(userId);
        List<FolderDTO> listDto = new ArrayList<>();

        list.stream().forEach(o->{
//            System.out.println(o[0]+","+o[1]+","+o[2]+","+o[3]+","+o[4]);
            FolderDTO dto = new FolderDTO();
            dto.setFolderId(((Number) o[0]).intValue()); //dto.setFolderId(((BigInteger)o[0]).intValue());
            dto.setFolderName((String)o[1]);
            dto.setFolderImg((String)o[2]);
            dto.setFolderFavor((Boolean)o[3]);
            dto.setFolderParticipants(((Number) o[4]).intValue());//dto.setFolderParticipants(((BigInteger)o[4]).intValue());
            dto.setFolderParticipantsId(((Number)o[5]).intValue());
            listDto.add(dto);
        });

        //listDto.sort(Comparator.comparing(FolderDTO::getFolderName));
        return listDto;

    }

    @Override
    @Transactional
    public String updateFolderFavor(Map map) {
        map.forEach((key, value) -> {
            Optional<Participants> participantsOptional = partiRepo.findById(Integer.parseInt(String.valueOf(key)));

            if(participantsOptional.isPresent()) {
                Participants parti = participantsOptional.get();
                parti.setFolderFavor(Boolean.parseBoolean((String)value));
            }
        });
        return "true";
    }
}

