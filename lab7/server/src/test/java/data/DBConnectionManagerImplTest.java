package data;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionManagerImplTest {

    final Properties config = new Properties();
    String url;
    String userName;
    String password;

    final DBConnectionManagerImpl dbConnectionManager = new DBConnectionManagerImpl();

    @Test
    void connect() {
        try {
            readConfigFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            dbConnectionManager.connect(url, userName, password);
            System.out.println("SUCCESSFUL connection");
        } catch (Exception e) {
            System.out.println("UNSUCCESSFUL connection");
            e.printStackTrace();
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            URL[] urls = ((URLClassLoader) cl).getURLs();

            for (URL url : urls) {
                System.out.println(url.getFile());
            }

            fail();
        }
    }

    private void readConfigFile() throws IOException {
        FileInputStream fis;
        fis = new FileInputStream("src/main/resources/config.properties");
        config.load(fis);
        url = config.getProperty("db.url");
        userName = config.getProperty("db.username");
        password = config.getProperty("db.password");
    }

    @Test
    void testConnect() {
    }
}