package wbServerApp;

import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.regex.Pattern;

public class WbServerCmdValue {
    /** logger */
    private final static Logger logger = Logger.getLogger(WbServerCmdValue.class);

    private static final int USAGE_WIDTH = 100;
    private static final String IPv4_REGEX =
            "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    private static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);

    @Option(name = "-ip", aliases = { "--IP" }, required = true,
            usage = "input network IPV4 address (or \"localhost\") of the running device")
    private String serverIP;

    private boolean errorFree = false;

    public WbServerCmdValue(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        parser.setUsageWidth(USAGE_WIDTH);

        try {
            parser.parseArgument(args);

            if (!isValidAddr()) {
                throw new CmdLineException(parser,
                        "--Input address are not valid.");
            }

            errorFree = true;
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Parse command line arguments failed, exit program");
            parser.printUsage(System.err);
            System.err.println("#### USAGE: Correct format: java -jar WbServer.jar -ip <network_ipv4_address>");
            System.exit(1);
        }
    }

    /**
     * Returns whether the parameters could be parsed without an
     * error.
     *
     * @return true if no error occurred.
     */
    public boolean isErrorFree() {
        return errorFree;
    }

    private boolean isValidAddr() {
        if (serverIP.equals("localhost"))
            return true;

        if (!IPv4_PATTERN.matcher(serverIP).matches())
            return false;

        String[] parts = serverIP.split("\\.");

        // verify that each of the four subgroups of IPv4 address is legal
        try {
            for (String segment: parts) {
                // x.0.x.x is accepted but x.01.x.x is not
                if (Integer.parseInt(segment) > 255 ||
                        (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        } catch(NumberFormatException e) {
            return false;
        }

        return true;
    }

    // getters
    public String getServerIP() {
        if (serverIP == null)
            return "localhost";

        return serverIP;
    }
}
