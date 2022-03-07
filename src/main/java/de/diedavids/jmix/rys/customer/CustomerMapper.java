package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.entity.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface CustomerMapper {

    Customer toEntity(CustomerData customerData);
}

