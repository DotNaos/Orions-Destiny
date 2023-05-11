package Burst.Engine.Source.Core.Util;

public class DebugMessage {
    private static final String seperator = "-";
    private static char leftChar = '#';
    private static char rightChar = '#';

    public static boolean noDebug = false;

    public static void notFound(String message) {
        leftChar = '?';
        DebugPrint(message);
    }

    public static void error(String message) {
        leftChar = '!';
        DebugPrint(message);
    }

    public static void printWarning(String message) {
        leftChar = '~';
        DebugPrint(message);
    }

    public static void loadSuccess(String message) {
        leftChar = '+';
        DebugPrint(message);
    }

    public static void loadFail(String message) {
        leftChar = '\\';
        rightChar = '/';
        DebugPrint(message);
    }

    public static void header(String header)
    {
        if (noDebug) return;
        int headerLength = 50;
        StringBuilder tempSeperator = new StringBuilder();
        System.out.println("\n\n\n" + header);

        tempSeperator.append(seperator.repeat(30 - tempSeperator.length()));
        System.out.println(tempSeperator);
    }

    public static void DebugPrint(String message)
    {
        if (noDebug) return;
        rightChar = leftChar == '#' ? '#' : leftChar;

        StringBuilder tempTopBottom = new StringBuilder();
        StringBuilder tempLeft = new StringBuilder();
        StringBuilder tempRight = new StringBuilder();

        tempTopBottom.append(seperator.repeat(Math.max(0, message.length() + 10)));
        tempLeft.append(Character.toString(leftChar).repeat(4));
        tempRight.append(Character.toString(rightChar).repeat(4));

        System.out.println(
                "\n\\" + tempTopBottom + "/" +
                "\n\\" + tempLeft + " " + message + " " + tempRight + "/" +
                "\n\\" + tempTopBottom + "/" +
                "\n"
        );
        reset();
    }

    private static void reset()
    {
        leftChar = rightChar = '#';
    }

    public static void info(String s) {
        leftChar = 'i';
        DebugPrint(s);
    }
}
