package it.tosiani.step;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import it.tosiani.Utility.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Properties;

public class MobileStep {
    private WebElement webElement = null;
    private ChromeDriver driver = null;
    private Properties prop = null;
    private String ERRORE = "";
    private int indice = 0;
    private float sommaCarrello = 0;
    private int totaleCercato = 0;
    private int totale = -1;

    public MobileStep(ChromeDriver driver, Properties prop) {
        this.driver = driver;
        this.prop = prop;
    }

    public String getERRORE() {
        return ERRORE;
    }

    public void setERRORE(String ERRORE) {
        this.ERRORE = ERRORE;
    }

    public void setIndice(int i){
        indice = i;
    }

    public int getIndice() {
        return indice;
    }

    public int getTotaleCercato() {
        return totaleCercato;
    }

    public void setTotaleCercato(int totaleCercato) {
        this.totaleCercato = totaleCercato;
    }

    public int getTotale() {
        return totale;
    }

    public void setTotale(int totale) {
        this.totale = totale;
    }

    /**
     * @param console serve per controllare se viene richesto da cucumbe (in quel caso è true)
     * @return ritorna una stringa pronta per il report per le offerte in evidenza
     */
    public String ritornaListaBestseller(boolean console) throws InterruptedException {
        String risultato = "";
        for(WebElement element: driver.findElements(By.className("gwm-Card--withPadding"))){
            Thread.sleep(1000);
            for(WebElement elements: element.findElements(By.className("gwm-MultiAsinCard-row"))) {
                Thread.sleep(1000);
                if (console){
                    risultato += elements.findElement(By.className("gwm-MultiAsinCard-productTitle")).getText() + "\n"
                            + elements.findElement(By.className("gwm-MultiAsinCard-productPrice")).getText() + "\n"
                            + elements.findElement(By.className("a-dynamic-image")).getAttribute("src") + "\n" + "\n";
                } else {
                    risultato += elements.findElement(By.className("gwm-MultiAsinCard-productTitle")).getText() + "<br>"
                            + elements.findElement(By.className("gwm-MultiAsinCard-productPrice")).getText() + "<br>"
                            + elements.findElement(By.className("a-dynamic-image")).getAttribute("src") + "<br>" + "<br>";
                }
            }
            if (risultato.length() != 0)
                return risultato;
        }
        return risultato;
    }

    /**
     * @param console serve per controllare se viene richesto da cucumbe (in quel caso è true)
     * @return ritorna una stringa pronta per il report per le categorie
     */
    public String ritornaListaCategorie(boolean console) throws InterruptedException {
        String risultato = "";
        driver.findElement(By.id(prop.getProperty("id.side.menu"))).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("a[data-menu-id = '2']")).click();
        Thread.sleep(2000);
        for(WebElement element: driver.findElement(By.cssSelector("ul[data-menu-id = '2']"))
                .findElements(By.className("hmenu-item"))){
            if (console) {
                risultato += element.getText() + "\n";
            } else {
                risultato += element.getText() + "<br>";
            }
        }
        return risultato;
    }

    /**
     * inserisce una stringa nella barra delle ricerche
     * @param qry
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void ricercaQuery(String qry) throws InterruptedException {
        driver.findElement(By.id(prop.getProperty("id.input.search"))).sendKeys(qry);
        Thread.sleep(2000);
    }

    /**
     * Preme invio dopo aver premuto su un risultato suggerito per la ricerca
     * @return la stringa che contiene la stringa suggerita che verrà cercata
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public String ricercaEnter() throws InterruptedException {
        WebElement webElement = driver.findElement(By.id(prop.getProperty("id.search.suggerimento")));
        String elementClick = webElement.getText();
        webElement.click();
        Thread.sleep(2000);
        return elementClick;
    }

    /**
     * Cerca gli elementi fuoriusciti da una ricerca
     * @param elemento un path che serviva
     * @return ritorna una stringa pronta per essere inserita nel report
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public String stampaLista(WebElement elemento) throws InterruptedException {
        String risultato = "";
        try {
            int i = 1;
            for (WebElement elements : elemento.findElements(By.cssSelector("div[data-component-type = 's-search-result']"))) {

                risultato += i + "<br>" + elements.findElement(By.className("a-size-small")).getText() + "<br>" +
                        elements.findElement(By.className("a-price-whole")).getText() + "<br>" +
                        elements.findElement(By.className("s-image")).getAttribute("src") + "<br>";
                System.out.println(i + "\n" + elements.findElement(By.className("a-size-small")).getText() + "\n" +
                        elements.findElement(By.className("a-price-whole")).getText() + "\n" +
                        elements.findElement(By.className("s-image")).getAttribute("src") + "\n");

                System.out.println(i);
                Thread.sleep(3000);
                //i++;
            }
            setIndice(i);
            return risultato;
        } catch (Exception e){
            return risultato;
        }
    }

    /**
     * Esplora i filtri selezionandone uno (nello specifico videogiochi in novità)
     * @param extentTest serve per fare lo screen in questo punto
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void nFiltri(ExtentTest extentTest) throws InterruptedException {
        for (WebElement element: driver.findElement(By.id("zg-mobile-browseRoot"))
                .findElements(By.className("a-link-normal"))){
            if (element.getText().equals("Videogiochi"))
                element.click();
        }
        Thread.sleep(1000);
        extentTest.log(LogStatus.INFO, "Screen dopo la selezione dei filtri voluti", extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));
        for (WebElement element : driver.findElements(By.cssSelector("li[data-fling-container='true']"))) {
            totaleCercato++;
            totale = new Integer(element.findElement(By.className(prop.getProperty("class.totale"))).getText().substring(1));
        }
        setTotale(totale);
        setTotaleCercato(totaleCercato);
    }

    /**
     * Esplora i filtri selezionandone uno (nello specifico videogiochi in novità) senza report di selenium, fatto per cucumber
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void nFiltri(String qry) throws InterruptedException {
        for (WebElement element: driver.findElement(By.id("zg-mobile-browseRoot"))
                .findElements(By.className("a-link-normal"))){
            if (element.getText().equals(qry))
                element.click();
        }
        Thread.sleep(1000);
        Utils.getScreenshot();
        for (WebElement element : driver.findElements(By.cssSelector("li[data-fling-container='true']"))) {
            totaleCercato++;
            totale = new Integer(element.findElement(By.className(prop.getProperty("class.totale"))).getText().substring(1));
        }
        setTotale(totale);
        setTotaleCercato(totaleCercato);
    }

    /**
     * Aggiunge l'articolo al carrello
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void selezioneProdotto() throws InterruptedException {
        for (WebElement elements : driver.findElement(By.className(prop.getProperty("class.risultati.ricerca")))
                .findElements(By.cssSelector("div[data-component-type = 's-search-result']"))) {
            elements.click();
            Thread.sleep(2000);
            driver.findElement(By.id(prop.getProperty("id.btn.add.cart"))).click();
            return;
        }
    }

    /**
     * Rimuove tutto dal carrello
     * @return restituisce il numero di elementi nel carrello oppure -1 se errore
     */
    public int removeAll(){
        try {
            int totale = driver.findElements(By.cssSelector("input[data-action = 'delete']")).size();
            for (int i = totale; i > 0; i--) {
                driver.findElements(By.cssSelector("input[data-action = 'delete']")).get(i - 1).click();
                Thread.sleep(2000);
            }
            driver.navigate().refresh();
            Thread.sleep(2000);
            return driver.findElements(By.cssSelector("input[data-action = 'delete']")).size();
        } catch (Exception e){
            setERRORE(e.getMessage());
            return -1;
        }
    }
}