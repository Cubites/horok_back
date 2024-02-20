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

    public static final QFolderReviews folderReviews = new QFolderReviews("folderReviews");

    public final ListPath<Favors, QFavors> favorsList = this.<Favors, QFavors>createList("favorsList", Favors.class, QFavors.class, PathInits.DIRECT2);

    public final NumberPath<Integer> folderReviewId = createNumber("folderReviewId", Integer.class);

    public final ListPath<Replies, QReplies> repliesList = this.<Replies, QReplies>createList("repliesList", Replies.class, QReplies.class, PathInits.DIRECT2);

    public QFolderReviews(String variable) {
        super(FolderReviews.class, forVariable(variable));
    }

    public QFolderReviews(Path<? extends FolderReviews> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFolderReviews(PathMetadata metadata) {
        super(FolderReviews.class, metadata);
    }

}

