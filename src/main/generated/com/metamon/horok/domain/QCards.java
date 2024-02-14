package com.metamon.horok.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCards is a Querydsl query type for Cards
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCards extends EntityPathBase<Cards> {

    private static final long serialVersionUID = 1696942004L;

    public static final QCards cards = new QCards("cards");

    public final StringPath cardImg = createString("cardImg");

    public final StringPath cardName = createString("cardName");

    public final StringPath cardNumber = createString("cardNumber");

    public final ListPath<Pays, QPays> payList = this.<Pays, QPays>createList("payList", Pays.class, QPays.class, PathInits.DIRECT2);

    public QCards(String variable) {
        super(Cards.class, forVariable(variable));
    }

    public QCards(Path<? extends Cards> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCards(PathMetadata metadata) {
        super(Cards.class, metadata);
    }

}

