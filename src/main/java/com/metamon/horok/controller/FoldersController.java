package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.service.FolderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoldersController {

    private final FolderService foldersService;

    public FoldersController(FolderService foldersService){
        this.foldersService = foldersService;
    }
    @GetMapping("/api/folders/user")
    public List<PartFolderDTO> folderList(@UserIdFromJwt Integer userId){
        return foldersService.getFolderListByUserId(userId);
    }
}
