package it.tosiani.Utility;

import java.io.File;

public class GlobalParameters {

    public static final String PACKAGE				 					= "com.academy";

    // CONTEXT
    public static final String NATIVE_APP								= "NATIVE_APP";
    public static final String WEBVIEW 									= "WEBVIEW";

    // EXT SYSTEM
    public static final String EXT_JPG 									= ".jpg";
    public static final String EXT_PNG 									= ".png";
    public static final String EXT_HTML 								= ".html";
    public static final String EXT_EXE 									= ".exe";
    public static final String EXT_ANDROID 								= ".apk";
    public static final String EXT_IOS 									= ".ipa";
    public static final String EXT_SIMULATOR_IOS 						= ".app";
    public static final String EXT_XLSX 								= ".xlsx";
    public static final String EXT_XML 									= ".xml";
    public static final String EXT_JAVA 								= ".java";
    public static final String EXT_PROP 								= ".properties";
    public static final String EXT_ENV 									= ".env";
    public static final String EXT_JSON 								= ".json";
    public static final String EXT_CSV									= ".csv";
    public static final String EXT_SH									= ".sh";

    public static final String HTTP		 								= "http";
    public static final String HTTPS 									= "https";

    public static final String USER_DIR 								= System.getProperty("user.dir");
    public static final String SRC_DIR 									= USER_DIR + File.separator + "src";
    public static final String MAIN_DIR 								= SRC_DIR + File.separator + "main";
    public static final String ROOT_PATH_TEST 							= SRC_DIR + File.separator + "test" + File.separator + "java";
    public static final String BASE_PATH_TEST							= SRC_DIR + File.separator + "test" + File.separator + "java" + File.separator + PACKAGE.replace('.', File.separatorChar) + File.separator;

    public static final String SCREENSHOT_PATH			 				= USER_DIR + File.separator + "screenshot" + File.separator;
    public static final String RESOURCES_PATH 							= MAIN_DIR + File.separator + "resources";
    public static final String BASE_DRIVER_PATH 						= USER_DIR + File.separator + "driver" + File.separator;
    public static final String PROPERTIES_PATH 							= RESOURCES_PATH + File.separator + "properties";
    public static final String BASE_PATH_APP 							= RESOURCES_PATH + File.separator + "apps";
    public static final String CONFIG_PATH 								= PROPERTIES_PATH + File.separator + "config";

    public static final String PROPERTIES_ACCELERATOR_PATH				= CONFIG_PATH + File.separator + "accelerator";
    public static final String PROPERTIES_LOG4J_PATH 					= CONFIG_PATH + File.separator + "log4j";


    // SO
    public static final String MY_SO 									= System.getProperty("os.name");
    public static final String WINDOWS_SO 								= "Windows";
    public static final String MAC_SO 									= "Mac OS X";
    public static final String LINUX_SO 								= "Linux";

    // APPIUM
    public static final String SERVER_APPIUM                            = "http://127.0.0.1:4723/wd/hub";

    // CHROME DRIVER
    public static final String BASE_CHROME_PATH							= BASE_DRIVER_PATH + "chrome" + File.separator;
    public static final String CHROME_DRIVER_PATH_WIN 					= BASE_CHROME_PATH + "Windows" + File.separator + "chromedriver" + EXT_EXE;
    public static final String CHROME_DRIVER_PATH 						= BASE_CHROME_PATH + MY_SO + File.separator + "chromedriver";

    // FIREFOX DRIVER
    public static final String BASE_FIREFOX_PATH						= BASE_DRIVER_PATH + "firefox" + File.separator;
    public static final String FIREFOX_DRIVER_PATH_WIN 					= BASE_FIREFOX_PATH + "Windows" + File.separator + "geckodriver" + EXT_EXE;
    public static final String FIREFOX_DRIVER_PATH 						= BASE_FIREFOX_PATH + MY_SO + File.separator + "geckodriver";

    // INTERNET EXPLORER DRIVER
    public static final String IEXPLORER_DRIVER_PATH				 	= BASE_DRIVER_PATH + "iexplorer" + File.separator + "IEDriverServer" + EXT_EXE;

    // REPORT
    public static final String REPORT_PATH 			 					= USER_DIR + File.separator + "report";
    public static final String SCREENSHOT2_PATH                         = "screenshot" + File.separator;
    public static final String REPORT_CONFIG_XML 	 					= USER_DIR + File.separator + "report_config" + EXT_XML;

    //LOG
    public static final String LOG_PATH 			 					= USER_DIR + File.separator + "logs";

}
