package de.diedavids.jmix.rys.entity;

import de.diedavids.jmix.rys.JmixEntityFactory;
import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.customer.CustomerData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface AddressMapper {

    Address toEntity(AddressData addressData);
}

