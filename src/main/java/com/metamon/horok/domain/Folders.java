package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@Table(name="folders")
@EqualsAndHashCode(of="folderId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer folderId;
    private String folderName;
    private String folderImg;

    @OneToMany
    @JoinColumn(name = "folder_id")
    private List<FolderReviews> folderList;

    // floder가 생성될 때 participants도 같이 생성될 수 있음으로, cascade옵션 추가
    @OneToMany(mappedBy = "folder",cascade = CascadeType.ALL)
    private List<Participants> participantsList;

}
