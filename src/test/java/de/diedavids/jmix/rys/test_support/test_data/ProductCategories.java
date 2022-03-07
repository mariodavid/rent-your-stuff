package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCategories
        implements TestDataProvisioning<ProductCategoryData, ProductCategoryData.ProductCategoryDataBuilder, ProductCategory> {

    @Autowired
    ProductCategoryRepository repository;

    public static String DEFAULT_NAME = "product_category_name";
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategoryData.ProductCategoryDataBuilder defaultData() {
        return ProductCategoryData.builder()
                .name(DEFAULT_NAME);
    }

    @Override
    public ProductCategory save(ProductCategoryData productCategoryData) {
        return repository.save(productCategoryData);
    }

    @Override
    public ProductCategory create(ProductCategoryData productCategoryData) {
        return productCategoryMapper.toEntity(productCategoryData);
    }

    @Override
    public ProductCategory createDefault() {
        return create(defaultData().build());
    }

    @Override
    public ProductCategory saveDefault() {
        return save(defaultData().build());
    }
}