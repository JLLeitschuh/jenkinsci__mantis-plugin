package hudson.plugins.mantis;

import hudson.Util;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Utility class.
 *
 * @author Seiji Sogabe
 */
public final class Utility {

    private final static char[] REGEXP_CHARS = new char[] {
        '\\', '[', ']', '(', ')', '{', '}', '^', '$', '|', '?', '*', '+', '-', ':', ',', '.', '&'
    };

    static {
        Arrays.sort(REGEXP_CHARS);
    }

    private Utility() {
        //
    }

    public static String escape(final String str) {
        if (str == null) {
            return null;
        }

        final int len = str.length();
        final StringBuffer buf = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            final char c = str.charAt(i);

            switch (c) {
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                case '&':
                    if ((i < len - 1) && (str.charAt(i + 1) == '#')) {
                        buf.append(c);
                    } else {
                        buf.append("&amp;");
                    }
                    break;
                case '"':
                    buf.append("&quot;");
                    break;
                case '\'':
                    buf.append("&#039;");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }

        return buf.toString();
    }

    public static String join(final int[] values, final String separator) {
        boolean first = true;
        final StringBuffer sb = new StringBuffer();
        for (final int value : values) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(String.valueOf(value));
        }
        return sb.toString();
    }

    public static int[] tokenize(final String str, final String delimiter) {
        if (str == null || delimiter == null) {
            return new int[0];
        }
        final String[] s = Util.tokenize(str, delimiter);
        final int[] values = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            values[i] = Integer.valueOf(s[i]);
        }
        return values;
    }

    public static void log(final PrintStream logger, final String message) {
        final StringBuffer buf = new StringBuffer();
        buf.append("[MANTIS] ").append(message);
        logger.println(buf.toString());
    }

    public static final String escapeRegExp(final String str) {
        if (str == null) {
            return null;
        }

        final StringBuffer buf = new StringBuffer();
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            final char c = str.charAt(i);
            if (Arrays.binarySearch(REGEXP_CHARS, c) >= 0) {
                buf.append("\\");
            }
            buf.append(c);
        }

        return buf.toString();
    }

    public static boolean isValidPort(final String port) {
        if (port == null || port.length() == 0) {
            return false;
        }
        int portNumber;
        try {
            portNumber = Integer.parseInt(port);
        } catch (final NumberFormatException nfe) {
            return false;
        }
        if (portNumber < 0 || 65535 < portNumber) {
            return false;
        }
        return true;
    }

}
