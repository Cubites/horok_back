package com.metamon.horok.service;

import com.metamon.horok.domain.Folders;
import com.metamon.horok.dto.FolderDTO;
import com.metamon.horok.dto.PartFolderDTO;

import java.util.List;
import java.util.Map;

public interface FolderService {
    public List<FolderDTO> getFolderListByUserId (Boolean isFavor , Integer userId);
    public List<FolderDTO> getFolderListAllByUserId( Integer userId);
    public String updateFolderFavor(Map map);
    public Integer createFolder(Map<String, String> map, Integer userId);
    public List<PartFolderDTO> getFolderListByUserId(Integer userId);
    public Boolean folderParticipation(Integer userId, Integer folderId);
    public Boolean alreadyParticipated(Integer userId, Integer folderId);
    public List<FolderDTO> findFolderIdsByUserIdAndReviewIdNotShared(Integer userId, Integer reviewId);

    public String deleteFolder(Integer userId, Integer folderId);
    public Integer updateFolder(Map<String, String> map);
    Folders getFolderInfo(Integer folderId);
}
