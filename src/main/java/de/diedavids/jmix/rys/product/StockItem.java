package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "RYS_STOCK_ITEM")
@Entity(name = "rys_StockItem")
public class StockItem extends StandardEntity {
    @InstanceName
    @NotBlank
    @Column(name = "IDENTIFIER", nullable = false)
    private String identifier;

    @NotNull
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}