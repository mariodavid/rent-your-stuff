package de.diedavids.jmix.rys.entity;

import de.diedavids.jmix.rys.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface MoneyMapper {

    Money toEntity(MoneyData moneyData);
}

