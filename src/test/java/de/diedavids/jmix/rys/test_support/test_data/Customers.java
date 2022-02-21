package de.diedavids.jmix.rys.test_support.test_data;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.customer.CustomerData;
import de.diedavids.jmix.rys.customer.CustomerRepository;
import de.diedavids.jmix.rys.entity.Address;
import de.diedavids.jmix.rys.entity.AddressData;
import de.diedavids.jmix.rys.entity.AddressMapper;
import de.diedavids.jmix.rys.order.Order;
import de.diedavids.jmix.rys.order.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("rys_Customers")
public class Customers
        implements TestDataProvisioning<CustomerData, CustomerData.CustomerDataBuilder, Customer> {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressMapper addressMapper;

    public static final String DEFAULT_ORDER_DATE = "first_name";
    public static final String DEFAULT_LAST_NAME = "last_name";
    public static final String DEFAULT_EMAIL = "first_name@last_name.com";
    public static final String DEFAULT_STREET = "street";
    public static final String DEFAULT_POST_CODE = "postcode";
    public static final String DEFAULT_CITY = "city";

    @Override
    public CustomerData.CustomerDataBuilder defaultData() {
        return CustomerData.builder()
                .firstName(DEFAULT_ORDER_DATE)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .address(defaultAddress());
    }

    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .build());
    }

    @Override
    public Customer save(CustomerData customerData)  {
        return customerRepository.save(customerData);
    }

    @Override
    public Customer saveDefault() {
        return save(defaultData().build());
    }

}