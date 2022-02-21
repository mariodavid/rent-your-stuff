package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface ProductMapper {

    Product toEntity(ProductData productData);
}
