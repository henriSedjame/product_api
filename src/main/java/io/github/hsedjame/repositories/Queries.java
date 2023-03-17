package io.github.hsedjame.repositories;

public class Queries {

    public static final String NAME = "name";

    public static final String FIND_BY_NAME = """
            SELECT info FROM products WHERE cast(info AS json) ->> 'name' = :name
            """;

    public static final String FIND_DISTRIBUTORS = """
            SELECT
                cast (distribs.value AS json) ->> 'name' as name,
                cast(distribs.value AS json) ->> 'cities' as cities
            FROM
            (
                SELECT
                    json_array_elements_text(cast(info AS json) -> 'distributors') as value
                FROM products
                WHERE cast(info AS json) ->> 'name' = :name
            ) distribs
            """;

    public static final String FIND_DISTRIBUTION_CITIES = """
            SELECT distinct
             json_array_elements_text(
                 json_array_elements(cast(info AS json) -> 'distributors') -> 'cities'
             ) as name
            FROM products
            WHERE cast(info AS json) ->> 'name' = :name
            """;

    public static final String FIND_DISTRIBUTED_PRODUCTS_BY_CITY = """
            SELECT cast(q.product AS json) ->> 'name' as name,
                   cast(q.product AS json) ->> 'distributors' as distributors,
                   cast(cast(q.product AS json) ->> 'price' as DECIMAL) as price
            FROM
                (
                    SELECT
                        info as product,
                        ((json_array_elements(cast(info AS json) -> 'distributors') -> 'cities')::jsonb ?| array[%s]) as result
                    FROM products
                ) q
            WHERE q.result = true
            """;
}
