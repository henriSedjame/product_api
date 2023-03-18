package io.github.hsedjame.data.projections;


import io.vertx.mutiny.sqlclient.Row;

public record ProductProjection(String info) {

    public static ProductProjection fromRow(Row row) {
        return new ProductProjection(row.getString(Fields.INFO));
    }
}
