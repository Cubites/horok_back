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

    public static final QUsers users = new QUsers("users");

    public final BooleanPath agreement = createBoolean("agreement");

    public final ListPath<Cards, QCards> cardsList = this.<Cards, QCards>createList("cardsList", Cards.class, QCards.class, PathInits.DIRECT2);

    public final ListPath<Favors, QFavors> favorsList = this.<Favors, QFavors>createList("favorsList", Favors.class, QFavors.class, PathInits.DIRECT2);

    public final ListPath<Participants, QParticipants> participantsList = this.<Participants, QParticipants>createList("participantsList", Participants.class, QParticipants.class, PathInits.DIRECT2);

    public final StringPath personalCode = createString("personalCode");

    public final ListPath<Replies, QReplies> repliesList = this.<Replies, QReplies>createList("repliesList", Replies.class, QReplies.class, PathInits.DIRECT2);

    public final ListPath<Reviews, QReviews> reviewsList = this.<Reviews, QReviews>createList("reviewsList", Reviews.class, QReviews.class, PathInits.DIRECT2);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath userLoginType = createString("userLoginType");

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userProfile = createString("userProfile");

    public final DateTimePath<java.time.LocalDateTime> userRegdate = createDateTime("userRegdate", java.time.LocalDateTime.class);

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

