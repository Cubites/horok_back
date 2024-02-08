package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPays is a Querydsl query type for Pays
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPays extends EntityPathBase<Pays> {

    private static final long serialVersionUID = -2023082406L;

    public static final QPays pays = new QPays("pays");

    public final NumberPath<Integer> credit = createNumber("credit", Integer.class);

    public final BooleanPath expiration = createBoolean("expiration");

    public final BooleanPath isWritten = createBoolean("isWritten");

    public final StringPath payAddr = createString("payAddr");

    public final DateTimePath<java.time.LocalDateTime> payDate = createDateTime("payDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> payId = createNumber("payId", Integer.class);

    public final StringPath storeCategory = createString("storeCategory");

    public final StringPath storeName = createString("storeName");

    public QPays(String variable) {
        super(Pays.class, forVariable(variable));
    }

    public QPays(Path<? extends Pays> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPays(PathMetadata metadata) {
        super(Pays.class, metadata);
    }

}

