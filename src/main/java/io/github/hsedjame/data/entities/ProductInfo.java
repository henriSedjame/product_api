package io.github.hsedjame.data.entities;

import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public record ProductInfo(String name,
                          BigDecimal price,
                          List<Distributor> distributors)
        implements Serializable {

    /**
     * Permet de transformer une information produit en objet de type Json
     * @return un optional de json
     */
    public Optional<JsonObject> toJson()  {

        try {
            return Optional.of(JsonObject.mapFrom(this));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
