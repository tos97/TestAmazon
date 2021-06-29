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
    private int totaleCercato = 0;
    private int totale = -1;

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

    public String getSommaCarrelloString() {
        return String.valueOf((sommaCarrello));
    }

    public void setSommaCarrello(float sommaCarrello) {
        this.sommaCarrello += sommaCarrello;
    }

    /**
     * Chiude il banner
     * @return responso dell'operazione
     */
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

    /**
     * Inizia il test aprendo amazon e massimizzando la pagina
     * @param mobile se è mobile apre solo amazon
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void start(boolean mobile) throws InterruptedException {
        driver.get(prop.getProperty("A.url"));
        closeBanner();
        if (!mobile)
            driver.manage().window().maximize();
        Thread.sleep(4000);
    }

    /**
     * inserisce una stringa nella barra delle ricerche
     * @param qry
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void ricercaQuery(String qry) throws InterruptedException {
        driver.findElement(By.id(prop.getProperty("id.search.bar"))).sendKeys(qry);
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
     * @param console serve per controllare se viene richesto da cucumbe (in quel caso è true)
     * @return ritorna una stringa pronta per il report per le offerte in evidenza
     */
    public String ritornaListaBestseller(boolean console){
        String risultato = "";
        for(WebElement element: driver.findElement(By.className("feed-carousel-shelf"))
                .findElements(By.className("feed-carousel-card"))){
            if (console){
                risultato += element.findElement(By.className("product-image")).getAttribute("alt") + "\n"
                        + element.findElement(By.className("product-image")).getAttribute("src") + "\n" + "\n";
            } else {
                risultato += element.findElement(By.className("product-image")).getAttribute("alt") + "<br>"
                        + element.findElement(By.className("product-image")).getAttribute("src") + "<br>" + "<br>";
            }
        }
        return risultato;
    }

    /**
     * @return ritorna una stringa pronta per il report per la tendina delle categorie
     */
    public String ritornaListaCategorie(){
        String risultato = "";
        for(WebElement element: driver.findElements(By.id(prop.getProperty("id.categorie")))){
            risultato += element.getText();
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

    /**
     * @param i serve per ricordare l'indice della ricerca
     */
    public void setIndice(int i){
        indice = i;
    }

    /**
     * @return ritorna l'indice
     */
    public int getIndice() {
        return indice;
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
                int j = 0;
                for (WebElement element : elements.findElement(By.className("a-spacing-medium")).
                        findElements(By.className("a-section"))) {
                    j++;
                }
                if (j > 4) {
                    risultato += i + "<br>" + elements.findElement(By.className("a-size-base-plus")).getText() + "<br>" +
                            elements.findElement(By.className("a-price-whole")).getText() + "<br>" +
                            elements.findElement(By.className("s-image")).getAttribute("src") + "<br>";
                    System.out.println(i + "\n" + elements.findElement(By.className("a-size-base-plus")).getText() + "\n" +
                            elements.findElement(By.className("a-price-whole")).getText() + "\n" +
                            elements.findElement(By.className("s-image")).getAttribute("src") + "\n");
                } else {
                    risultato += i + "<br>" + elements.findElement(By.className("a-size-base-plus")).getText() + "<br>" + "" + "<br>" +
                            elements.findElement(By.className("s-image")).getAttribute("src") + "<br>";
                    System.out.println(i + "\n" + elements.findElement(By.className("a-size-base-plus")).getText() + "\n" + "" + "\n" +
                            elements.findElement(By.className("s-image")).getAttribute("src") + "\n");
                }
                System.out.println(i);
                Thread.sleep(1000);
                i++;
            }
            setIndice(i);
            return risultato;
        } catch (Exception e){
            return risultato;
        }
    }

    /**
     * Apre risultati in altre schede
     * @param extentTest per fare screen dei report
     * @return per uscire dopo che ha aperto le pagine volute
     */
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

    /**
     * Apre risultati in altre schede senza report di selenium
     * @param tabs pagine da aprire
     * @return per uscire dopo che ha aperto le pagine volute
     */
    public boolean ricercaTab(int tabs){
        int i = 0;
        String handler = driver.getWindowHandle();
        String url = "";
        try {
            for (WebElement element : driver.findElement(By.className(prop.getProperty("class.risultati.ricerca")))
                    .findElements(By.cssSelector("div[data-component-type = 's-search-result']"))) {
                if (i > (tabs-1))
                    return true;
                url += element.findElement(By.className("a-text-normal")).getAttribute("href");
                driver.switchTo().newWindow(WindowType.TAB);
                Thread.sleep(1000);
                driver.get(url);
                Thread.sleep(1000);
                driver.switchTo().window(handler);
                i++;
                url = "";
            }
        } catch (Exception e){
            setERRORE(e.getMessage());
        }
        return false;
    }

    /**
     * clicca su un elemento della navbar
     * @param select per decidere il filtro della nav bar in alto
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void navBar(String select) throws InterruptedException {
        for (WebElement element: driver.findElements(By.className("nav-a"))) {
            if (element.getText().equals(select)) {
                element.click();
                Thread.sleep(2000);
                return;
            }
        }
    }

    /**
     * Esplora i filtri selezionandone uno (nello specifico videogiochi in novità)
     * @param extentTest serve per fare lo screen in questo punto
     * @throws InterruptedException per poter utilizzare gli sleep
     */
    public void nFiltri(ExtentTest extentTest) throws InterruptedException {
        for (int i = 1; i <= 29; i++) {
            String elemento = prop.getProperty("xpath.left.filter.parte1") + i + prop.getProperty("xpath.left.filter.parte2");
            if (driver.findElement(By.xpath(elemento)).getText().equals("Videogiochi"))
                driver.findElement(By.xpath(elemento)).click();
        }
        Thread.sleep(1000);
        extentTest.log(LogStatus.INFO, "Screen dopo la selezione dei filtri voluti", extentTest.addBase64ScreenShot(Utils.getScreenBase64Android()));

        for (WebElement element : driver.findElements(By.className(prop.getProperty("class.li.ricerca")))) {
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
        for (int i = 1; i <= 29; i++) {
            String elemento = prop.getProperty("xpath.left.filter.parte1") + i + prop.getProperty("xpath.left.filter.parte2");
            if (driver.findElement(By.xpath(elemento)).getText().equals(qry))
                driver.findElement(By.xpath(elemento)).click();
        }
        Thread.sleep(1000);
        Utils.getScreenshot();

        for (WebElement element : driver.findElements(By.className(prop.getProperty("class.li.ricerca")))) {
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

    /**
     * Prende le info dell'articolo da mettere nel carrello
     * @return ritorna il prezzo che senìrviva per la somma che da errore quindi non utilizzata
     * @throws InterruptedException per poter utilizzare gli sleep
     */
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
            } else {
                elements.findElement(By.className("a-size-base-plus")).click();
                Thread.sleep(2000);
                carrello();
                return somma;
            }
        }
        return somma;
    }

    /**
     * Rimuove tutto dal carrello
     * @return restituisce il numero di elementi nel carrello oppure -1 se errore
     */
    public int removeAll(){
        try {
            int totale = driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).size();
            for (int i = totale; i > 0; i--) {
                driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).get(i - 1).click();
                Thread.sleep(2000);
            }
            driver.navigate().refresh();
            Thread.sleep(2000);
            return driver.findElements(By.cssSelector("input[value = 'Rimuovi']")).size();
        } catch (Exception e){
            setERRORE(e.getMessage());
            return -1;
        }
    }
}
