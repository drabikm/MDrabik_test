package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  private static final By REMOVE_PRODUCT_BUTTON = By.cssSelector("[data-locator-id^='miniBag-remove']");
  private static final By QUANTITY_DROPDOWN = By.cssSelector("[data-locator-id^='miniBag-quantityDropdown']");
  private static final By LOADING_OVERLAY = By.cssSelector("[data-locator-id='miniBag-loadingOverlay-read']");

  public BagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
      .map(this::getVariantId)
      .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public void removeProductFromBag() {
    getCommands().waitForAndClickOnElementLocated(REMOVE_PRODUCT_BUTTON);
    getCommands().waitForElementToDisappear(LOADING_OVERLAY);
  }

  public void changeQuantity(Integer newQuantity) {
    getCommands().selectFromDropdown(QUANTITY_DROPDOWN, newQuantity.toString());
    getCommands().waitForElementToDisappear(LOADING_OVERLAY);
  }

  public Integer getQuantityOfProduct() {
    return Integer.parseInt(getCommands().getSelected(QUANTITY_DROPDOWN));
  }
}
