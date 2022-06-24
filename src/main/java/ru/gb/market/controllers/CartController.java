package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.DataValidationException;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.model.Category;
import ru.gb.market.model.Product;
import ru.gb.market.services.CartService;
import ru.gb.market.services.CategoryService;
import ru.gb.market.services.ProductService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping("/cart")
    public Page<ProductDto> findAll(@RequestParam(name = "p", defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        int size= cartService.getProductList().size();

        List<ProductDto> productDtos = cartService.getProductListByPage(pageIndex - 1, 10).stream().map(ProductDto::new).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);

        return new PageImpl<>(productDtos, pageable, size);
    }

    @DeleteMapping("/cart/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.findById(id);
            cartService.delete(product.get());
        } catch(Exception e){
            throw new ResourceNotFoundException("Product id = "+ id +" not found");
        }
    }

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public int save(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult) {
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
       cartService.addProduct(product);
       List<Product> productList= cartService.getProductList();
       return  cartService.getProductList().size();
    }
}
