package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.entity.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderLineMapper {

    OrderLine toEntity(OrderLineData orderLineData);
}

