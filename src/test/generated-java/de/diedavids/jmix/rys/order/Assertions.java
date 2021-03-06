package de.diedavids.jmix.rys.order;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions extends org.assertj.core.api.Assertions {

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.customer.CustomerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.customer.CustomerAssert assertThat(de.diedavids.jmix.rys.customer.Customer actual) {
    return new de.diedavids.jmix.rys.customer.CustomerAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.entity.AddressAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.entity.AddressAssert assertThat(de.diedavids.jmix.rys.entity.Address actual) {
    return new de.diedavids.jmix.rys.entity.AddressAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.entity.MoneyAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.entity.MoneyAssert assertThat(de.diedavids.jmix.rys.entity.Money actual) {
    return new de.diedavids.jmix.rys.entity.MoneyAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.order.OrderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.order.OrderAssert assertThat(de.diedavids.jmix.rys.order.Order actual) {
    return new de.diedavids.jmix.rys.order.OrderAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.order.OrderLineAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.order.OrderLineAssert assertThat(de.diedavids.jmix.rys.order.OrderLine actual) {
    return new de.diedavids.jmix.rys.order.OrderLineAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.product.ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.product.ProductAssert assertThat(de.diedavids.jmix.rys.product.Product actual) {
    return new de.diedavids.jmix.rys.product.ProductAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.product.ProductCategoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.product.ProductCategoryAssert assertThat(de.diedavids.jmix.rys.product.ProductCategory actual) {
    return new de.diedavids.jmix.rys.product.ProductCategoryAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link de.diedavids.jmix.rys.product.ProductPriceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static de.diedavids.jmix.rys.product.ProductPriceAssert assertThat(de.diedavids.jmix.rys.product.ProductPrice actual) {
    return new de.diedavids.jmix.rys.product.ProductPriceAssert(actual);
  }

  protected Assertions() {
    // empty
  }
}