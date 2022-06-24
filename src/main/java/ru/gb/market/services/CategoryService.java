package ru.gb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.model.Category;
import ru.gb.market.model.Product;
import ru.gb.market.repositories.CategoryRepository;
import ru.gb.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
       return categoryRepository.findByTitle(title);
    }
}
