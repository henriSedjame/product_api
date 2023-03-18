package io.github.hsedjame.repositories;

import io.github.hsedjame.data.projections.CityProjection;
import io.github.hsedjame.data.projections.DistributorProjection;
import io.github.hsedjame.data.projections.ProductInfoProjection;
import io.github.hsedjame.data.projections.ProductProjection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowIterator;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static io.github.hsedjame.repositories.Queries.*;

@ApplicationScoped
public record ProductRepository(@Inject PgPool client) {

    /**
     * Find a product information by its name
     * @param name the product name
     * @return product's information
     */
    public Uni<ProductProjection> findByName(String name) {
        return client.preparedQuery(FIND_BY_NAME)
                .execute(Tuple.of(name))
                .onItem().transform(rows -> {
                    RowIterator<Row> iterator = rows.iterator();
                    return iterator.hasNext() ? ProductProjection.fromRow(iterator.next()) : null;
                });


    }

    /**
     * Find a product distributors
     * @param name the product name
     * @return stream of distributors
     */
    public Multi<DistributorProjection> findDistributors(String name) {
       return client.preparedQuery(FIND_DISTRIBUTORS)
                .execute(Tuple.of(name))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .map(DistributorProjection::fromRow);
    }

    /**
     * Find list of cities where a product is distributed
     * @param name the product name
     * @return list of cities
     */
    public Multi<CityProjection> findDistributionCities(String name) {
        return client.preparedQuery(FIND_DISTRIBUTION_CITIES)
                .execute(Tuple.of(name))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .map(CityProjection::fromRow);
    }

    /**
     * Find all products distributed in a list of cities
     * @param cities list of cities
     * @return list of products
     */
    public Multi<ProductInfoProjection>  findDistributedProductsByCity(String cities) {

        String sql = String.format(
                FIND_DISTRIBUTED_PRODUCTS_BY_CITY,
                String.format( "'%s'", cities.replace(",", "','"))
        );

        return client.query(sql)
                .execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .map(ProductInfoProjection::fromRow);
    }
}
