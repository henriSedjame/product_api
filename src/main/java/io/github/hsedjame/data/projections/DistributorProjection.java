package io.github.hsedjame.data.projections;

import io.vertx.mutiny.sqlclient.Row;

public record DistributorProjection(String name, String cities) {

    public static DistributorProjection fromRow(Row row) {
        return new DistributorProjection(
                row.getString(Fields.NAME),
                row.getString(Fields.CITIES)
        );
    }
}
