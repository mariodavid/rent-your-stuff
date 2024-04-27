package de.diedavids.jmix.rys;

import de.diedavids.jmix.rys.entity.Currency;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties("rys")
public class RentYourStuffProperties {

    Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
