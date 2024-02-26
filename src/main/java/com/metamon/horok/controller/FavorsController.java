package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.dto.FavorDTO;
import com.metamon.horok.service.FavorsService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log
public class FavorsController {

    private final FavorsService favorsService;

    public FavorsController(FavorsService favorsService){
        this.favorsService = favorsService;
    }

//    @GetMapping("/api/favors/test/{folderId}")
//    public List<Object[]> testFavors(@PathVariable("folderId") Integer folderId){
//        return favorsService.testFavor(folderId);
//    }

    @GetMapping("/api/favors/{folderId}")
    public Map<Integer, FavorDTO> getFavorsInfo(@PathVariable("folderId") Integer folderId, @UserIdFromJwt Integer userId){
        return favorsService.getFavorsInfo(userId, folderId);
    }
}
