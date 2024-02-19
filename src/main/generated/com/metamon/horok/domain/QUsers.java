package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = 1714089561L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsers users = new QUsers("users");

    public final BooleanPath agreement = createBoolean("agreement");

    public final ListPath<Cards, QCards> cardsList = this.<Cards, QCards>createList("cardsList", Cards.class, QCards.class, PathInits.DIRECT2);

    public final ListPath<Favors, QFavors> favorsList = this.<Favors, QFavors>createList("favorsList", Favors.class, QFavors.class, PathInits.DIRECT2);

    public final ListPath<Participants, QParticipants> participantsList = this.<Participants, QParticipants>createList("participantsList", Participants.class, QParticipants.class, PathInits.DIRECT2);

    public final StringPath personalCode = createString("personalCode");

    public final ListPath<Replies, QReplies> repliesList = this.<Replies, QReplies>createList("repliesList", Replies.class, QReplies.class, PathInits.DIRECT2);

    public final ListPath<Reviews, QReviews> reviewsList = this.<Reviews, QReviews>createList("reviewsList", Reviews.class, QReviews.class, PathInits.DIRECT2);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final QUserLoginInfo userLoginInfo;

    public final StringPath userLoginType = createString("userLoginType");

    public final StringPath userNickname = createString("userNickname");

    public final ArrayPath<byte[], Byte> userProfile = createArray("userProfile", byte[].class);

    public final DateTimePath<java.time.LocalDateTime> userRegdate = createDateTime("userRegdate", java.time.LocalDateTime.class);

    public QUsers(String variable) {
        this(Users.class, forVariable(variable), INITS);
    }

    public QUsers(Path<? extends Users> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsers(PathMetadata metadata, PathInits inits) {
        this(Users.class, metadata, inits);
    }

    public QUsers(Class<? extends Users> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userLoginInfo = inits.isInitialized("userLoginInfo") ? new QUserLoginInfo(forProperty("userLoginInfo"), inits.get("userLoginInfo")) : null;
    }

}

