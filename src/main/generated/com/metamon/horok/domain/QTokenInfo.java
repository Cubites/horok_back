package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTokenInfo is a Querydsl query type for TokenInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTokenInfo extends EntityPathBase<TokenInfo> {

    private static final long serialVersionUID = 1395899928L;

    public static final QTokenInfo tokenInfo = new QTokenInfo("tokenInfo");

    public final StringPath accessToken = createString("accessToken");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath refreshToken = createString("refreshToken");

    public QTokenInfo(String variable) {
        super(TokenInfo.class, forVariable(variable));
    }

    public QTokenInfo(Path<? extends TokenInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTokenInfo(PathMetadata metadata) {
        super(TokenInfo.class, metadata);
    }

}

