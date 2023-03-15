package io.github.hsedjame.services;

import io.github.hsedjame.data.entities.Product;
import io.github.hsedjame.data.projections.CityProjection;
import io.github.hsedjame.data.projections.DistributorProjection;
import io.github.hsedjame.data.projections.ProductInfoProjection;
import io.github.hsedjame.data.projections.ProductProjection;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

import java.util.List;

import static io.github.hsedjame.services.Queries.*;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Override
    public Uni<ProductProjection> findByName(String productName) {
        return Product
                .find(FIND_BY_NAME)
                .project(ProductProjection.class)
                .firstResult();
    }

    @Override
    public Multi<DistributorProjection> findDistributors(String productName) {
        return Product
                .find(FIND_DISTRIBUTORS, Parameters.with(NAME, productName))
                .project(DistributorProjection.class)
                .stream();
    }

    @Override
    public Multi<CityProjection> findDistributionCities(String productName) {
        return Product
                .find(FIND_DISTRIBUTION_CITIES,  Parameters.with(NAME, productName))
                .project(CityProjection.class)
                .stream();
    }

    @Override
    public Multi<ProductInfoProjection>  findDistributedProductsByCity(List<String> cities) {

        String sql = String.format(
                FIND_DISTRIBUTED_PRODUCTS_BY_CITY,
                String.join(",", cities.stream().map(s -> String.format("'%s'", s)).toList())
        );

        return Product
                .find(sql)
                .project(ProductInfoProjection.class)
                .stream();
    }
}
