package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {
    private static final Logger logger = LoggerFactory.getLogger(Logback.class);

    public static Logger getLogger(){
        return logger;
    }
}
