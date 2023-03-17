package io.github.hsedjame.data.projections;

import java.util.Optional;

public record CityProjection(String name) {
    public static Optional<CityProjection> fromObject(Object o) {
        return Optional.of(new CityProjection((String) o));
    }
}
