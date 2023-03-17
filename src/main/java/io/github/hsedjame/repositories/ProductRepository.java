package io.github.hsedjame.repositories;

import io.github.hsedjame.data.entities.Product;
import io.github.hsedjame.data.projections.CityProjection;
import io.github.hsedjame.data.projections.DistributorProjection;
import io.github.hsedjame.data.projections.ProductInfoProjection;
import io.github.hsedjame.data.projections.ProductProjection;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.github.hsedjame.repositories.Queries.*;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    @Inject
    PgPool client;

    /**
     * Find a product information by its name
     * @param name the product name
     * @return product's information
     */
    public Uni<ProductProjection> findByName(String name) {
        return getSession()
                .flatMap(session -> session
                        .createNativeQuery(FIND_BY_NAME)
                        .setParameter(NAME, name)
                        .getSingleResult()
                )
                .map(ProductProjection::fromObject)
                .map(Optional::orElseThrow);
    }

    /**
     * Find a product distributors
     * @param name the product name
     * @return stream of distributors
     */
    public Multi<DistributorProjection> findDistributors(String name) {
        return getSession()
                .flatMap(session -> session
                                    .createNativeQuery(FIND_DISTRIBUTORS)
                                    .setParameter(NAME, name)
                                    .getResultList()
                )
                .toMulti()
                .flatMap(l -> Multi.createFrom().iterable(l))
                .map(DistributorProjection::fromObject)
                .map(Optional::orElseThrow);
    }

    /**
     * Find list of cities where a product is distributed
     * @param name the product name
     * @return list of cities
     */
    public Multi<CityProjection> findDistributionCities(String name) {
        return getSession()
                .flatMap(session -> session
                        .createNativeQuery(FIND_DISTRIBUTION_CITIES)
                        .setParameter(NAME, name)
                        .getResultList()
                )
                .toMulti()
                .flatMap(l -> Multi.createFrom().iterable(l))
                .map(CityProjection::fromObject)
                .map(Optional::orElseThrow);
    }

    /**
     * Find all products distributed in a list of cities
     * @param cities list of cities
     * @return list of products
     */
    public Multi<ProductInfoProjection>  findDistributedProductsByCity(List<String> cities) {

        String sql = String.format(
                FIND_DISTRIBUTED_PRODUCTS_BY_CITY,
                String.join(",", cities.stream().map(s -> String.format("'%s'", s)).toList())
        );


        Future<List<ProductInfoProjection>> map = client.query(sql)
                .execute()
                .map(rows -> {
                    List<ProductInfoProjection> list = new ArrayList<>();

                    rows.forEach(row -> list.add(ProductInfoProjection.fromRow(row)));

                    return list;
                });

        return Uni.createFrom()
                .completionStage(map.toCompletionStage())
                .toMulti()
                .flatMap(l -> Multi.createFrom().iterable(l));

    }
}
