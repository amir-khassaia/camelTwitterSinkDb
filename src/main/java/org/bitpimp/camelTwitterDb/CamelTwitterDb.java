package org.bitpimp.camelTwitterDb;

import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class CamelTwitterDb {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new MyRouteBuilder());
        main.run(args);
    }
}

