package io.github.hsedjame.repositories;

public class Queries {

    public static final String FIND_BY_NAME = """
            SELECT  (info #>> '{}') as info FROM products WHERE info ->> 'name' = $1
            """;

    public static final String FIND_DISTRIBUTORS = """
            SELECT
                distribs.value ->> 'name' as name,
                distribs.value ->> 'cities' as cities
            FROM
            (
                SELECT
                    json_array_elements(info -> 'distributors') as value
                FROM products
                WHERE info ->> 'name' = $1
            ) distribs
            """;

    public static final String FIND_DISTRIBUTION_CITIES = """
            SELECT distinct
             json_array_elements_text(
                 json_array_elements(info -> 'distributors') -> 'cities'
             ) as name
            FROM products
            WHERE info ->> 'name' = $1
            """;

    public static final String FIND_DISTRIBUTED_PRODUCTS_BY_CITY = """
            SELECT q.product ->> 'name' as name,
                   q.product ->> 'distributors' as distributors,
                   cast(q.product ->> 'price' as DECIMAL) as price
            FROM
                (
                    SELECT
                        info as product,
                        ((json_array_elements(info -> 'distributors') -> 'cities')::jsonb ?| array[%s]) as result
                    FROM products
                ) q
            WHERE q.result = true
            """;
}
