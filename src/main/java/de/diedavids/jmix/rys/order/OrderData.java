package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.customer.Customer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderData {
    LocalDate orderDate;
    Customer customer;
    LocalDateTime pickupDate;
    LocalDateTime returnDate;
    List<OrderLine> orderLines;
}
    