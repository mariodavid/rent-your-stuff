package de.diedavids.jmix.rys.product;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.internal.Iterables;

/**
 * {@link Product} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class ProductAssert extends AbstractObjectAssert<ProductAssert, Product> {

  /**
   * Creates a new <code>{@link ProductAssert}</code> to make assertions on actual Product.
   * @param actual the Product we want to make assertions on.
   */
  public ProductAssert(Product actual) {
    super(actual, ProductAssert.class);
  }

  /**
   * An entry point for ProductAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myProduct)</code> and get specific assertion with code completion.
   * @param actual the Product we want to make assertions on.
   * @return a new <code>{@link ProductAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static ProductAssert assertThat(Product actual) {
    return new ProductAssert(actual);
  }

  /**
   * Verifies that the actual Product's category is equal to the given one.
   * @param category the given category to compare the actual Product's category to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's category is not equal to the given one.
   */
  public ProductAssert hasCategory(ProductCategory category) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting category of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    ProductCategory actualCategory = actual.getCategory();
    if (!Objects.deepEquals(actualCategory, category)) {
      failWithMessage(assertjErrorMessage, actual, category, actualCategory);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's createdBy is equal to the given one.
   * @param createdBy the given createdBy to compare the actual Product's createdBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's createdBy is not equal to the given one.
   */
  public ProductAssert hasCreatedBy(String createdBy) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting createdBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualCreatedBy = actual.getCreatedBy();
    if (!Objects.deepEquals(actualCreatedBy, createdBy)) {
      failWithMessage(assertjErrorMessage, actual, createdBy, actualCreatedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's createdDate is equal to the given one.
   * @param createdDate the given createdDate to compare the actual Product's createdDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's createdDate is not equal to the given one.
   */
  public ProductAssert hasCreatedDate(java.util.Date createdDate) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting createdDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualCreatedDate = actual.getCreatedDate();
    if (!Objects.deepEquals(actualCreatedDate, createdDate)) {
      failWithMessage(assertjErrorMessage, actual, createdDate, actualCreatedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's deletedBy is equal to the given one.
   * @param deletedBy the given deletedBy to compare the actual Product's deletedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's deletedBy is not equal to the given one.
   */
  public ProductAssert hasDeletedBy(String deletedBy) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting deletedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualDeletedBy = actual.getDeletedBy();
    if (!Objects.deepEquals(actualDeletedBy, deletedBy)) {
      failWithMessage(assertjErrorMessage, actual, deletedBy, actualDeletedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's deletedDate is equal to the given one.
   * @param deletedDate the given deletedDate to compare the actual Product's deletedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's deletedDate is not equal to the given one.
   */
  public ProductAssert hasDeletedDate(java.util.Date deletedDate) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting deletedDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualDeletedDate = actual.getDeletedDate();
    if (!Objects.deepEquals(actualDeletedDate, deletedDate)) {
      failWithMessage(assertjErrorMessage, actual, deletedDate, actualDeletedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's description is equal to the given one.
   * @param description the given description to compare the actual Product's description to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's description is not equal to the given one.
   */
  public ProductAssert hasDescription(String description) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting description of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualDescription = actual.getDescription();
    if (!Objects.deepEquals(actualDescription, description)) {
      failWithMessage(assertjErrorMessage, actual, description, actualDescription);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's id is equal to the given one.
   * @param id the given id to compare the actual Product's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's id is not equal to the given one.
   */
  public ProductAssert hasId(java.util.UUID id) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting id of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.UUID actualId = actual.getId();
    if (!Objects.deepEquals(actualId, id)) {
      failWithMessage(assertjErrorMessage, actual, id, actualId);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's lastModifiedBy is equal to the given one.
   * @param lastModifiedBy the given lastModifiedBy to compare the actual Product's lastModifiedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's lastModifiedBy is not equal to the given one.
   */
  public ProductAssert hasLastModifiedBy(String lastModifiedBy) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastModifiedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualLastModifiedBy = actual.getLastModifiedBy();
    if (!Objects.deepEquals(actualLastModifiedBy, lastModifiedBy)) {
      failWithMessage(assertjErrorMessage, actual, lastModifiedBy, actualLastModifiedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's lastModifiedDate is equal to the given one.
   * @param lastModifiedDate the given lastModifiedDate to compare the actual Product's lastModifiedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's lastModifiedDate is not equal to the given one.
   */
  public ProductAssert hasLastModifiedDate(java.util.Date lastModifiedDate) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastModifiedDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualLastModifiedDate = actual.getLastModifiedDate();
    if (!Objects.deepEquals(actualLastModifiedDate, lastModifiedDate)) {
      failWithMessage(assertjErrorMessage, actual, lastModifiedDate, actualLastModifiedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's name is equal to the given one.
   * @param name the given name to compare the actual Product's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's name is not equal to the given one.
   */
  public ProductAssert hasName(String name) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualName = actual.getName();
    if (!Objects.deepEquals(actualName, name)) {
      failWithMessage(assertjErrorMessage, actual, name, actualName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's prices contains the given ProductPrice elements.
   * @param prices the given elements that should be contained in actual Product's prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices does not contain all given ProductPrice elements.
   */
  public ProductAssert hasPrices(ProductPrice... prices) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given ProductPrice varargs is not null.
    if (prices == null) failWithMessage("Expecting prices parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getPrices(), prices);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's prices contains the given ProductPrice elements in Collection.
   * @param prices the given elements that should be contained in actual Product's prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices does not contain all given ProductPrice elements.
   */
  public ProductAssert hasPrices(java.util.Collection<? extends ProductPrice> prices) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given ProductPrice collection is not null.
    if (prices == null) {
      failWithMessage("Expecting prices parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getPrices(), prices.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's prices contains <b>only</b> the given ProductPrice elements and nothing else in whatever order.
   * @param prices the given elements that should be contained in actual Product's prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices does not contain all given ProductPrice elements.
   */
  public ProductAssert hasOnlyPrices(ProductPrice... prices) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given ProductPrice varargs is not null.
    if (prices == null) failWithMessage("Expecting prices parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getPrices(), prices);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's prices contains <b>only</b> the given ProductPrice elements in Collection and nothing else in whatever order.
   * @param prices the given elements that should be contained in actual Product's prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices does not contain all given ProductPrice elements.
   */
  public ProductAssert hasOnlyPrices(java.util.Collection<? extends ProductPrice> prices) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given ProductPrice collection is not null.
    if (prices == null) {
      failWithMessage("Expecting prices parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getPrices(), prices.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's prices does not contain the given ProductPrice elements.
   *
   * @param prices the given elements that should not be in actual Product's prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices contains any given ProductPrice elements.
   */
  public ProductAssert doesNotHavePrices(ProductPrice... prices) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given ProductPrice varargs is not null.
    if (prices == null) failWithMessage("Expecting prices parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getPrices(), prices);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's prices does not contain the given ProductPrice elements in Collection.
   *
   * @param prices the given elements that should not be in actual Product's prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices contains any given ProductPrice elements.
   */
  public ProductAssert doesNotHavePrices(java.util.Collection<? extends ProductPrice> prices) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given ProductPrice collection is not null.
    if (prices == null) {
      failWithMessage("Expecting prices parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getPrices(), prices.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product has no prices.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's prices is not empty.
   */
  public ProductAssert hasNoPrices() {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have prices but had :\n  <%s>";

    // check
    if (actual.getPrices().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getPrices());
    }

    // return the current assertion for method chaining
    return this;
  }


  /**
   * Verifies that the actual Product's stockItems contains the given StockItem elements.
   * @param stockItems the given elements that should be contained in actual Product's stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems does not contain all given StockItem elements.
   */
  public ProductAssert hasStockItems(StockItem... stockItems) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given StockItem varargs is not null.
    if (stockItems == null) failWithMessage("Expecting stockItems parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getStockItems(), stockItems);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's stockItems contains the given StockItem elements in Collection.
   * @param stockItems the given elements that should be contained in actual Product's stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems does not contain all given StockItem elements.
   */
  public ProductAssert hasStockItems(java.util.Collection<? extends StockItem> stockItems) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given StockItem collection is not null.
    if (stockItems == null) {
      failWithMessage("Expecting stockItems parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getStockItems(), stockItems.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's stockItems contains <b>only</b> the given StockItem elements and nothing else in whatever order.
   * @param stockItems the given elements that should be contained in actual Product's stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems does not contain all given StockItem elements.
   */
  public ProductAssert hasOnlyStockItems(StockItem... stockItems) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given StockItem varargs is not null.
    if (stockItems == null) failWithMessage("Expecting stockItems parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getStockItems(), stockItems);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's stockItems contains <b>only</b> the given StockItem elements in Collection and nothing else in whatever order.
   * @param stockItems the given elements that should be contained in actual Product's stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems does not contain all given StockItem elements.
   */
  public ProductAssert hasOnlyStockItems(java.util.Collection<? extends StockItem> stockItems) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given StockItem collection is not null.
    if (stockItems == null) {
      failWithMessage("Expecting stockItems parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getStockItems(), stockItems.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's stockItems does not contain the given StockItem elements.
   *
   * @param stockItems the given elements that should not be in actual Product's stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems contains any given StockItem elements.
   */
  public ProductAssert doesNotHaveStockItems(StockItem... stockItems) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given StockItem varargs is not null.
    if (stockItems == null) failWithMessage("Expecting stockItems parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getStockItems(), stockItems);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's stockItems does not contain the given StockItem elements in Collection.
   *
   * @param stockItems the given elements that should not be in actual Product's stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems contains any given StockItem elements.
   */
  public ProductAssert doesNotHaveStockItems(java.util.Collection<? extends StockItem> stockItems) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // check that given StockItem collection is not null.
    if (stockItems == null) {
      failWithMessage("Expecting stockItems parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getStockItems(), stockItems.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product has no stockItems.
   * @return this assertion object.
   * @throws AssertionError if the actual Product's stockItems is not empty.
   */
  public ProductAssert hasNoStockItems() {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have stockItems but had :\n  <%s>";

    // check
    if (actual.getStockItems().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getStockItems());
    }

    // return the current assertion for method chaining
    return this;
  }


  /**
   * Verifies that the actual Product's tenant is equal to the given one.
   * @param tenant the given tenant to compare the actual Product's tenant to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's tenant is not equal to the given one.
   */
  public ProductAssert hasTenant(String tenant) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting tenant of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualTenant = actual.getTenant();
    if (!Objects.deepEquals(actualTenant, tenant)) {
      failWithMessage(assertjErrorMessage, actual, tenant, actualTenant);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Product's version is equal to the given one.
   * @param version the given version to compare the actual Product's version to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Product's version is not equal to the given one.
   */
  public ProductAssert hasVersion(Integer version) {
    // check that actual Product we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting version of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualVersion = actual.getVersion();
    if (!Objects.deepEquals(actualVersion, version)) {
      failWithMessage(assertjErrorMessage, actual, version, actualVersion);
    }

    // return the current assertion for method chaining
    return this;
  }

}