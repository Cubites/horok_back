package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLoginInfo is a Querydsl query type for UserLoginInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserLoginInfo extends EntityPathBase<UserLoginInfo> {

    private static final long serialVersionUID = 1029485437L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLoginInfo userLoginInfo = new QUserLoginInfo("userLoginInfo");

    public final QUsers user;

    public final StringPath userLoginEmail = createString("userLoginEmail");

    public final NumberPath<Integer> userLoginInfoId = createNumber("userLoginInfoId", Integer.class);

    public final StringPath userLoginRole = createString("userLoginRole");

    public QUserLoginInfo(String variable) {
        this(UserLoginInfo.class, forVariable(variable), INITS);
    }

    public QUserLoginInfo(Path<? extends UserLoginInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLoginInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLoginInfo(PathMetadata metadata, PathInits inits) {
        this(UserLoginInfo.class, metadata, inits);
    }

    public QUserLoginInfo(Class<? extends UserLoginInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user"), inits.get("user")) : null;
    }

}

