package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

  private final WebDriver driver;
  private Long productId;
  private List<Long> productIds = new ArrayList<>();
  private int selectedQuantity;

  public ProductStepDefs(){
    this.driver = Hooks.getDriver();
  }

  @Given("the user is on a product page")
  public void theUserIsOnAProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productId = 39654522814667L;
    productIds.add(39654522814667L);
    new ProductDisplayPage();
  }

  @When("the user is on a second product page")
  public void theUserIsOnASecondProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-training-tank-black");
    productIds.add(39245926695115L);
    new ProductDisplayPage();
  }

  @When("adding the product to the Bag")
  public void addingTheProductToTheBag() {
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @Then("the product should appear in the Bag")
  public void theProductShouldAppearInTheBag() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  @Given("there are products in the bag")
  public void thereAreProductsInTheBag() {
    BagPage bagPage = new BagPage();
    assertThat(bagPage.getVariantIdsInBag()).isNotEmpty();
  }

  @When("I remove product")
  public void iRemoveProduct() {
    BagPage bagPage = new BagPage();
    bagPage.removeProductFromBag();
  }

  @Then("both products are in the Bag")
  public void bothProductsAreInTheBag() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productIds.getFirst());
    assertThat(variantIds).as("Expected product is in Bag").contains(productIds.getLast());
  }

  @Then("one product is removed")
  public void oneProductIsRemoved() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productIds.getFirst());
    assertThat(variantIds).as("Expected product is in Bag").doesNotContain(productIds.getLast());
  }

  @When("I add quantity to first product in the bag")
  public void iAddQuantityToFirstProductInTheBag() {
    BagPage bagPage = new BagPage();
    selectedQuantity = bagPage.getQuantityOfProduct();
    bagPage.changeQuantity(selectedQuantity+1);
  }

  @Then("product quantity is increased")
  public void productQuantityIsIncreased() {
    BagPage bagPage = new BagPage();
    assertThat(bagPage.getQuantityOfProduct()).isEqualTo(selectedQuantity + 1);
  }

  @When("I remove quantity for first product in the bag")
  public void iRemoveQuantityForFirstProductInTheBag() {
    BagPage bagPage = new BagPage();
    selectedQuantity = bagPage.getQuantityOfProduct();
    bagPage.changeQuantity(selectedQuantity-1);
  }

  @Then("product quantity is decreased")
  public void productQuantityIsDecreased() {
    BagPage bagPage = new BagPage();
    assertThat(bagPage.getQuantityOfProduct()).isEqualTo(selectedQuantity - 1);
  }
}
