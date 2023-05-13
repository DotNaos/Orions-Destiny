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



    public static void plotValues(String plotName, Map<String, Float> values)
    {
        Runnable function = () -> {
            ImGui.begin(plotName);
            for (int i = 0; i < values.size(); i++) {
                addOrUpdatePlot(plotName, values.values().stream().toList());
                ImGui.plotLines(plotName, plotBuffers.get(plotName).get(i).getBuffer(), plotBuffers.get(plotName).get(i).getSize(), 0, values.keySet().stream().toList().get(i), minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
            }
            ImGui.end();
        };

        addOrUpdatePanel(plotName, function);
    }

    public static void plotValues(String plotName, float[] values)
    {
        Map<String, Float> valueMap = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            valueMap.put("", values[i]);
        }
        plotValues(plotName, valueMap);
    }

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

    private static void addOrUpdatePlot(String name, List<Float> values) {
        int bufferCount = values.size();
        if (plotBuffers.containsKey(name)) {
            // If it has a buffer, update it
            for (int i = 0; i < bufferCount; i++) {
                plotBuffers.get(name).get(i).update(values.get(i));
            }
        } else {
            // If it has no buffer, add one
            plotBuffers.put(name, new ArrayList<>());

            for (int i = 0; i < bufferCount; i++) {
                plotBuffers.get(name).add(new PlotBuffer(bufferSize));
            }
        }


    }

    public static void imgui() {
        for (Runnable panel : debugPanels) {
            panel.run();
        }
    }


}
