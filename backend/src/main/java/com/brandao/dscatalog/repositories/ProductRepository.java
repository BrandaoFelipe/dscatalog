package com.brandao.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandao.dscatalog.entities.Product;
import com.brandao.dscatalog.projections.ProductProjection;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories") // n
    // + 1 consultas

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
            SELECT DISTINCT tb_product.id, tb_product.name
                    FROM tb_product
                    INNER JOIN product_category ON tb_product.id = product_category.product_id
                    WHERE (:categoryIds IS NULL OR product_category.category_id IN :categoryIds)
                    AND LOWER (tb_product.name) LIKE LOWER (CONCAT('%',:name,'%'))
                    ) AS tb_result
                                """, countQuery = """
                                    SELECT COUNT(*) FROM (
                                    SELECT DISTINCT tb_product.id, tb_product.name
                        FROM tb_product
                        INNER JOIN product_category ON tb_product.id = product_category.product_id
                        WHERE (:categoryIds IS NULL OR product_category.category_id IN :categoryIds)
                        AND LOWER (tb_product.name) LIKE LOWER (CONCAT('%',:name,'%'))
            ) AS tb_result
                                             """)
    Page<ProductProjection> findAllWithCategories(Pageable pageable, @Param("name") String name,
            @Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.id IN :productIds")
    List<Product> searchProductWithCategories(@Param("productIds") List<Long> productIds);
}
