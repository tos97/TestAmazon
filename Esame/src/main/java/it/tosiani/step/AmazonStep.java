package it.tosiani.step;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import it.tosiani.Utility.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.Properties;

public class AmazonStep {

    private WebElement webElement = null;
    private ChromeDriver driver = null;
    private Properties prop = null;
    private String ERRORE = "";
    private int indice = 0;
    private float sommaCarrello = 0;

    public AmazonStep(ChromeDriver driver, Properties prop) {
        this.driver = driver;
        this.prop = prop;
    }

    public String getERRORE() {
        return ERRORE;
    }

    public void setERRORE(String ERRORE) {
        this.ERRORE = ERRORE;
    }

    public String getSommaCarrelloString() {
        return String.valueOf((sommaCarrello));
    }

    public void setSommaCarrello(float sommaCarrello) {
        this.sommaCarrello += sommaCarrello;
    }

    public boolean closeBanner(){
        try {
            Thread.sleep(3000);
            if (driver.findElement(By.id(prop.getProperty("id.banner"))).isDisplayed()) {
                driver.findElement(By.id(prop.getProperty("id.btn.banner"))).click();
                System.out.println("Banner Trovato e chiuso");
                return true;
            }
        }catch (NoSuchElementException | InterruptedException e) {
            setERRORE(e.getMessage());
        }
        return false;
    }

    public void start() throws InterruptedException {
        driver.get(prop.getProperty("A.url"));
        closeBanner();
        driver.manage().window().maximize();
        Thread.sleep(4000);
    }

    public void ricercaQuery(String qry) throws InterruptedException {
        driver.findElement(By.id(prop.getProperty("id.search.bar"))).sendKeys(qry);
        Thread.sleep(2000);
    }

    public String ricercaEnter() throws InterruptedException {
        WebElement webElement = driver.findElement(By.id(prop.getProperty("id.search.suggerimento")));
        String elementClick = webElement.getText();
        webElement.click();
        Thread.sleep(2000);
        return elementClick;
    }

    public String ritornaListaBestseller(){
        String risultato = "";
        for(WebElement element: driver.findElement(By.className("feed-carousel-shelf"))
                .findElements(By.className("feed-carousel-card"))){
            risultato += element.findElement(By.className("product-image")).getAttribute("alt")+"<br>"
                    +element.findElement(By.className("product-image")).getAttribute("src")+"<br>"+"<br>";
        }
        return risultato;
    }

    public String ritornaListaCategorie(){
        String risultato = "";
        for(WebElement element: driver.findElements(By.id(prop.getProperty("id.categorie")))){
            risultato += element.getText()+"<br>";
        }
        return risultato;
    }

    /*public ArrayList<ElementoRicercato> caricaTrovati(WebElement elemento) throws InterruptedException {
        ArrayList<ElementoRicercato> tmp = new ArrayList<>();

        int i = 1;
        for(WebElement elements: elemento.findElements(By.cssSelector("div[data-component-type = 's-search-result']"))){
            int j = 0;
            for(WebElement element: elements.findElement(By.className("a-spacing-medium")).
                    findElements(By.className("a-section"))){
                j++;
            }
            if(j > 4) {
                tmp.add(new ElementoRicercato(elements.findElement(By.className("a-size-base-plus")).getText(),
                        elements.findElement(By.className("a-price-whole")).getText(),
                        elements.findElement(By.className("s-image")).getAttribute("src"), i));
            } else {
                tmp.add(new ElementoRicercato(elements.findElement(By.className("a-size-base-plus")).getText(), "",
                        elements.findElement(By.className("s-image")).getAttribute("src"), i));
            }
            Thread.sleep(400);
            i++;
        }

        return tmp;
    }*/

    public void setIndice(int i){
        indice = i;
    }

    public int getIndice() {
        return indice;
    }

