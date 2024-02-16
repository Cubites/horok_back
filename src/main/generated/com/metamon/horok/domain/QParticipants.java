package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipants is a Querydsl query type for Participants
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipants extends EntityPathBase<Participants> {

    private static final long serialVersionUID = -1123104209L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipants participants = new QParticipants("participants");

    public final QFolders folder;

    public final BooleanPath folderFavor = createBoolean("folderFavor");

    public final NumberPath<Integer> folderParticipantsId = createNumber("folderParticipantsId", Integer.class);

    public final QUsers user;

    public QParticipants(String variable) {
        this(Participants.class, forVariable(variable), INITS);
    }

    public QParticipants(Path<? extends Participants> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipants(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipants(PathMetadata metadata, PathInits inits) {
        this(Participants.class, metadata, inits);
    }

    public QParticipants(Class<? extends Participants> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folder = inits.isInitialized("folder") ? new QFolders(forProperty("folder")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user"), inits.get("user")) : null;
    }

}

