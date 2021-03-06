package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.order.*;
import de.diedavids.jmix.rys.order.OrderLineData;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static de.diedavids.jmix.rys.test_support.test_data.Orders.*;

@Component
public class OrderLines
        implements TestDataProvisioning<OrderLineData, OrderLineData.OrderLineDataBuilder, OrderLine> {

    @Autowired
    DataManager dataManager;
    @Autowired
    private OrderLineMapper orderLineMapper;
    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    StockItems stockItems;
    @Autowired
    Orders orders;

    public static final LocalDateTime DEFAULT_STARTS_AT = LocalDateTime.of(DEFAULT_ORDER_DATE, LocalTime.of(8, 0));
    public static final LocalDateTime DEFAULT_ENDS_AT = LocalDateTime.of(DEFAULT_ORDER_DATE, LocalTime.of(16, 0));



    @Override
    public OrderLineData.OrderLineDataBuilder defaultData() {
        return OrderLineData.builder()
                .stockItem(stockItems.createDefault())
                .order(orders.createDefault())
                .startsAt(DEFAULT_STARTS_AT)
                .endsAt(DEFAULT_ENDS_AT);
    }

    @Override
    public OrderLine save(OrderLineData orderLineData) {
        return orderLineRepository.save(orderLineData);
    }

    @Override
    public OrderLine create(OrderLineData orderLineData) {
        return orderLineMapper.toEntity(orderLineData);
    }

    @Override
    public OrderLine createDefault() {
        return create(defaultData().build());
    }

    @Override
    public OrderLine saveDefault() {
        return save(defaultData().build());
    }

}