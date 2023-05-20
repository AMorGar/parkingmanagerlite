package com.hormigo.david.parkingmanager.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.verification.VerificationMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.hormigo.david.parkingmanager.draw.domain.Draw;
import com.hormigo.david.parkingmanager.draw.domain.DrawRepository;
import com.hormigo.david.parkingmanager.draw.service.DrawServiceImpl;
import com.hormigo.david.parkingmanager.bdd.CucumberConfiguration;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserRepository;
import com.hormigo.david.parkingmanager.user.service.UserService;
import com.hormigo.david.parkingmanager.user.service.UserServiceImpl;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberConfiguration {

    private static ChromeDriver driver;
    @BeforeAll
    public static void prepareWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

    }
    @MockBean
    private UserRepository mockedUserRepository;
    @Spy
    @InjectMocks
    private UserServiceImpl mockedUserService;
    @MockBean
    private UserRepository mockedDrawRepository;
    @Spy
    @InjectMocks
    private UserServiceImpl mockedDrawService;
    @Value("${local.server.port}")
    private int port;

    @Before
    public void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        MockitoAnnotations.openMocks(this);
        clearInvocations(mockedUserRepository);
        clearInvocations(mockedUserService);
        clearInvocations(mockedDrawRepository);
        clearInvocations(mockedDrawService);
    }

    @After
    public void quitDriver() {
        driver.quit();
    
    }



    @Dado("un usuario esta en la pagina {}")
    public void openPage(String pageName) {
        driver.get(getUrlFromPageName(pageName));
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
            case "creacion de usuarios":
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
    private String getUrlFromEndPoint(String endpoint) {
        return "http://localhost:" + port + endpoint;
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
            case "usuarios":
                buttonId = "to-users-link";
                break;
            case "sorteos":
                buttonId = "to-draws-link";
                break;
            case "guardar usuario":
                buttonId = "user-create-button-submit";
                break;
            case "crear usuario":
                buttonId = "users-button-create";
                break;
            case "crear sorteos":
                buttonId = "draws-button-create";
                break;
            case "guardar sorteo":
                buttonId = "draw-create-button-submit";
                break;
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

    @Entonces("se ha persistido el sorteo en la base de datos")
    public void checkDrawWasSaved() {
        verify(mockedDrawRepository,times(1)).save(any(Draw.class));
    }
    @Entonces("se muestra el campo de {}")
    public void fieldIsDisplayed(String fieldName){
        String fieldId = getFieldIdFromName(fieldName);
        WebElement field = driver.findElement(By.id(fieldId));
        
        assertTrue(field.isDisplayed());
    }

    private String getFieldIdFromName(String fieldName) {
        String fieldId ="";
        switch (fieldId) {
            case "el boton inicio":
            fieldId= "to-home-link";

            case "el boton usuarios":
            fieldId= "to-users-link";

            case "el boton sorteos":
            fieldId= "to-draws-link";

            case "el boton de crear usuarios":
            fieldId= "users-button-create";

            case "el boton de crear sorteos":
            fieldId= "draws-button-create";

            case "el boton de guardar usuario":
            fieldId= "user-create-button-submit";

            case "el boton de guardar sorteo":
            fieldId= "draw-create-button-submit";

            case "el campo correo":
            fieldId= "user-create-field-email";

            case "el campo nombre":
            fieldId= "user-create-field-name";

            case "el campo primer apellido":
            fieldId= "user-create-field-lastname1";

            case "el campo descripcion":
            fieldId= "draw-field-description";

        }
        return fieldId;
    }
    @Entonces("el titulo es de {}")
    public void titleIsShowing(String titleHtml) {
        String title = "";
        String titleId = "";
        switch (titleHtml) {
            case "lista de sorteos":
                title = "Sorteos";
                titleId = "title-draws";
                break;
            case "creacion de sorteos":
                title = "Crear nuevo sorteo";
                titleId = "title-create-draws";
                break;
        }

        WebElement titulo = driver.findElement(By.id(titleId));
        assertEquals(title, titulo.getText());

    }




//------------------------------------------------------------------------------------------------------------------------------------------
}
