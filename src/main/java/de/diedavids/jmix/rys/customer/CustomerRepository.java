package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.entity.EntityRepository;
import de.diedavids.jmix.rys.order.OrderLine;
import de.diedavids.jmix.rys.order.OrderLineData;
import de.diedavids.jmix.rys.order.OrderLineMapper;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_CustomerRepository")
public class CustomerRepository implements EntityRepository<CustomerData, Customer> {

    @Autowired
    DataManager dataManager;

    @Autowired
    CustomerMapper mapper;

    @Override
    public Customer save(CustomerData customerData) {
        return dataManager.save(mapper.toEntity(customerData));
    }

}