package io.github.hsedjame.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record ProductInfo(String name,
                          BigDecimal price,
                          List<Distributor> distributors)
        implements Serializable {}
