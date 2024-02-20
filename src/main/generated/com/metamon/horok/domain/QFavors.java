package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavors is a Querydsl query type for Favors
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavors extends EntityPathBase<Favors> {

    private static final long serialVersionUID = 1151611844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavors favors = new QFavors("favors");

    public final NumberPath<Integer> favorId = createNumber("favorId", Integer.class);

    public final QFolderReviews folderReview;

    public final QUsers user;

    public QFavors(String variable) {
        this(Favors.class, forVariable(variable), INITS);
    }

    public QFavors(Path<? extends Favors> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavors(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavors(PathMetadata metadata, PathInits inits) {
        this(Favors.class, metadata, inits);
    }

    public QFavors(Class<? extends Favors> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folderReview = inits.isInitialized("folderReview") ? new QFolderReviews(forProperty("folderReview")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user"), inits.get("user")) : null;
    }

}

