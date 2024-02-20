package com.metamon.horok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartFolderDTO {
    private Integer folderParticipantsId;
    private Integer userId;
    private Integer folderId;
    private String folderName;

}
