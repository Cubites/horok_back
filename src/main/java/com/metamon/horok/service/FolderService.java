package com.metamon.horok.service;


import com.metamon.horok.dto.FolderDTO;

import java.util.List;
import java.util.Map;

public interface FolderService {
    public List<FolderDTO> getFolderListByUserId (Boolean isFavor , Integer userId);
    public List<FolderDTO> getFolderListAllByUserId( Integer userId);
    public String updateFolderFavor(Map map);
}
