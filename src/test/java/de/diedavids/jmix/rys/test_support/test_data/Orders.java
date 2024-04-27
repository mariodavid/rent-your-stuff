package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.order.Order;
import de.diedavids.jmix.rys.order.OrderData;
import de.diedavids.jmix.rys.order.OrderMapper;
import de.diedavids.jmix.rys.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class Orders
        implements TestDataProvisioning<OrderData, OrderData.OrderDataBuilder, Order> {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Customers customers;
    @Autowired
    private OrderMapper orderMapper;

    public static final LocalDate DEFAULT_ORDER_DATE = LocalDate.now().plusDays(1);

    public static final LocalDateTime DEFAULT_PICKUP_DATE = LocalDateTime.of(DEFAULT_ORDER_DATE, LocalTime.of(8, 0));
    public static final LocalDateTime DEFAULT_RETURN_DATE = LocalDateTime.of(DEFAULT_ORDER_DATE, LocalTime.of(16, 0));


    @Override
    public OrderData.OrderDataBuilder defaultData() {
        return OrderData.builder()
                .pickupDate(DEFAULT_PICKUP_DATE)
                .returnDate(DEFAULT_RETURN_DATE)
                .orderDate(DEFAULT_ORDER_DATE)
                .customer(customers.createDefault())
                .orderLines(List.of());
    }

    @Override
    public Order save(OrderData orderData)  {
        return orderRepository.save(orderData);
    }

    @Override
    public Order create(OrderData orderData) {
        return orderMapper.toEntity(orderData);
    }

    @Override
    public Order createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Order saveDefault() {
        return save(defaultData().build());
    }

}
