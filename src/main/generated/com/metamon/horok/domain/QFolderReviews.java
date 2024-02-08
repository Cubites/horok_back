package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFolderReviews is a Querydsl query type for FolderReviews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFolderReviews extends EntityPathBase<FolderReviews> {

    private static final long serialVersionUID = 809547550L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFolderReviews folderReviews = new QFolderReviews("folderReviews");

    public final ListPath<Favors, QFavors> favorsList = this.<Favors, QFavors>createList("favorsList", Favors.class, QFavors.class, PathInits.DIRECT2);

    public final QFolders folder;

    public final NumberPath<Integer> folderReviewId = createNumber("folderReviewId", Integer.class);

    public final ListPath<Replies, QReplies> repliesList = this.<Replies, QReplies>createList("repliesList", Replies.class, QReplies.class, PathInits.DIRECT2);

    public final QReviews review;

    public QFolderReviews(String variable) {
        this(FolderReviews.class, forVariable(variable), INITS);
    }

    public QFolderReviews(Path<? extends FolderReviews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFolderReviews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFolderReviews(PathMetadata metadata, PathInits inits) {
        this(FolderReviews.class, metadata, inits);
    }

    public QFolderReviews(Class<? extends FolderReviews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folder = inits.isInitialized("folder") ? new QFolders(forProperty("folder")) : null;
        this.review = inits.isInitialized("review") ? new QReviews(forProperty("review"), inits.get("review")) : null;
    }

}

