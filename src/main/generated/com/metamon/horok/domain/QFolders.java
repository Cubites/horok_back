package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFolders is a Querydsl query type for Folders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFolders extends EntityPathBase<Folders> {

    private static final long serialVersionUID = 1731461590L;

    public static final QFolders folders = new QFolders("folders");

    public final NumberPath<Integer> folderId = createNumber("folderId", Integer.class);

    public final StringPath folderImg = createString("folderImg");

    public final ListPath<FolderReviews, QFolderReviews> folderList = this.<FolderReviews, QFolderReviews>createList("folderList", FolderReviews.class, QFolderReviews.class, PathInits.DIRECT2);

    public final StringPath folderName = createString("folderName");

    public final ListPath<Participants, QParticipants> participantsList = this.<Participants, QParticipants>createList("participantsList", Participants.class, QParticipants.class, PathInits.DIRECT2);

    public QFolders(String variable) {
        super(Folders.class, forVariable(variable));
    }

    public QFolders(Path<? extends Folders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFolders(PathMetadata metadata) {
        super(Folders.class, metadata);
    }

}

