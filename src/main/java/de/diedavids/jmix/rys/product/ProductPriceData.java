package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.Money;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductPriceData {
    Product product;
    Money price;
    PriceUnit unit;
}
