package io.github.hsedjame.data.entities;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.vertx.core.json.JsonObject;

import javax.persistence.Entity;
import java.util.Optional;


@Entity(name = "products")
public class Product extends PanacheEntity {

    public JsonObject info;

    public Product() {}

    public Product(JsonObject info) {
        this.info = info;
    }

    public static Optional<Product> withInfos(ProductInfo info) {
        return info.toJson().map(Product::new);
    }
}
