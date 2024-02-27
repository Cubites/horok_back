package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtUtil;
import com.metamon.horok.dto.FolderDTO;
import com.metamon.horok.dto.PartFolderDTO;
import com.metamon.horok.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FoldersController {
    private final JwtUtil jwtUtil;
    private final FolderService folderService;

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
    public Integer folderMake(@UserIdFromJwt Integer userId, @RequestBody Map<String, String> map){
        return folderService.createFolder(map, userId);
    }


    @GetMapping("/api/folders/invite/{folderId}")
    public String folderInvite(@PathVariable("folderId") Integer folderId){
        //토큰 발급
        String token = jwtUtil.generateInviteToken(folderId);
       return token;
    }

    @PostMapping("/api/folders/invite")
      public String folderInvite(@UserIdFromJwt Integer userId, @RequestBody Map<String,String> inviteToken){
        String msg = "expiration";
        //토큰에서 폴더 아이디 뽑기
        Integer folderId = jwtUtil.getFolderId(inviteToken.get("inviteToken"));

        if(folderId > 0){
            msg = "notExpiration"; //폴더 있음
            if(folderService.alreadyParticipated(userId, folderId)){
                msg = "invited"; //초대 가능
                if(folderService.folderParticipation(userId, folderId)){
                    msg = "invite success"; // 초대 성공
                } //else 초대 실패
            } //else 이미 참가한 아이디
        }//else 폴더 없음
        return msg;
    }

    @GetMapping("/api/folders/notshared/{reviewId}")
    public List<FolderDTO> getNotSharedFolderList(@UserIdFromJwt Integer userId, @PathVariable("reviewId") Integer reviewId){
        System.out.println(userId);
        return folderService.findFolderIdsByUserIdAndReviewIdNotShared(userId, reviewId);
    }

}
