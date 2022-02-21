package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_OrderRepository")
public class OrderRepository implements EntityRepository<OrderData, Order> {

    @Autowired
    DataManager dataManager;

    @Autowired
    OrderMapper mapper;

    @Override
    public Order save(OrderData orderData) {
        return dataManager.save(mapper.toEntity(orderData));
    }

}