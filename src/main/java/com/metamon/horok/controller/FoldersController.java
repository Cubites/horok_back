package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.dto.FolderDTO;
import lombok.extern.java.Log;
import com.metamon.horok.service.FolderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log
public class FoldersController {

    private final FolderService folderService;
    //의존성 생성자 주입
    public FoldersController(FolderService folderService){
        this.folderService = folderService;
    }
    @GetMapping("/api/folders/user")
    public List<PartFolderDTO> folderList(@UserIdFromJwt Integer userId){
        return foldersService.getFolderListByUserId(userId);

    @GetMapping("/api/folders/{is_favor}")
    public List<FolderDTO> folderList(@PathVariable Boolean is_favor){
        int testId = 171;
        if(is_favor) {
            return folderService.getFolderListByUserId(is_favor, testId);
        }else {
            return folderService.getFolderListAllByUserId(testId);

        }
    }

    @PatchMapping("/api/folders/favor/edit")
    public String folderFavorEdit (@RequestBody Map<String, String> data){
        int testId = 171;
        return folderService.updateFolderFavor(data);
    }
}
