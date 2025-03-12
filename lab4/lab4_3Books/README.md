# b) Check the locators being used. Are there any instances of “xpath”? What about identifier-based locators? Which locator strategies are more robust?
As you can see in the following java code correspondent to the previous test
```
 driver.findElement(By.cssSelector("div:nth-child(1) > .Navbar_searchBar__3q5Gb > .Navbar_searchBarInput__w8FwI")).click();
    driver.findElement(By.cssSelector("div:nth-child(1) > .Navbar_searchBar__3q5Gb > .Navbar_searchBarInput__w8FwI")).sendKeys("Harry Potter");
    driver.findElement(By.cssSelector("div:nth-child(1) > .Navbar_searchBar__3q5Gb .Navbar_searchBtnIcon__25k0u")).click();
    assertThat(driver.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")).getText(), is("Harry Potter and the Sorcerer\\\'s Stone"));
    assertThat(driver.findElement(By.cssSelector(".SearchList_bookAuthor__3giPc")).getText(), is("J.K. Rowling"));
``` 
I can make the following conclusions:

My test uses CSS selectors not XPATH selectors nor identifier-based selectors.
The most robust locatorS strategies are:
- Identifier-based: The ID is assumed to be unique, so it can locate more efficiently elements.
- XPATH: Xpath is used to locate a web element based on its XML path. So it can be used to locate any element on the web page even if they have no id, class or name.

> Useful link, tutorials on Selenium and locators: https://www.softwaretestinghelp.com/using-selenium-xpath-and-other-locators-selenium-tutorial-5/

# Notes:
### Using XPath as a Locator
Xpath is used to locate a web element based on its XML path. XML stands for Extensible Markup Language and is used to store, organize, and transport arbitrary data. It stores data in a key-value pair which is very similar to HTML tags. Both the markup languages and since they fall under the same umbrella, XPath can locate HTML elements.

The fundamental behind locating elements using Xpath is the traversing between various elements across the entire page and thus enabling a user to find an element with the reference of another element.

Xpath can be created in two ways:

Relative Xpath

Relative Xpath begins from the current location and is prefixed with a “//”.

For example: //span[@class=’Email’]

Absolute Xpath

Absolute Xpath begins with a root path and is prefixed with a “/”.

For example: /HTML/body/div/div[@id=’Email’]

Key Points:

The success rate of finding an element using Xpath is too high. Along with the previous statement, Xpath can find relatively all the elements on a web page. Thus, Xpaths can locate elements having no id, class, or name.
Creating a valid Xpath is a tricky and complex process. There are plug-ins available to generate Xpaths but most of the time, the generated Xpaths cannot identify the web element correctly.
While creating xpath, the user should know the various nomenclatures and protocols.


### Using ID as a Locator
The best and the most popular method to identify web elements is to use ID. The ID of each element is alleged to be unique.


### Locators
![locators](image.png)

### Ex3 
Use the correct locators: https://www.selenium.dev/documentation/webdriver/elements/locators/

Waits : https://www.selenium.dev/documentation/webdriver/waits/

Example: 

```
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(By.className("SearchList_bookInfoContainer__bkMz1")));
```