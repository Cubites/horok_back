package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPays is a Querydsl query type for Pays
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPays extends EntityPathBase<Pays> {

    private static final long serialVersionUID = -2023082406L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPays pays = new QPays("pays");

    public final QCards card;

    public final NumberPath<Integer> credit = createNumber("credit", Integer.class);

    public final BooleanPath expiration = createBoolean("expiration");

    public final BooleanPath isWritten = createBoolean("isWritten");

    public final StringPath payAddr = createString("payAddr");

    public final DateTimePath<java.time.LocalDateTime> payDate = createDateTime("payDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> payId = createNumber("payId", Integer.class);

    public final QStores store;

    public final StringPath storeCategory = createString("storeCategory");

    public final StringPath storeName = createString("storeName");

    public QPays(String variable) {
        this(Pays.class, forVariable(variable), INITS);
    }

    public QPays(Path<? extends Pays> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPays(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPays(PathMetadata metadata, PathInits inits) {
        this(Pays.class, metadata, inits);
    }

    public QPays(Class<? extends Pays> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCards(forProperty("card")) : null;
        this.store = inits.isInitialized("store") ? new QStores(forProperty("store")) : null;
    }

}

