package io.github.hsedjame.data.dtos;

import io.github.hsedjame.data.projections.CityProjection;


public record CityDTO(String name) {
    public static CityDTO fromProjection(CityProjection projection) {
        return new CityDTO(projection.name());
    }
}
