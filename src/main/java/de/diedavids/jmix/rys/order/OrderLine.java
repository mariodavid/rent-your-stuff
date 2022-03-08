package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.entity.StandardTenantEntity;
import de.diedavids.jmix.rys.order.validation.ValidRentalPeriod;
import de.diedavids.jmix.rys.product.StockItem;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.validation.group.UiCrossFieldChecks;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.Optional;

@JmixEntity
@Table(name = "RYS_ORDER_LINE", indexes = {
        @Index(name = "IDX_ORDERLINE_STOCK_ITEM_ID", columnList = "STOCK_ITEM_ID"),
        @Index(name = "IDX_ORDERLINE_ORDER_ID", columnList = "ORDER_ID")
})
@Entity(name = "rys_OrderLine")
@ValidRentalPeriod(groups = {Default.class, UiCrossFieldChecks.class})
public class OrderLine extends StandardTenantEntity {
    @InstanceName
    @NotNull
    @JoinColumn(name = "STOCK_ITEM_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private StockItem stockItem;

    @NotNull
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @FutureOrPresent
    @NotNull
    @Column(name = "STARTS_AT", nullable = false)
    private LocalDateTime startsAt;

    @FutureOrPresent
    @NotNull
    @Column(name = "ENDS_AT", nullable = false)
    private LocalDateTime endsAt;

    @Transient
    @JmixProperty
    @DependsOnProperties({"stockItem", "order"})
    public String getCaption() {
        return Optional.ofNullable(getStockItem())
                .map(it -> String.format("%s - %s", it.getIdentifier(), getOrder().getCustomer().getInstanceName()))
                .orElse("");
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
}