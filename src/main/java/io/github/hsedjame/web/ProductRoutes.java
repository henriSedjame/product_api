package io.github.hsedjame.web;

import io.github.hsedjame.data.dtos.CityDTO;
import io.github.hsedjame.data.dtos.DistributorDTO;
import io.github.hsedjame.data.dtos.ProductDTO;
import io.github.hsedjame.data.dtos.ProductInfoDTO;
import io.github.hsedjame.repositories.ProductRepository;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
@RouteBase(path = "/api/v1/products")
public record ProductRoutes(@Inject ProductRepository repository) {

    @Route(path = ":name", methods = Route.HttpMethod.GET, order = 2)
    public Uni<ProductDTO> findByName(@Param String name) {
        return repository.findByName(name)
                .map(ProductDTO::fromProjection)
                .map(Optional::orElseThrow);
    }

    @Route(path = ":name/distributors", methods = Route.HttpMethod.GET)
    public Multi<DistributorDTO> findDistributors(@Param String name) {
        return repository.findDistributors(name)
                .map(DistributorDTO::fromProjection)
                .map(Optional::orElseThrow);
    }

    @Route(path = ":name/distributions", methods = Route.HttpMethod.GET)
    public Multi<CityDTO> findDistributionCities(@Param String name) {
        return repository.findDistributionCities(name)
                .map(CityDTO::fromProjection);
    }

    @Route(path = "distributed", methods = Route.HttpMethod.GET, order = 1)
    public Multi<ProductInfoDTO> findDistributedIn(RoutingExchange exchange) {
        String cities = exchange.getParam("cities").orElse("");
        return repository.findDistributedProductsByCity(cities)
                .map(ProductInfoDTO::fromProjection)
                .map(Optional::orElseThrow);
    }

}
