package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStores is a Querydsl query type for Stores
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStores extends EntityPathBase<Stores> {

    private static final long serialVersionUID = 1541131649L;

    public static final QStores stores = new QStores("stores");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath storeAddr = createString("storeAddr");

    public final StringPath storeCategory = createString("storeCategory");

    public final NumberPath<Integer> storeId = createNumber("storeId", Integer.class);

    public final StringPath storeName = createString("storeName");

    public QStores(String variable) {
        super(Stores.class, forVariable(variable));
    }

    public QStores(Path<? extends Stores> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStores(PathMetadata metadata) {
        super(Stores.class, metadata);
    }

}

