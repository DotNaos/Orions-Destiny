package Burst.Engine.Source.Core.UI;

import imgui.ImGui;
import imgui.flag.ImGuiCol;

import java.util.ArrayList;
import java.util.List;

public class DebugPanel {
    private final static int bufferSize = 2000;
    private static List<Runnable> debugPanels = new ArrayList<>();
    private static List<String> debugPanelNames = new ArrayList<>();
    private static float[] plotBuffer = new float[bufferSize];
    private static float[] plotBuffer2 = new float[bufferSize];
    private static float[] plotBuffer3 = new float[bufferSize];

    private final static int maxScale = 100;
    private final static int minScale = -100;

    public static void plotValue(String name, float v1) {
        plotValue(name, "", v1);
    }

    public static void plotValue(String name, String t1, float v1) {
        // If the panel already exists, update it
        Runnable function = () -> {
            ImGui.begin(name);
            //plot a sinus function
            ImGui.plotLines(name, plotBuffer, plotBuffer.length, 0, t1, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth());
            // update the values
            plotBuffer[0] = v1;
            for (int i = plotBuffer.length - 1; i > 0; i--) {
                plotBuffer[i] = plotBuffer[i - 1];
            }
            ImGui.end();
        };

        addOrUpdate(name, function);
    }

    public static void plotValue2(String name, float v1, float v2) {
        plotValue2(name, "", v1, "", v2);
    }

    public static void plotValue2(String name, String t1, float v1, String t2, float v2) {
        // If the panel already exists, update it
        Runnable function = () -> {
            ImGui.begin(name);
            //plot a sinus function
            ImGui.plotLines(name, plotBuffer, plotBuffer.length, 0, t1, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);

            // change the color of the plot
            ImGui.pushStyleColor(ImGuiCol.PlotLines, 0.0f, 1f, 0f, 1.0f);
            ImGui.plotLines(name, plotBuffer2, plotBuffer2.length, 0, t2, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.popStyleColor();
            // update the values
            plotBuffer[0] = v1;
            plotBuffer2[0] = v2;
            for (int i = plotBuffer.length - 1; i > 0; i--) {
                plotBuffer[i] = plotBuffer[i - 1];
                plotBuffer2[i] = plotBuffer2[i - 1];
            }
            ImGui.end();
        };

        addOrUpdate(name, function);
    }

    public static void plotValue3(String name, float v1, float v2, float v3) {
        plotValue3(name, "", v1, "", v2, "", v3);
    }

    public static void plotValue3(String name, String t1, float v1, String t2, float v2, String t3, float v3) {
        // If the panel already exists, update it
        Runnable function = () -> {
            ImGui.begin(name);
            //plot a sinus function
            ImGui.plotLines(name, plotBuffer, plotBuffer.length, 0, t1, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 3);

            // change the color of the plot
            ImGui.pushStyleColor(ImGuiCol.PlotLines, 0.0f, 1f, 0f, 1.0f);
            ImGui.plotLines(name, plotBuffer2, plotBuffer2.length, 0, t2, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 3);
            ImGui.popStyleColor();

            // change the color of the plot
            ImGui.pushStyleColor(ImGuiCol.PlotLines, 0.0f, 0f, 1f, 1.0f);
            ImGui.plotLines(name, plotBuffer3, plotBuffer3.length, 0, t3, -25, 25, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 3);
            ImGui.popStyleColor();

            // update the values
            plotBuffer[0] = v1;
            plotBuffer2[0] = v2;
            plotBuffer3[0] = v3;
            for (int i = plotBuffer.length - 1; i > 0; i--) {
                plotBuffer[i] = plotBuffer[i - 1];
                plotBuffer2[i] = plotBuffer2[i - 1];
                plotBuffer3[i] = plotBuffer3[i - 1];
            }
            ImGui.end();
        };

        addOrUpdate(name, function);
    }

    public static void imgui() {
        for (Runnable panel : debugPanels) {
            panel.run();
        }
    }

    public static <T> void displayValue(String name, T value) {
        // If the panel already exists, update it

        Runnable panel = () -> {
            ImGui.begin(name);
            //plot a simple line
            ImGui.plotLines(name, plotBuffer, plotBuffer.length, 0, "", minScale, maxScale, ImGui.getWindowWidth(), 100);
            plotBuffer[0] = (float) value;
            for (int i = 1; i < plotBuffer.length; i++) {
                plotBuffer[i] = plotBuffer[i - 1];
            }

            // update the values
            ImGui.end();
        };
        addOrUpdate(name, panel);
    }

    private static void addOrUpdate(String name, Runnable function) {
        int index = debugPanelNames.indexOf(name);
        if (index != -1) {
            debugPanels.set(index, function);
        } else {
            debugPanelNames.add(name);
            debugPanels.add(function);
        }
        Window.getImguiLayer().update(0, Window.getScene());
    }
}
