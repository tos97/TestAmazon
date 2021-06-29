package it.tosiani.step.cucumber;

import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.java.After;
import cucumber.api.java.en.*;
import it.tosiani.Utility.Utils;
import it.tosiani.drivers.ManagmentDriver;
import it.tosiani.selenium.DefaultChromeOptions;
import it.tosiani.step.AmazonStep;
import it.tosiani.step.MobileStep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;

public class CucumberStep {
    private ChromeDriver driver = null;
    private WebElement webElement = null;
    private Properties prop = null;
    private boolean mobile;
    private AmazonStep step = null;
    private MobileStep stepM = null;

    private String risultato;
    private String elementClick = "";
    private boolean passato = false;
    private int totaleCercato = 0;
    private int totale = -1;


    @Given("^the opening of the test app on mobile: (.*)$")
    public void doSomethingBeforeStep(boolean mob){
        mobile = mob;
        DefaultChromeOptions defaultChromeOptions = new DefaultChromeOptions(new ChromeOptions());
        if(mobile){
            defaultChromeOptions.addArguments("--window-size=375,812");
            defaultChromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
        }

        ManagmentDriver.startDriver(defaultChromeOptions);
        driver = ManagmentDriver.getDriver();
        prop = Utils.loadProp("amazonWeb");
        step = new AmazonStep(driver,prop);
        stepM = new MobileStep(driver,prop);
    }

    @Given("open amazon site")
    public void startAmazon() throws InterruptedException {
        step.start(mobile);
    }


    @When("the list of featured articles is found and prints them in the console")
    public void test001() throws InterruptedException {
        risultato = "";
        if (mobile) {
            System.out.println("Fatto sul mobile");
            risultato = stepM.ritornaListaBestseller(true);
        } else {
            System.out.println("Fatto sul web");
            risultato = step.ritornaListaBestseller(true);
        }
        System.out.println("Lista articoli in evidenza:\n"+risultato);
    }

    @Then("check if it found anything")
    public void checkLenght(){
        assertThat(risultato.length(),is(not(0)));
    }

    @When("found and prints in the console the list of categories")
    public void test002() throws InterruptedException {
        risultato = "";
        if (mobile) {
            System.out.println("Fatto sul mobile");
            risultato = stepM.ritornaListaCategorie(true);
        } else {
            System.out.println("Fatto sul web");
            risultato = step.ritornaListaCategorie();
        }
        System.out.println("Lista categorie di ricerca:\n"+risultato);
    }

    @When("^searching for (.*) click on a result in the suggestion menu$")
    public void test003(String qry) throws InterruptedException {
        if (mobile) {
            System.out.println("Fatto sul mobile");
            stepM.ricercaQuery(qry);
            Utils.getScreenshot();
            elementClick = stepM.ricercaEnter();
            Utils.getScreenshot();
        } else {
            System.out.println("Fatto sul web");
            step.ricercaQuery(qry);
            Utils.getScreenshot();
            elementClick = step.ricercaEnter();
            Utils.getScreenshot();
        }
    }

    @Then("check if he clicked the correct item")
    public void checkString(){
        if (mobile){
            assertThat(elementClick, is(driver.findElement(By.id(prop.getProperty("id.input.search"))).getAttribute("value")));
        } else {
            assertThat(elementClick, is(driver.findElement(By.id(prop.getProperty("id.search.bar"))).getAttribute("value")));
        }
    }

    @When("searched for an item then starts saving items")
    public void test004(){
        WebElement elemento = driver.findElement(By.className(prop.getProperty("class.risultati.ricerca")));
        risultato = "";

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(3000);
                String pagina = "Pagina " + (i + 1);
                System.out.println(pagina);
                System.out.println("--------------");
                if (mobile) {
                    risultato = stepM.stampaLista(elemento);
                } else {
                    risultato = step.stampaLista(elemento);
                }
                if(mobile){
                    if (stepM.getIndice() > 1)
                        passato = true;
                } else {
                    if (step.getIndice() > 1)
                        passato = true;
                }
                /*if(mobile){
                    if (stepM.getIndice() > 5)
                        passato = true;
                } else {
                    if (step.getIndice() > 50)
                        passato = true;
                }*/
                elemento.findElement(By.xpath(prop.getProperty("xpath.btn.next"))).click();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            passato = false;
        }
    }

    @Then("check if it worked")
    public void checkBoolean(){
            assertThat(passato, is(true));
    }

    @When("^has searched for an element then opens the first (.*) results in other tabs$")
    public void test005(int tabs){
        passato = step.ricercaTab(tabs);
    }

    @When("^search how many results come out of the tag: (.*)$")
    public void test006(String ricerca) throws InterruptedException {

        step.navBar("Novità");

        if (mobile){
            System.out.println("Fatto sul mobile");
            stepM.nFiltri(ricerca);
            totale = stepM.getTotale();
            totaleCercato = stepM.getTotaleCercato();
        } else {
            System.out.println("Fatto sul web");
            step.nFiltri(ricerca);
            totale = step.getTotale();
            totaleCercato = step.getTotaleCercato();
        }
    }

    @Then("check if the results are right")
    public void checkInteger(){
        assertThat(totale, is(totaleCercato));
    }

    @When("^the item to search for is (.*) is placed in the cart$")
    public void test007(String qry) throws InterruptedException {
        if (mobile){
            stepM.ricercaQuery(qry);
            stepM.ricercaEnter();
            stepM.selezioneProdotto();
        } else {
            step.ricercaQuery(qry);
            step.ricercaEnter();
            float somma = step.selezioneProdotto();
            System.out.println(somma);
            step.setSommaCarrello(somma);
        }
    }

    @Then("^check if it was really added (.*)$")
    public void checkCart(int inCarrello){
        assertThat(inCarrello, is(Integer.parseInt(driver.findElement(By.id("nav-cart-count")).getText())));
    }

    @Then("empty cart")
    public void checkEmptyCart() throws InterruptedException {
        if (mobile){
            driver.findElement(By.id("nav-button-cart")).click();
            Thread.sleep(2000);
            assertThat(0,is(stepM.removeAll()));
        } else {
            driver.findElement(By.id("nav-cart")).click();
            Thread.sleep(2000);
            assertThat(0,is(step.removeAll()));
        }
    }


    @After
    public void doSomethingAfter() {
        ManagmentDriver.stopDriver();
    }
}