package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductRepository")
public class ProductRepository implements EntityRepository<ProductData, Product> {

    @Autowired
    ProductMapper mapper;

    @Autowired
    DataManager dataManager;

    public Product save(ProductData productData) {
        return dataManager.save(mapper.toEntity(productData));
    }
}