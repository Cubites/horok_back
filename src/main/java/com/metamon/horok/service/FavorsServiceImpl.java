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
    public Map<Integer,FavorDTO> getFavorsInfo(Integer userId, Integer folderId) {
        List<Object[]> cntList = favorsRepo.countFavorsByFolderIdGroupByFolderReviewId(folderId);
        List<Object[]> checkList = favorsRepo.checkIfUserLikedReview(userId, folderId);

//        System.out.println(cntList.size());
//        for(Object[] a : cntList){
//            for(Object o : a){
//                System.out.print(o);
//            }
//        }
//        System.out.println(checkList.size());
//        for(Object[] a : checkList){
//            for(Object o : a){
//                System.out.print(o);
//            }
//        }
        Map<Integer,FavorDTO> favorsMap = new HashMap<>();
        for(int i=0; i<checkList.size(); i++){
            FavorDTO dto = FavorDTO.builder()
                    .folderReviewId(Integer.parseInt(String.valueOf(cntList.get(i)[0])))
                    .favorCnt(Integer.parseInt(String.valueOf(cntList.get(i)[1])))
                    .isFavor(Boolean.parseBoolean(String.valueOf(checkList.get(i)[1])))
                    .build();

           favorsMap.put(Integer.parseInt(String.valueOf(cntList.get(i)[0])), dto);
        }


        return favorsMap;
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

    @Override
    public void deleteFavorByReviewId(Integer reviewId) {
        favorsRepo.deleteFavorByReviewId(reviewId);
    }


}
