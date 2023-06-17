package Burst.Engine.Source.Core.Util;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * @author Oliver Schuetz
 */
public class DebugMessage {
  private static final String seperator = "-";
  public static boolean noDebug = false;
  private static char leftChar = '#';
  private static char rightChar = '#';

  public static void notFound(String message) {
    leftChar = '?';
    DebugPrint(message, true);
  }

  public static void error(String message) {
    leftChar = '!';
    DebugPrint(message, true);
  }

  public static void printWarning(String message) {
    leftChar = '~';
    DebugPrint(message, true);
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

  public static void header(String header) {
    if (noDebug) return;
    int headerLength = 50;
    StringBuilder tempSeperator = new StringBuilder();
    System.out.println("\n\n\n" + header);

    tempSeperator.append(seperator.repeat(30 - tempSeperator.length()));
    System.out.println(tempSeperator);
  }

  private static void DebugPrint(String message) {
    DebugPrint(message, false);
  }

  private static void DebugPrint(String message, boolean isErrorMessage) {
    if (noDebug) return;
    rightChar = leftChar == '#' ? '#' : leftChar;

    StringBuilder tempTopBottom = new StringBuilder();
    StringBuilder tempLeft = new StringBuilder();
    StringBuilder tempRight = new StringBuilder();

    tempTopBottom.append(seperator.repeat(Math.max(0, message.length() + 10)));
    tempLeft.append(Character.toString(leftChar).repeat(4));
    tempRight.append(Character.toString(rightChar).repeat(4));

    if (isErrorMessage)
      System.err.println("\n\\" + tempTopBottom + "/" + "\n\\" + tempLeft + " " + message + " " + tempRight + "/" + "\n\\" + tempTopBottom + "/" + "\n");
    else
      System.out.println("\n\\" + tempTopBottom + "/" + "\n\\" + tempLeft + " " + message + " " + tempRight + "/" + "\n\\" + tempTopBottom + "/" + "\n");

    reset();
  }

  private static void reset() {
    leftChar = rightChar = '#';
  }

  public static void info(String s) {
    leftChar = 'i';
    DebugPrint(s);
  }

  // Matrix Debug

  // 2D Matrix
  public static void printMatrix(float[][] matrix) {
    if (noDebug) return;
    System.out.println("\n");
    for (int i = 0; i < matrix.length; i++) {
      System.out.print("| ");
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
        if ((j + 1) % 4 == 0) System.out.print("| ");
      }
      System.out.println();
    }
    System.out.println("\n");
  }

  // 3D Matrix
  public static void printMatrix(float[][][] matrix) {
    if (noDebug) return;
    System.out.println("\n");
    for (int i = 0; i < matrix.length; i++) {
      System.out.println("Matrix " + i);
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print("| ");
        for (int k = 0; k < matrix[i][j].length; k++) {
          System.out.print(matrix[i][j][k] + " ");
          if ((k + 1) % 4 == 0) System.out.print("| ");
        }
        System.out.println();
      }
      System.out.println();
    }
    System.out.println("\n");
  }

  // 2x2 Matrix (JOML)
  public static void printMatrix(Matrix2f matrix) {
    if (noDebug) return;
    System.out.println("\n");
    for (int i = 0; i < 2; i++) {
      System.out.print("| ");
      for (int j = 0; j < 2; j++) {
        System.out.print(matrix.get(i, j) + " ");
        if ((j + 1) % 4 == 0) System.out.print("| ");
      }
      System.out.println();
    }
    System.out.println("\n");
  }

  // 3x3 Matrix (JOML)
  public static void printMatrix(Matrix3f matrix) {
    if (noDebug) return;
    System.out.println("\n");
    for (int i = 0; i < 3; i++) {
      System.out.print("| ");
      for (int j = 0; j < 3; j++) {
        System.out.print(matrix.get(i, j) + " ");
        if ((j + 1) % 4 == 0) System.out.print("| ");
      }
      System.out.println();
    }
    System.out.println("\n");
  }


  // 4x4 Matrix (JOML)
  public static void printMatrix(Matrix4f matrix) {
    if (noDebug) return;
    System.out.println("\n");
    for (int i = 0; i < 4; i++) {
      System.out.print("| ");
      for (int j = 0; j < 4; j++) {
        System.out.print("(" + matrix.get(i, j) + ") ");
        if ((j + 1) % 4 == 0) System.out.print("| ");
      }
      System.out.println();
    }
    System.out.println("\n");
  }

  // Matrices with names
  public static void printMatrix(float[][] matrix, String name) {
    if (noDebug) return;
    header(name);
    printMatrix(matrix);
  }

  public static void printMatrix(float[][][] matrix, String name) {
    if (noDebug) return;
    header(name);
    printMatrix(matrix);
  }

  public static void printMatrix(Matrix2f matrix, String name) {
    if (noDebug) return;
    header(name);
    printMatrix(matrix);
  }

  public static void printMatrix(Matrix3f matrix, String name) {
    if (noDebug) return;
    header(name);
    printMatrix(matrix);
  }

  public static void printMatrix(Matrix4f matrix, String name) {
    if (noDebug) return;
    header(name);
    printMatrix(matrix);
  }

}
