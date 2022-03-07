package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.entity.*;
import de.diedavids.jmix.rys.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("rys_ProductPrices")
public class ProductPrices
        implements TestDataProvisioning<ProductPriceData, ProductPriceData.ProductPriceDataBuilder, ProductPrice> {

    @Autowired
    ProductPriceRepository repository;
    @Autowired
    MoneyMapper moneyMapper;

    public static final PriceUnit DEFAULT_PRICE_UNIT = PriceUnit.DAY;;
    public static final Currency DEFAULT_CURRENCY = Currency.EUR;
    public static final BigDecimal DEFAULT_AMOUNT = BigDecimal.TEN;

    @Override
    public ProductPriceData.ProductPriceDataBuilder defaultData() {
        return ProductPriceData.builder()
                .unit(DEFAULT_PRICE_UNIT)
                .price(defaultPrice())
                .product(null);
    }

    public Money defaultPrice() {
        return money(DEFAULT_AMOUNT, DEFAULT_CURRENCY);
    }

    @Override
    public ProductPrice save(ProductPriceData productData)  {
        return repository.save(productData);
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