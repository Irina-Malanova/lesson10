package ru.gb.market.services;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component

public class CartService {

    private List<Product> productList = new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public List<Product> getProductListByPage(int pageNum, int pageSize) {
        int first= pageNum*pageSize;
        try {
            return  productList.subList(first, first + pageSize);
        } catch (IndexOutOfBoundsException e) {
            if (productList.size() < first || productList.size() == 0)
                return Collections.emptyList();
            if (productList.size() < first + pageSize) {
                return productList.subList(first, productList.size());
            }
        }
        return Collections.emptyList();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void delete(Product product){
        for(Product item: productList){
            if(item.getId().equals(product.getId()) &&
                    item.getTitle().equals(product.getTitle()) &&
                    item.getPrice()==product.getPrice()){
                productList.remove(item);
                return;
            }
        }
        throw new ResourceNotFoundException("Product id = " + product.getId() + " not found");
    }
}
