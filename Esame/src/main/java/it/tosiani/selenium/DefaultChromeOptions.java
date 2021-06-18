package it.tosiani.selenium;

import org.openqa.selenium.chrome.ChromeOptions;

public class DefaultChromeOptions extends ChromeOptions{

    public DefaultChromeOptions(ChromeOptions customChromeOptions) {
        if (customChromeOptions == null) return;
        for (String key: customChromeOptions.getCapabilityNames()){
            Object value = customChromeOptions.getCapability(key);
            setCapability(key,value);
        }
    }

}
