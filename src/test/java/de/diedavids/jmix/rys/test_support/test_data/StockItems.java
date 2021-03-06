package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.product.*;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockItems
        implements TestDataProvisioning<StockItemData, StockItemData.StockItemDataBuilder, StockItem> {

    @Autowired
    DataManager dataManager;

    @Autowired
    StockItemRepository repository;

    public static String DEFAULT_IDENTIFIER = "stock_item_identifier";
    @Autowired
    private StockItemMapper stockItemMapper;

    @Override
    public StockItemData.StockItemDataBuilder defaultData() {
        return StockItemData.builder()
                .identifier(DEFAULT_IDENTIFIER)
                .product(null);
    }

    @Override
    public StockItem save(StockItemData stockItemData) {
        return repository.save(stockItemData);
    }

    @Override
    public StockItem create(StockItemData stockItemData) {
        return stockItemMapper.toEntity(stockItemData);
    }

    @Override
    public StockItem createDefault() {
        return create(defaultData().build());
    }

    @Override
    public StockItem saveDefault() {
        return save(defaultData().build());
    }

}