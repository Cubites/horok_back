package com.metamon.horok.service;

import com.metamon.horok.dto.PartFolderDTO;

import java.util.List;

public interface FoldersService {
    List<PartFolderDTO> getFolderListByUserId(Integer userId);
}
