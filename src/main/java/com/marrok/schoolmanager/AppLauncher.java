package com.marrok.schoolmanager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AppLauncher {
    private static final Logger logger = LogManager.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info("Here we go");

        var app = new Main();
        app.run();
    }
}
