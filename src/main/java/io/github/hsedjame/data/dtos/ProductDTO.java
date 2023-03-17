package io.github.hsedjame.data.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hsedjame.data.projections.ProductProjection;

import java.util.Optional;

public record ProductDTO(ProductInfoDTO infos) {

    public static Optional<ProductDTO> fromProjection(ProductProjection projection) {
        try {
            ProductInfoDTO info = new ObjectMapper().readValue(projection.info(), ProductInfoDTO.class);
            return Optional.of(new ProductDTO(info));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }
}
