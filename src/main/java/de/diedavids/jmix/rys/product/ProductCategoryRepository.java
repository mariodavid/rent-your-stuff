package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductCategoryRepository")
public class ProductCategoryRepository implements EntityRepository<ProductCategoryData, ProductCategory> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ProductCategoryMapper mapper;

    @Override
    public ProductCategory save(ProductCategoryData productCategoryData) {
        return dataManager.save(mapper.toEntity(productCategoryData));
    }

}