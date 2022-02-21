package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_StockItemRepository")
public class StockItemRepository implements EntityRepository<StockItemData, StockItem> {

    @Autowired
    StockItemMapper mapper;

    @Autowired
    DataManager dataManager;

    @Override
    public StockItem save(StockItemData stockItemData) {
        return dataManager.save(mapper.toEntity(stockItemData));
    }
}