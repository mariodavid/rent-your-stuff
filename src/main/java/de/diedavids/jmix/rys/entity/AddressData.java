package de.diedavids.jmix.rys.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressData {
    String street;
    String postCode;
    String city;
}
    