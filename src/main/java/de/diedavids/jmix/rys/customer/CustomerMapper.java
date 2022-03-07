package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.JmixEntityFactory;
import de.diedavids.jmix.rys.order.OrderLine;
import de.diedavids.jmix.rys.order.OrderLineData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface CustomerMapper {

    Customer toEntity(CustomerData customerData);
}

