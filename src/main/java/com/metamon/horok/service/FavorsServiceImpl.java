package com.metamon.horok.service;

import com.metamon.horok.dto.FavorDTO;
import com.metamon.horok.repository.FavorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FavorsServiceImpl implements FavorsService{

    private final FavorsRepository favorsRepo;

    @Override
    public Map<String, Object> getFavorInfo(Integer userId, Integer folderReviewId) {
        Map<String, Object> map = new HashMap<>();

        map.put("favorCnt",favorsRepo.countFavorsByFolderReviewId(folderReviewId));
        //map.put("isFavor", favorsRepo.checkIfUserLikedReview(userId, folderReviewId));

        return map;
    }

    @Override
    public List<FavorDTO> getFavorsInfo(Integer userId, Integer folderId) {
        List<Object[]> cntList = favorsRepo.countFavorsByFolderIdGroupByFolderReviewId(folderId);
        List<Object[]> checkList = favorsRepo.checkIfUserLikedReview(userId, folderId);

        List<FavorDTO> favorsList = new ArrayList<>();

        for(int i=0; i<cntList.size(); i++){
            FavorDTO dto = FavorDTO.builder()
                    .folderReviewId(Integer.parseInt(String.valueOf(cntList.get(i)[0])))
                    .favorCnt(Integer.parseInt(String.valueOf(cntList.get(i)[1])))
                    .isFavor(Boolean.parseBoolean(String.valueOf(checkList.get(i)[1])))
                    .build();
           favorsList.add(dto);
        }


        return favorsList;
    }

//    @Override
//    public List<Object[]> testFavor(Integer folderId) {
//        return favorsRepo.countFavorsByFolderIdGroupByFolderReviewId(folderId);
//    }

//    @Override
//    public List<Object[]> testFavor2(Integer userId, Integer folderId) {
//        return favorsRepo.checkIfUserLikedReview(userId, folderId);
//    }

    @Override
    public void addFavor(Integer userId, Integer folderReviewId) {
        favorsRepo.addFavor(userId,folderReviewId);
    }

    @Override
    public void removeFavor(Integer userId, Integer folderReviewId) {
        favorsRepo.removeFavor(userId, folderReviewId);
    }


}
