package ru.gb.market.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.DataValidationException;
import ru.gb.market.exceptions.MarketError;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.model.Category;
import ru.gb.market.model.Product;
import ru.gb.market.services.CategoryService;
import ru.gb.market.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public Page<ProductDto> findAll(@RequestParam(name = "p", defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return productService.findAll(pageIndex - 1, 10).map(ProductDto::new);
    }


//    @GetMapping("/products/{id}")
//    public ResponseEntity<?> findById(@PathVariable Long id) {
//        Optional<Product> product = productService.findById(id);
//        if (product.isEmpty()) {
//            return new ResponseEntity<>(new MarketError("Product id = "+ id +" not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(new ProductDto(product.get()), HttpStatus.OK);
//    }

    @GetMapping("/products/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return new ProductDto(productService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + " not found")));
    }


    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult
            .getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList()));
        }
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto
                .getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException("Category title = " + productDto.getCategoryTitle() + " not found"));
        product.setCategory(category);
        productService.save(product);
        return new ProductDto(product);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProductFromDto(productDto);
    }
}
