package io.github.hsedjame.data.entities;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.vertx.core.json.JsonObject;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;


@Entity(name = "products")
@Table(name = "products")
public class Product extends PanacheEntity {

    public String info;

    public Product() {}
    public Product(String info) {
        this.info = info;
    }


    public static Optional<Product> withInfos(ProductInfo info) {
        return info.toJson().map(Product::new);
    }
}