    public String stampaLista(WebElement elemento) throws InterruptedException {
        String risultato = "";
        int i = 1;
        for(WebElement elements: elemento.findElements(By.cssSelector("div[data-component-type = 's-search-result']"))){
            int j = 0;
            for(WebElement element: elements.findElement(By.className("a-spacing-medium")).
                    findElements(By.className("a-section"))){
                j++;
            }
            if(j > 4) {
                risultato += i+"<br>"+elements.findElement(By.className("a-size-base-plus")).getText()+"<br>"+
                        elements.findElement(By.className("a-price-whole")).getText()+"<br>"+
                        elements.findElement(By.className("s-image")).getAttribute("src")+"<br>";
            } else {
                risultato += i+"<br>"+elements.findElement(By.className("a-size-base-plus")).getText()+"<br>"+""+"<br>"+
                        elements.findElement(By.className("s-image")).getAttribute("src")+"<br>";
            }
            System.out.println(i);
            Thread.sleep(1000);
            i++;
        }
        setIndice(i);
        return risultato;
    }

    public boolean ricercaTab(ExtentTest extentTest){
        int i = 0;
        String handler = driver.getWindowHandle();
        String url = "";
        try {
            for (WebElement element : driver.findElement(By.className(prop.getProperty("class.risultati.ricerca")))
                    .findElements(By.cssSelector("div[data-component-type = 's-search-result']"))) {
                if (i > 2)
                    return true;
                url += element.findElement(By.className("a-text-normal")).getAttribute("href");
                driver.switchTo().newWindow(WindowType.TAB);
                Thread.sleep(1000);
                driver.get(url);
                Thread.sleep(1000);
                extentTest.log(LogStatus.INFO, "Screen nuova pagina",extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
                driver.switchTo().window(handler);
                i++;
                url = "";
            }
        } catch (Exception e){
            setERRORE(e.getMessage());
        }
        return false;
    }

    public void carrello() throws InterruptedException {
        driver.findElement(By.id(prop.getProperty("id.btn.add.cart"))).click();
        Thread.sleep(2000);
        try {
            if (driver.findElement(By.xpath(prop.getProperty("xpath.no.btn"))).isDisplayed())
                driver.findElement(By.xpath(prop.getProperty("xpath.no.btn"))).click();
            Thread.sleep(3000);
            if(driver.findElement(ByChained.cssSelector("input[aria-labelledby = 'attach-sidesheet-view-cart-button-announce']")).isDisplayed())
                driver.findElement(ByChained.cssSelector("input[aria-labelledby = 'attach-sidesheet-view-cart-button-announce']")).click();
        } catch (NoSuchElementException e){
            Thread.sleep(2000);
            try{
                if(driver.findElement(ByChained.cssSelector("input[aria-labelledby = 'attach-sidesheet-view-cart-button-announce']")).isDisplayed())
                    driver.findElement(ByChained.cssSelector("input[aria-labelledby = 'attach-sidesheet-view-cart-button-announce']")).click();
            } catch (NoSuchElementException ex) {
                driver.findElement(By.id(prop.getProperty("id.btn.cart.no.banner"))).click();
                setERRORE(e.getMessage());
            }
        }
        Thread.sleep(2000);
    }

    public float selezioneProdotto() throws InterruptedException {
        float somma = 0;
        for(WebElement elements: driver.findElement(By.className(prop.getProperty("class.risultati.ricerca")))
                .findElements(By.cssSelector("div[data-component-type = 's-search-result']"))) {
            int j = 0;
            for (WebElement element : elements.findElement(By.className("a-spacing-medium")).
                    findElements(By.className("a-section"))) {
                j++;
            }
            if (j >= 4) {
                String sum = elements.findElement(By.className("a-price-whole")).getText();
                sum = sum.replace(",",".");
                somma += Float.parseFloat(sum);
                elements.findElement(By.className("a-size-base-plus")).click();
                Thread.sleep(2000);
                carrello();
                return somma;
            }
        }
        return somma;
    }

    public int removeAll(WebDriver driver) throws InterruptedException {
        try {
            int totale = driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).size();
            for (int i = totale; i > 0; i--) {
                driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).get(i - 1).click();
            }
            Thread.sleep(2000);
            driver.navigate().refresh();
            Thread.sleep(2000);
            return driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).size();
        } catch (Exception e){
            setERRORE(e.getMessage());
            return -1;
        }
    }
}
