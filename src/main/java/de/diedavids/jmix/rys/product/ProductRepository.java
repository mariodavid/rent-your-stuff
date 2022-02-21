package de.diedavids.jmix.rys.product;

import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductRepository")
public class ProductRepository {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    DataManager dataManager;

    public Product save(ProductInfo productInfo) {
        return dataManager.save(productMapper.toEntity(productInfo));
    }
}