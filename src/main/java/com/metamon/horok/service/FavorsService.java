package com.metamon.horok.service;

import com.metamon.horok.dto.FavorDTO;

import java.util.Map;

public interface FavorsService {
    public Map<String, Object> getFavorInfo(Integer userId, Integer folderReviewId);

    //folderReviewId, count(Favors)
//    public List<Object[]> testFavor(Integer folderId);

    //folderReviewId, isFavor
//    public List<Object[]> testFavor2(Integer userId, Integer folderId);

    public Map<Integer,FavorDTO> getFavorsInfo(Integer userId, Integer folderId);

    void addFavor(Integer userId, Integer folderReviewId);

    void removeFavor(Integer userId, Integer folderReviewId);

    void deleteFavorByReviewId(Integer reviewId);

}
