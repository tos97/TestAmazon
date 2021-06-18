package it.tosiani.testAmazon;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import it.tosiani.Utility.Utils;
import it.tosiani.drivers.ManagmentDriver;
import it.tosiani.selenium.DefaultChromeOptions;
import it.tosiani.step.AmazonStep;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Properties;

import static it.tosiani.Utility.GlobalParameters.*;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_001_Amazon {
    static private ChromeDriver driver = null;
    static private WebElement webElement = null;
    static private String nomeProp = "amazon";
    static private ExtentReports extentReports;
    static private ExtentTest extentTest;
    static private Properties prop = null;
    static private boolean mobile = false;
    static private AmazonStep step = null;

    @BeforeAll
    static void beforeAll(){
        DefaultChromeOptions defaultChromeOptions = new DefaultChromeOptions(new ChromeOptions());
        if(mobile){
            defaultChromeOptions.addArguments("--window-size=375,812");
            defaultChromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
            nomeProp = "mobile";
        }

        extentReports = new ExtentReports(REPORT_PATH + File.separator + "amazonReport" + EXT_HTML, true);
        extentReports.loadConfig(new File(REPORT_CONFIG_XML));
        ManagmentDriver.startDriver(defaultChromeOptions);
        driver = ManagmentDriver.getDriver();
        prop = Utils.loadProp("amazonWeb");
        step = new AmazonStep(driver,prop);
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        step.start();
    }


    @Test
    @Order(1)
    @DisplayName("Lista evidenza")
    void Test_001_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test per ritonare la lista degli articoli in evidenza","");
        String risultato = step.ritornaListaBestseller();
        if (risultato.length() > 0)
            extentTest.log(LogStatus.PASS, "Lista articoli in evidenza trovata",risultato);
        else{
            extentTest.log(LogStatus.FAIL, "Lista articoli in evidenza non trovata o con errori","");
            fail();
        }
        Thread.sleep(3000);
    }

    @Test
    @Order(2)
    @DisplayName("Lista categorie")
    void Test_002_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test per ritonare la lista delle categorie per la ricerca","");
        String risultato = step.ritornaListaCategorie();
        if (risultato.length() > 0)
            extentTest.log(LogStatus.PASS, "Lista articoli in evidenza trovata",risultato);
        else{
            extentTest.log(LogStatus.FAIL, "Lista articoli in evidenza non trovata o con errori","");
            fail();
        }
        Thread.sleep(3000);
    }

    @Test
    @Order(3)
    @DisplayName("Lista ricerca con suggerimento")
    void Test_003_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test per cliccare sul terzo elemento suggerito dalla barra di ricerca cercando Iphone","");

        step.ricercaQuery("Iphone");
        extentTest.log(LogStatus.INFO, "Screen prima di cliccare il suggerimento",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        String elementClick = step.ricercaEnter();
        if (driver.findElement(By.id(prop.getProperty("id.search.bar"))).getAttribute("value").equals(elementClick))
            extentTest.log(LogStatus.PASS, "Cliccato elemento corretto tra i suggeriti",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        else{
            extentTest.log(LogStatus.FAIL, "Elemento cliccato non corretto",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
            fail();
        }
        Thread.sleep(3000);
    }

    @Test
    @Order(4)
    @DisplayName("Lista risultati ricerca")
    void Test_004_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test per cliccare sul terzo elemento suggerito dalla barra di ricerca cercando Iphone","");
        step.ricercaQuery("Iphone");
        step.ricercaEnter();
        WebElement elemento = driver.findElement(By.className(prop.getProperty("class.risultati.ricerca")));
        boolean passato = false;

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(2000);
                String pagina = "Pagina " + (i + 1);
                System.out.println(pagina);
                System.out.println("--------------");
                String risultati = step.stampaLista(elemento);
                extentTest.log(LogStatus.INFO, "risultati della " + pagina, risultati);
                if (step.getIndice() > 50)
                    passato = true;
                elemento.findElement(By.className("a-last")).click();
            }
        } catch (Exception e){
            step.setERRORE(e.getMessage());
            passato = false;
        }
        if(passato)
            extentTest.log(LogStatus.PASS, "Test passato tutti i risultati salvati","");
        else {
            extentTest.log(LogStatus.FAIL, "Test non passato fallito perché non riesce a salvare tutti i risultati","Errore: "+ step.getERRORE());
            fail();
        }
        Thread.sleep(3000);
    }

    @Test
    @Order(5)
    @DisplayName("Test tab")
    void Test_005_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test che apre i risultati delle ricerche in schede diverse","");
        step.ricercaQuery("Iphone");
        step.ricercaEnter();
        extentTest.log(LogStatus.INFO, "Screen prima di provare ad aprire le nuove schede",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));

        if(step.ricercaTab(extentTest))
            extentTest.log(LogStatus.PASS, "Test passato","");
        else{
            extentTest.log(LogStatus.FAIL, "Test fallito","");
            fail();
        }
        Thread.sleep(3000);
    }

    @Test
    @Order(6)
    @DisplayName("Ricerca filtri")
    void Test_006_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test che apre nav bar mettendo dei filtri alla ricerca","");

        driver.findElement(By.cssSelector("a[data-csa-c-slot-id = 'nav_cs_4']")).click();
        Thread.sleep(2000);

        for(int i = 1;i <= 29;i++){
            String elemento = prop.getProperty("xpath.left.filter.parte1")+i+prop.getProperty("xpath.left.filter.parte2");
            if(driver.findElement(By.xpath(elemento)).getText().equals("Videogiochi")){
                driver.findElement(By.xpath(elemento)).click();
            }
        }
        Thread.sleep(1000);
        extentTest.log(LogStatus.INFO, "Screen dopo la selezione dei filtri voluti",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        int totaleCercato = 0;
        int totale = 0;
        for(WebElement element: driver.findElements(By.className(prop.getProperty("class.li.ricerca")))){
            totaleCercato++;
            totale = new Integer(element.findElement(By.className(prop.getProperty("class.totale"))).getText().substring(1));
        }
        if(totaleCercato == totale)
            extentTest.log(LogStatus.PASS, "Test passato","");
        else{
            extentTest.log(LogStatus.FAIL, "Test fallito","");
            fail();
        }
        Thread.sleep(5000);
    }

    @ParameterizedTest(name = "cercato = {0}")
    @Order(7)
    @DisplayName("Nel carrello")
    @CsvSource({"Accendini,1","Borraccia,2","Penne,3"})
    void Test_007_Amazon(String qry,int inCarrello,TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test che dopo la ricerca inserisce un articolo nel carrello","");

        step.ricercaQuery(qry);
        step.ricercaEnter();
        float somma = step.selezioneProdotto();
        System.out.println(somma);
        step.setSommaCarrello(somma);
        Thread.sleep(2000);
        if(Integer.parseInt(driver.findElement(By.id("nav-cart-count")).getText()) == inCarrello)
            extentTest.log(LogStatus.PASS, "Test passato",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        else{
            extentTest.log(LogStatus.FAIL, "Test fallito",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
            fail();
        }
        //System.out.println(driver.findElement(By.className("sc-price")).getText());
        /*if(driver.findElement(By.className("sc-price")).getText().contains(step.getSommaCarrelloString()))
            extentTest.log(LogStatus.PASS, "Test passato",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        else{
            extentTest.log(LogStatus.FAIL, "Test fallito",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
            fail();
        }*/


        Thread.sleep(5000);
    }

    @Test
    @Order(9)
    @DisplayName("RemoveAll Cart")
    void Test_009_Amazon(TestInfo testInfo) throws InterruptedException{
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        extentTest.log(LogStatus.INFO, "Test che rimuove tutti gli elementi dal carrello","");

        //Thread.sleep(30000);
        driver.findElement(By.id("nav-cart")).click();
        int totale = driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).size();
        extentTest.log(LogStatus.INFO, "Carrello prima del removeAll: ",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));

        int risposta = step.removeAll(driver);
        if (totale == 0)
            extentTest.log(LogStatus.PASS, "Il carrello è già vuoto: ",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        else {
            if (risposta == 0)
                extentTest.log(LogStatus.PASS, "Img carrello vuoto: ", extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
            else {
                if (risposta == -1)
                    extentTest.log(LogStatus.FAIL, "ERRORE: " + step.getERRORE() + "\n carrello non vuoto: ", extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
                else {
                    extentTest.log(LogStatus.FAIL, "Img ERRORE carrello non vuoto\ncisono ancora: " + risposta + " prodotti", extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
                }
                fail();
            }
        }
    }


    @AfterEach
    void tearDown(){
        extentReports.endTest(extentTest);
    }

    @AfterAll
    static void tearDownAll(){
        ManagmentDriver.stopDriver();
        extentReports.flush();
    }
}
