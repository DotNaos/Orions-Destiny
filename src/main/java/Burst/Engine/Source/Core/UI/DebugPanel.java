package Burst.Engine.Source.Core.UI;

import imgui.ImGui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebugPanel {
    private final static int bufferSize = 2000;
    private final static int maxScale = 100;
    private final static int minScale = -100;
    private static List<Runnable> debugPanels = new ArrayList<>();
    private static List<String> debugPanelNames = new ArrayList<>();
    private static Map<String, List<PlotBuffer>> plotBuffers = new HashMap<>();

    private static void addOrUpdatePanel(String name, Runnable function) {
        int index = debugPanelNames.indexOf(name);
        if (index != -1) {
            // If the panel already exists, update it
            debugPanels.set(index, function);
        } else {
            // If the panel doesn't exist, add it
            debugPanelNames.add(name);
            debugPanels.add(function);
        }
        Window.getImguiLayer().update(0, Window.getScene());
    }

    private static void addOrUpdatePlot(String name, float[] values) {
        int bufferCount = values.length;
        if (plotBuffers.containsKey(name)) {
            // If it has a buffer, update it
            for (int i = 0; i < bufferCount; i++) {
                plotBuffers.get(name).get(i).update(values[i]);
            }
        } else {
            // If it has no buffer, add one
            plotBuffers.put(name, new ArrayList<>());

            for (int i = 0; i < bufferCount; i++) {
                plotBuffers.get(name).add(new PlotBuffer(bufferSize));
            }
        }


    }

    public static void plotValue(String name, float v1) {
        plotValue(name, "", v1);
    }

    public static void plotValue(String name, String t1, float v1) {
        // If the panel already exists, update it
        Runnable function = () -> {
            ImGui.begin(name);
            addOrUpdatePlot(name, new float[]{v1});
            ImGui.plotLines(name, plotBuffers.get(name).get(0).getBuffer(), plotBuffers.get(name).get(0).getSize(), 0, t1, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.end();
        };

        addOrUpdatePanel(name, function);
    }

    public static void plotValue2(String name, float v1, float v2) {
        plotValue2(name, "", v1, "", v2);
    }

    public static void plotValue2(String name, String t1, float v1, String t2, float v2) {
        // If the panel already exists, update it
        Runnable function = () -> {
            ImGui.begin(name);
            addOrUpdatePlot(name, new float[]{v1, v2});
            ImGui.plotLines(name, plotBuffers.get(name).get(0).getBuffer(), plotBuffers.get(name).get(0).getSize(), 0, t1, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.plotLines(name, plotBuffers.get(name).get(1).getBuffer(), plotBuffers.get(name).get(1).getSize(), 0, t2, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.end();
        };

        addOrUpdatePanel(name, function);
    }

    public static void plotValue3(String name, float v1, float v2, float v3) {
        plotValue3(name, "", v1, "", v2, "", v3);
    }

    public static void plotValue3(String name, String t1, float v1, String t2, float v2, String t3, float v3) {
        // If the panel already exists, update it
        Runnable function = () -> {
            ImGui.begin(name);
            addOrUpdatePlot(name, new float[]{v1, v2, v3});
            ImGui.plotLines(name, plotBuffers.get(name).get(0).getBuffer(), plotBuffers.get(name).get(0).getSize(), 0, t1, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.plotLines(name, plotBuffers.get(name).get(1).getBuffer(), plotBuffers.get(name).get(1).getSize(), 0, t2, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.plotLines(name, plotBuffers.get(name).get(2).getBuffer(), plotBuffers.get(name).get(2).getSize(), 0, t3, minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            ImGui.end();
        };

        addOrUpdatePanel(name, function);
    }


    public static void imgui() {
        for (Runnable panel : debugPanels) {
            panel.run();
        }
    }


}
