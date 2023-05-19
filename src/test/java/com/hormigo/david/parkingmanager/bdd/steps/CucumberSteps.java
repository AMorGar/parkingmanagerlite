package com.hormigo.david.parkingmanager.bdd.steps;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hormigo.david.parkingmanager.bdd.CucumberConfiguration;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.service.UserService;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberConfiguration {

    @MockBean
    private UserService userService;
    @Value("${local.server.port}")
    private  int port;
    private static ChromeDriver driver;
    @BeforeAll
    public static void prepareWebDriver() {

        System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        
    }
//estas en indice a lista de usuarios
    @Given("un usuario esta en la pagina inicial")
    public void openHome() {
        driver.get("http://localhost:" + port + "/");
    }

    @When("el usuario hace click sobre el botón de Usuarios")
    public void clickUserListButton(){
        driver.findElement(By.id("to-users-link")).click();
    }

    @Then("se muestran todos los usuarios del sistema")
    public void showUserList(){
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/users"));
    }
    
//desde crear usuarios a lista de usuarios
    @Given("Dado un usuario esta en la pagina creacion de usuario")
    public void openUserCreateForm()
    {
        driver.get("http://localhost:" + port + "/createUser");
    }

    @When("el usuario hace click sobre el botón de Usuarios")
    public void clickUserButton(){
        driver.findElement(By.id("to-users-link")).click();

    }

    @Then("se muestran todos los usuarios del sistema")
    public void navigateToUsersList(){
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/users"));
    }


// Desde inicio a lista de soteos
    @Given("un usuario esta en la pagina inicial")
    public void openDraw() {
        driver.get("http://localhost:" + port + "/");
    }

    @When("el usuario hace click sobre el botón de Lista De Sorteos")
    public void clickDrawist(){
        driver.findElement(By.id("to-draws-link")).click();

    }

    @Then("se muestra la Lista de Sorteos del sistema")
    public void navigateToDraw(){
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/draws"));
    }

// Desde lista de sorteos a crear sorteos
    @Given("un usuario esta en la pagina lista de sorteos")
    public void openCreateDraw() {
        driver.get("http://localhost:" + port + "/newDraw");
    }

    @When("el usuario hace click sobre el botón de crear Sorteo")
    public void clickUserCreateDraw(){
        driver.findElement(By.className("button is-primary")).click();

    }

    @Then("se muestra el formulario de Crear Sorteos ")
    public void navigateToCreateDraw(){
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/newDraw"));
    }

//desde lista de usuarios a crear usuarios
    @Given("un usuario esta en la pagina lista de usuarios")
    public void openUserCreateUser()
    {
        driver.get("http://localhost:" + port + "/users");
    }

    @When("el usuario hace click sobre el botón de Crear Usuarios")
    public void clickCreateUserButton(){
        driver.findElement(By.id("users-button-create")).click();

    }

    @Then("se muestra el formulario de Crear Usuarios")
    public void navigateToCreateUser(){
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/newUser"));
    }

//---------------------------------------------------------------------------------------------------------

@Dado("un usuario esta en la pagina {}")
public void openPage(String pageName) {
    driver.get(getUrlFromPageName(pageName));
}
@Dado("el correo {} no esta asignado a otro usuario")
public void mockUserNotExists(String email){
    when(mockedUserRepository.findByEmail(email)).thenReturn(null);    
}
@Cuando("relleno el campo {} con {}")
public void populateField(String fieldName,String fieldValue){
    WebElement inputField = driver.findElement(By.id(getFieldIdFromName(fieldName)));
    inputField.sendKeys(fieldValue);
}
@Cuando("el usuario hace click sobre el botón de {}")
public void clickButton(String buttonName) {
    String buttonId = "";
    switch (buttonName) {
        case "Usuarios":
            buttonId = "to-users-link";
            break;
        case "Sorteos":
            buttonId = "to-draws-link";
            break;
        case "crear usuario":
            buttonId = "user-create-button-submit";
            break;
        case "crear sorteo":
            buttonId = "button-create-draw";
        default:
            break;
    }
    driver.findElement(By.id(buttonId)).click();
}
@Entonces("esta en la pagina de {}")
public void isInPage(String pageName) {
    assertTrue(driver.getCurrentUrl().equals(getUrlFromPageName(pageName)));
}
@Entonces("se ha persistido el usuario en la base de datos")
public void checkUserWasSaved(){
    verify(mockedUserRepository,times(1)).save(any(User.class));
}
@Entonces("se muestra un campo de {}")
public void fieldIsDisplayed(String fieldName){
    String fieldId = getFieldIdFromName(fieldName);
    WebElement field = driver.findElement(By.id(fieldId));
    
    assertTrue(field.isDisplayed());
}
private String getUrlFromPageName(String pageName) {
    String endPoint = "";
    switch (pageName) {
        case "inicial":
            endPoint = "/";
            break;
        case "lista de usuarios":
            endPoint = "/users";
            break;
        case "lista de sorteos":
            endPoint = "/draws";
            break;
        case "creación de usuarios":
            endPoint = "/newUser";
            break;
        case "creacion de sorteos":
            endPoint = "/newDraw";
            break;
        default:
            break;
    }
    return getUrlFromEndPoint(endPoint);
}



}
