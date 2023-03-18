package io.github.hsedjame.data.projections;

import io.vertx.mutiny.sqlclient.Row;

import java.math.BigDecimal;

public record ProductInfoProjection(String name, BigDecimal price, String distributors) {

    public static ProductInfoProjection fromRow(Row row) {
        return new ProductInfoProjection(
                row.getString(Fields.NAME),
                row.getBigDecimal(Fields.PRICE),
                row.getString(Fields.DISTRIBUTORS)
        );
    }
}
