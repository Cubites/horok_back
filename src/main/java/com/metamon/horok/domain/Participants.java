package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name="participants")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer folderParticipantsId;
    @Column(columnDefinition = "TINYINT(4)" )
    private Boolean folderFavor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folders folder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;


    /*
     * 연관관계 편의 메서드
     * */

    public void setFoldersAndUsers(Folders folder, Users user){
        this.folder = folder;
        this.user = user;
        folder.getParticipantsList().add(this);
        user.getParticipantsList().add(this);
    }

    public void setFolderFavor(Boolean folderFavor) {
        this.folderFavor = folderFavor;
    }
}
