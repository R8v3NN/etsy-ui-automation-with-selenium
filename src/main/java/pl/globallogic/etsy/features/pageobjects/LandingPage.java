package pl.globallogic.etsy.features.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class LandingPage {

    protected Logger logger = LoggerFactory.getLogger(LandingPage.class);
    private static final String ACCEPT_DEFAULT_PRIVACY_POLICY_BUTTON = "//button[@data-gdpr-single-choice-accept='true']";
    private static final String SEARCH_FIELD_ID = "global-enhancements-search-query";
    public static final String SEARCH_RESULT_TITLE = "h3.v2-listing-card__title";
    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("https://www.etsy.com");
    }

    public void acceptDefaultPrivacyPolicy() {
        logger.info("Accepting default privacy policy");
        WebElement acceptButton =
                new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(ACCEPT_DEFAULT_PRIVACY_POLICY_BUTTON)));
        acceptButton.click();
    }

    public void searchFor(String query) {
        logger.info("Searching for items with query '{}'", query);
        Boolean isPrivacyPolicyModalDisappeared = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.invisibilityOfElementLocated(By.xpath(ACCEPT_DEFAULT_PRIVACY_POLICY_BUTTON))
        );
        if (isPrivacyPolicyModalDisappeared) {
            WebElement searchField = driver.findElement(By.id(SEARCH_FIELD_ID));
            searchField.sendKeys(query + Keys.ENTER);
        }
    }

    public boolean isSearchResultsValidFor(String validQuery) {
        logger.info("Verifying search results for '{}'",validQuery);
        List<WebElement> itemTitles = driver.findElements(By.cssSelector(SEARCH_RESULT_TITLE));
        boolean isTokenPresentInAllResults = false;
        List<String> tokenizedQuery = List.of(validQuery.toLowerCase().split(" "));
        for (WebElement title : itemTitles) {
            List<String> tokenizedTitle = List.of(title.getText().toLowerCase().split(" "));
            if ( tokenizedTitle.containsAll(tokenizedQuery) ) {
                isTokenPresentInAllResults = true;
                return isTokenPresentInAllResults;
            }
        }
        return isTokenPresentInAllResults;
    }
}
