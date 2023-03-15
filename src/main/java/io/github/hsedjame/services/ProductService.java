package io.github.hsedjame.services;

import io.github.hsedjame.data.projections.CityProjection;
import io.github.hsedjame.data.projections.DistributorProjection;
import io.github.hsedjame.data.projections.ProductInfoProjection;
import io.github.hsedjame.data.projections.ProductProjection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProductService {
    Uni<ProductProjection> findByName(String productName);

    Multi<DistributorProjection> findDistributors(String productName);

    Multi<CityProjection> findDistributionCities(String productName);

    Multi<ProductInfoProjection>  findDistributedProductsByCity(List<String> cities);

}
