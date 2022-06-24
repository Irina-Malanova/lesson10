package ru.gb.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.market.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.price <= :maxPrice and p.price >= :minPrice")
    List<Product> findQ(int minPrice, int maxPrice);


}
