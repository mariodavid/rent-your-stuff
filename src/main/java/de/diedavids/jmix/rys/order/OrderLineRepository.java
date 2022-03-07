package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_OrderLineRepository")
public class OrderLineRepository implements EntityRepository<OrderLineData, OrderLine> {

    @Autowired
    DataManager dataManager;

    @Autowired
    OrderLineMapper mapper;

    @Override
    public OrderLine save(OrderLineData orderLineData) {
        return dataManager.save(mapper.toEntity(orderLineData));
    }

}