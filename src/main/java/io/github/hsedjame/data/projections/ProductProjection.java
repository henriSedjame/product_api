package io.github.hsedjame.data.projections;


import java.util.Optional;

public record ProductProjection(String info) {

    public static Optional<ProductProjection> fromObject(Object o) {

        return Optional.of(new ProductProjection((String) o));

    }
}
