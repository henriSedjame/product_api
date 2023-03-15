package io.github.hsedjame.data.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Optional<String> toJson() {
        try {
            return Optional.of(new ObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
