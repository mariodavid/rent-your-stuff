package de.diedavids.jmix.rys.product.screen.productprice;

import de.diedavids.jmix.rys.RentYourStuffProperties;
import de.diedavids.jmix.rys.entity.Currency;
import de.diedavids.jmix.rys.entity.Money;
import io.jmix.core.Messages;
import io.jmix.ui.component.CurrencyField;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.product.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("rys_ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {

    @Autowired
    private RentYourStuffProperties rentYourStuffProperties;
    @Autowired
    private CurrencyField<BigDecimal> priceAmountField;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private Messages messages;

    @Subscribe
    public void onInitEntity(InitEntityEvent<ProductPrice> event) {
        Money defaultPrice = dataContext.create(Money.class);
        defaultPrice.setCurrency(rentYourStuffProperties.getCurrency());
        event.getEntity().setPrice(defaultPrice);
    }


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        priceAmountField.setCurrency(
                messages.getMessage(getEditedEntity().getPrice().getCurrency())
        );
    }

}