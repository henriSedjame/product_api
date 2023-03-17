package io.github.hsedjame.data.projections;

import java.util.Optional;

public record DistributorProjection(String name, String cities) {

    public static Optional<DistributorProjection> fromObject(Object o) {
        var array = (Object[]) o;
        if (array.length == 2) {
            return Optional.of(new DistributorProjection(
                            (String) array[0],
                            (String) array[1]
                    )
            );
        } else {
            return Optional.empty();
        }
    }
}
