package io.github.hsedjame.data.projections;

import io.vertx.mutiny.sqlclient.Row;

public record CityProjection(String name) {

    public static CityProjection fromRow(Row row) {
        return new CityProjection(row.getString(Fields.NAME));
    }
}
