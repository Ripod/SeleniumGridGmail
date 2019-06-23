package ru.ripod.tests.pageobjects;

public class DraftPage extends BasicPage {
    String createdDraftXpath = "//tr//span[@class = 'bog']/span[text() = '%s']";

    public DraftPage(String browserName) {
        super(browserName);
    }

    public void checkCreatedDraftVisible(String draftTheme){
        driver.checkElementIsPresent(String.format(createdDraftXpath, draftTheme));
    }

    public void openCreatedDraft(String draftTheme){
        driver.click(String.format(createdDraftXpath, draftTheme));
    }
}
