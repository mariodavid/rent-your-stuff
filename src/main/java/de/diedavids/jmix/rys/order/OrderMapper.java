package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderMapper {

    Order toEntity(OrderData orderData);
}

