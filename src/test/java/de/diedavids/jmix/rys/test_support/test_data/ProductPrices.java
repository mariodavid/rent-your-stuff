package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.entity.*;
import de.diedavids.jmix.rys.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductPrices
        implements TestDataProvisioning<ProductPriceData, ProductPriceData.ProductPriceDataBuilder, ProductPrice> {

    @Autowired
    private ProductPriceRepository repository;
    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    private ProductPriceMapper productPriceMapper;
    @Autowired
    private Products products;

    public static final PriceUnit DEFAULT_PRICE_UNIT = PriceUnit.DAY;;
    public static final Currency DEFAULT_CURRENCY = Currency.EUR;
    public static final BigDecimal DEFAULT_AMOUNT = BigDecimal.TEN;


    @Override
    public ProductPriceData.ProductPriceDataBuilder defaultData() {
        return ProductPriceData.builder()
                .product(products.createDefault())
                .unit(DEFAULT_PRICE_UNIT)
                .price(defaultPrice());
    }

    public Money defaultPrice() {
        return money(DEFAULT_AMOUNT, DEFAULT_CURRENCY);
    }

    @Override
    public ProductPrice save(ProductPriceData productData)  {
        return repository.save(productData);
    }

    @Override
    public ProductPrice create(ProductPriceData productPriceData) {
        return productPriceMapper.toEntity(productPriceData);
    }

    @Override
    public ProductPrice createDefault() {
        return create(defaultData().build());
    }

    @Override
    public ProductPrice saveDefault() {
        return save(defaultData().build());
    }

    public Money money(BigDecimal amount, Currency currency) {
        return moneyMapper.toEntity(MoneyData.builder()
                .amount(amount)
                .currency(currency)
                .build());
    }
}