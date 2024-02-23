package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtUtil;
import com.metamon.horok.domain.Participants;
import com.metamon.horok.dto.FolderDTO;
import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log
@RequiredArgsConstructor
public class FoldersController {
    private final JwtUtil jwtUtil;
    private final FolderService folderService;
    //의존성 생성자 주입
//    public FoldersController(FolderService folderService){
//        this.folderService = folderService;
//    }
    @GetMapping("/api/folders/user")
    public List<PartFolderDTO> folderList(@UserIdFromJwt Integer userId) {
        return folderService.getFolderListByUserId(userId);
    }

    @GetMapping("/api/folders/{is_favor}")
    public List<FolderDTO> folderList(@PathVariable("is_favor") Boolean is_favor, @UserIdFromJwt Integer userId){

        if(is_favor) {
            return folderService.getFolderListByUserId(is_favor, userId);
        }else {
            return folderService.getFolderListAllByUserId(userId);
        }
    }

    @PatchMapping("/api/folders/favor/edit")
    public String folderFavorEdit (@RequestBody Map<String, String> data){
        return folderService.updateFolderFavor(data);
    }

    @PostMapping("/api/folders/make")
    public Participants folderMake(@UserIdFromJwt Integer userId, @RequestBody Map<String, String> map){
        System.out.println(map);
        return folderService.createFolder(map, userId);
    }


    @GetMapping("/api/folders/invite/{folderId}")
    public String folderInvite(@PathVariable Integer folderId){
        //토큰 발급
        String token = jwtUtil.generateInviteToken(folderId);
        //토큰에서 폴더 아이디 뽑기
        //Integer folderId2 = jwtUtil.getFolderId(token);
        // /login?redirect_uri=""
       return token;
    }

    @PostMapping("/api/folders/invite")
      public String folderInvite(@UserIdFromJwt Integer userId, @RequestBody String inviteToken){
        String msg = null;
        //토큰에서 폴더 아이디 뽑기
        Integer folderId = jwtUtil.getFolderId(inviteToken);

        if(folderId > 0){
            msg = "expiration";
            if(folderService.alreadyParticipated(userId, folderId)){ //초대 가능
                msg = "invited";
                if(folderService.folderParticipation(userId, folderId)){ // 초대 성공
                    msg = "invite success";
                } //else 초대 실패
            } //else 이미 참가한 아이디
        }//else 폴더 없음
        return msg;
    }



}
