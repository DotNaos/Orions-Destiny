package Burst.Engine.Source.Core.util;

public class DebugMessage {
    private static final String seperator = "-";
    private static char leftChar = '#';
    private static char rightChar = '#';

    public static void printNotFound(String message) {
        leftChar = '?';
        DebugPrint(message);
        reset();
    }

    public static void printError(String message) {
        leftChar = '!';
        DebugPrint(message);
        reset();
    }

    public static void printWarning(String message) {
        leftChar = '~';
        DebugPrint(message);
        reset();
    }

    public static void printLoadSuccess(String message) {
        leftChar = '+';
        DebugPrint(message);
        reset();
    }

    public static void printLoadFail(String message) {
        leftChar = '\\';
        rightChar = '/';
        DebugPrint(message);
        reset();
    }

    public static void printHeader(String header)
    {
        int headerLength = 50;
        StringBuilder tempSeperator = new StringBuilder();
        System.out.println("\n\n\n" + header);

        tempSeperator.append(seperator.repeat(30 - tempSeperator.length()));
        System.out.println(tempSeperator);
    }

    public static void DebugPrint(String message)
    {
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
    }

    private static void reset()
    {
        leftChar = rightChar = '#';
    }
}
