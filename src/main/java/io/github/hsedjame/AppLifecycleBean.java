package io.github.hsedjame;

import io.github.hsedjame.data.entities.Distributor;
import io.github.hsedjame.data.entities.Product;
import io.github.hsedjame.data.entities.ProductInfo;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@ApplicationScoped
public class AppLifecycleBean {

    void onStart(/*@Observes StartupEvent ev*/) {

        Distributor fnac = new Distributor("FNAC", List.of("Paris", "Poitiers", "Bordeaux"));
        Distributor darty = new Distributor("DARTY", List.of("Dijon", "Paris"));
        Distributor amazon = new Distributor("AMAZON", Collections.singletonList("Web"));
        Distributor micromania = new Distributor("MICROMANIA", List.of("Paris", "Marseille", "Bordeaux"));

        ProductInfo xbox = new ProductInfo("XBOX", BigDecimal.valueOf(499), List.of(fnac, darty, amazon));
        ProductInfo ps = new ProductInfo("PLAYSTATION", BigDecimal.valueOf(655), List.of(micromania, amazon));
        ProductInfo raspberrypi = new ProductInfo("RASPBERRY PI", BigDecimal.valueOf(150), List.of(amazon));
        ProductInfo mac = new ProductInfo("MAC BOOK", BigDecimal.valueOf(1700), List.of(fnac, amazon));
        ProductInfo iphone = new ProductInfo("IPHONE", BigDecimal.valueOf(299), List.of(fnac, amazon));

        Product.deleteAll()
                .subscribe()
                .with(
                        x -> Stream.of(xbox, ps, raspberrypi, mac, iphone)
                                .map(Product::withInfos)
                                .map(Optional::get)
                                .forEach(p ->
                                    p.persistAndFlush()
                                            .subscribe()
                                            .with(y -> System.out.println("Product persisted: " + y))
                                )
                );

    }

}
