package it.tosiani.Utility;

import it.tosiani.drivers.ManagmentDriver;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static it.tosiani.Utility.GlobalParameters.*;

public class Utils {

    /**
     * Serve per poter leggere dai file .properties
     * @param propNome nome del file da cui leggere
     * @return
     */
    public static Properties loadProp(String propNome){
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream(PROPERTIES_PATH + File.separator + propNome + EXT_PROP));
        } catch (IOException e){
            e.printStackTrace();
            //getScreenshot();
        }
        return properties;
    }

    /**
     * Fa gli screen per errori creati in questa pagina
     */
    public static void getScreenshot(){
        try {
            SimpleDateFormat dta = new SimpleDateFormat("yyyyMMddHHmmss");
            String sDate = dta.format(new Date());
            byte[] immagine = ((TakesScreenshot)ManagmentDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(SCREENSHOT_PATH+sDate+".png"),immagine);
        } catch (IOException e) {
            e.printStackTrace();
            getScreenshot();
        }
    }

    /**
     * fa screen di quello richiesto e li salva nell'apposita cartella
     * @return ritorna del base64 dello screen da poter allegare in report
     */
    public static String getScreenBase64Android(){
        String img = null;
        Base64 base64 = new Base64();
        try {
            SimpleDateFormat dta = new SimpleDateFormat("yyyyMMddHHmmss");
            String sDate = dta.format(new Date());
            byte[] immagine = ((TakesScreenshot) ManagmentDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(SCREENSHOT_PATH+sDate+".png"),immagine);
            img = new String(base64.encodeBase64String(immagine));
        } catch (Exception e) {
            e.printStackTrace();
            getScreenshot();
        }
        return "data:image/png;base64," + img;
    }
}
