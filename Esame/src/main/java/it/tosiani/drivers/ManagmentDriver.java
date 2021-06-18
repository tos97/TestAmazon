package it.tosiani.drivers;

import it.tosiani.Utility.Utils;
import it.tosiani.selenium.DefaultChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static it.tosiani.Utility.GlobalParameters.*;

public class ManagmentDriver {
    private static ChromeDriver driver = null;

    /**
     * Start Chrome driver
     * @param defaultChromeOptions opzioni di funzionamento del driver dalla classe personalizzata
     */
    public static void startDriver(DefaultChromeOptions defaultChromeOptions){
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_WIN);
        System.setProperty("org.freemarker.loggerLibrary","none");
        driver = new ChromeDriver(defaultChromeOptions);
        System.err.close();
        System.setErr(System.err);
        Utils.loadProp("log4j");
    }

    /**
     * @return serve per restituire il driver creato in questa classe
     */
    public static ChromeDriver getDriver(){
        return driver;
    }

    /**
     * Termina l'esecuzione del driver
     */
    public static void stopDriver(){
            driver.quit();
    }
}
