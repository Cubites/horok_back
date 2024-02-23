package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReplies is a Querydsl query type for Replies
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReplies extends EntityPathBase<Replies> {

    private static final long serialVersionUID = -785751783L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReplies replies = new QReplies("replies");

    public final QFolderReviews folderReview;

    public final StringPath replyContent = createString("replyContent");

    public final DateTimePath<java.time.LocalDateTime> replyDate = createDateTime("replyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> replyId = createNumber("replyId", Integer.class);

    public final QUsers user;

    public QReplies(String variable) {
        this(Replies.class, forVariable(variable), INITS);
    }

    public QReplies(Path<? extends Replies> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReplies(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReplies(PathMetadata metadata, PathInits inits) {
        this(Replies.class, metadata, inits);
    }

    public QReplies(Class<? extends Replies> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folderReview = inits.isInitialized("folderReview") ? new QFolderReviews(forProperty("folderReview")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user"), inits.get("user")) : null;
    }

}
