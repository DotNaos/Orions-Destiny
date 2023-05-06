package Burst.Engine.Source.Core.util;

public class DebugMessage {
    public static void printNotFound(String message) {
        String seperator = "----";
        for (int i = 0; i < message.length(); i++) {
            seperator += "-";
        }
        seperator += "----";

        System.out.println(
                "\n" + seperator +
                        "****" + message +  "****"
                + seperator + "\n"
        );
    }

    public static void printError(String message) {
        String seperator = "----";
        for (int i = 0; i < message.length(); i++) {
            seperator += "-";
        }
        seperator += "----";

        System.out.println(
                "\n" + seperator +
                        "!!!!" + message +  "!!!!"
                + seperator + "\n"
        );
    }
}
