package com.metamon.horok.service;

import com.metamon.horok.dto.FavorDTO;

import java.util.List;
import java.util.Map;

public interface FavorsService {
    public Map<String, Object> getFavorInfo(Integer userId, Integer folderReviewId);

    public List<FavorDTO> getFavorsInfo(Integer userId, Integer folderId);

    void addFavor(Integer userId, Integer folderReviewId);

    void removeFavor(Integer userId, Integer folderReviewId);

}
