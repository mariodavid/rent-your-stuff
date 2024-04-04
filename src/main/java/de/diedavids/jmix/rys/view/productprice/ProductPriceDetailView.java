package de.diedavids.jmix.rys.view.productprice;

import com.vaadin.flow.component.html.Span;
import de.diedavids.jmix.rys.RentYourStuffProperties;
import de.diedavids.jmix.rys.entity.Money;
import de.diedavids.jmix.rys.product.ProductPrice;

import de.diedavids.jmix.rys.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route(value = "productPrices/:id", layout = MainView.class)
@ViewController("rys_ProductPrice.detail")
@ViewDescriptor("product-price-detail-view.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceDetailView extends StandardDetailView<ProductPrice> {

    @Autowired
    private RentYourStuffProperties rentYourStuffProperties;
    @ViewComponent
    private DataContext dataContext;
    @Autowired
    private Messages messages;
    @ViewComponent
    private TypedTextField<BigDecimal> priceAmountField;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInitEntity(InitEntityEvent<ProductPrice> event) {
        Money defaultPrice = dataContext.create(Money.class);
        defaultPrice.setCurrency(rentYourStuffProperties.getCurrency());
        event.getEntity().setPrice(defaultPrice);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        Span currency = uiComponents.create(Span.class);
        currency.setText(messages.getMessage(getEditedEntity().getPrice().getCurrency()));
        priceAmountField.setSuffixComponent(currency);
    }
}
