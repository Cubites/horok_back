package com.metamon.horok.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class FolderDTO {

    //폴더 no
    private Integer folderId;
    private Integer folderParticipantsId;
    //폴더명
    private String folderName;
    //폴더색
    private String folderImg;
    //폴더 당 즐겨찾기 여부
    private  Boolean folderFavor;
    //폴더별 참여자 수
    private Integer folderParticipants;




}
