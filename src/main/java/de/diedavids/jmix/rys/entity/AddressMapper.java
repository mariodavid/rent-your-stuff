package de.diedavids.jmix.rys.entity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface AddressMapper {

    Address toEntity(AddressData addressData);
}

