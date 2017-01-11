package net.atos.practica.initialization;

import java.net.URL;

import javax.annotation.PostConstruct;

import net.atos.practica.common.ApplicationContextProvider;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class Initializator implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static Logger log = org.apache.log4j.Logger.getLogger(Initializator.class);

    @Override
    public void initialize(ConfigurableApplicationContext arg0) {
        initializeLogger();
    }

    public void initializeLogger() {
        try {
        	org.apache.log4j.LogManager.shutdown();
            URL resource = this.getClass().getClassLoader().getResource("log4j.xml");
            String path = resource.getPath();
            DOMConfigurator.configure(path);
            log.info("test");
            log.debug("test");

        } catch (Exception e) {
            log.error("ERROR INICIALIZANDO LOGGER", e);
        }
    }

}
