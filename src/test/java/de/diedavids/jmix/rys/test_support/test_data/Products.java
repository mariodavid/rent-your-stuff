package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.product.Product;
import de.diedavids.jmix.rys.product.ProductInfo;
import de.diedavids.jmix.rys.product.ProductMapper;
import de.diedavids.jmix.rys.product.ProductRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_Products")
public class Products
        implements TestDataProvisioning<ProductInfo, ProductInfo.ProductInfoBuilder, Product> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ProductMapper mapper;

    @Autowired
    ProductRepository productRepository;

    public static String DEFAULT_NAME = "product_name";

    @Override
    public ProductInfo.ProductInfoBuilder defaultData() {
        return ProductInfo.builder()
                .name(DEFAULT_NAME)
                .category(null);
    }

    @Override
    public Product save(ProductInfo productInfo)  {
        return productRepository.save(productInfo);
    }

    @Override
    public Product saveDefault() {
        return save(defaultData().build());
    }

}