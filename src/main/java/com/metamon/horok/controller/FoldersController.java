package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.service.FoldersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoldersController {

    private final FoldersService foldersService;

    public FoldersController(FoldersService foldersService){
        this.foldersService = foldersService;
    }
    @GetMapping("/api/folders/user")
    public List<PartFolderDTO> folderList(@UserIdFromJwt Integer userId){
        return foldersService.getFolderListByUserId(userId);
    }
}
