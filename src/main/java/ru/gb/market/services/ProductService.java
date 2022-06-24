package ru.gb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.model.Category;
import ru.gb.market.model.Product;
import ru.gb.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> findAll(int pageIndex, int pageSize){
        return productRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public Product save(Product product){

        return productRepository.save(product);
    }

    public List<Product> findByPriceBetween(int minPrice, int maxPrice){
        return productRepository.findQ(minPrice, maxPrice);
    }

    @Transactional
    public void updateProductFromDto(ProductDto productDto){
        Product product = findById(productDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product id = " + productDto.getId() + " not found"));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto
                .getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException("Category title = " + productDto.getCategoryTitle() + " not found"));
        product.setCategory(category);
    }
}
