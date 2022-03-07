package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductPriceRepository")
public class ProductPriceRepository implements EntityRepository<ProductPriceData, ProductPrice> {

    @Autowired
    ProductPriceMapper mapper;

    @Autowired
    DataManager dataManager;

    public ProductPrice save(ProductPriceData productData) {
        return dataManager.save(mapper.toEntity(productData));
    }
}