package Burst.Engine.Source.Core.UI;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import org.joml.Vector2f;

import java.util.*;

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
            int color;
            for (int i = 0; i < values.size(); i++) {
                addOrUpdatePlot(plotName, values.values().stream().toList());
                // Change the color of the plot line
                if (i < 3)
                {
                    // X, Y, Z axis : Red, Green, Blue
                    color = 0xFF000000 | 0x0000FF << 8 * i;
                }
                else
                {
                    // hash the name of the value
                    int seed = values.keySet().stream().toList().get(i).hashCode();

                    if (seed < 0)
                    {
                        seed *= -1;
                    }
                    float t = (float) ((seed % 10 + seed % 100) * seed) / 199;
                    t *= 1000;
                    t = (float) Math.sin(t);
                    t *= 1000;
                    t *= Math.PI;
                    t /= 100;
                    t *= 1000;
                    t = (float) Math.sin(t);
                    t *= 1000;
                    t *= Math.PI;
                    t /= 100;
                    t *= 1000;
                    t = (float) Math.sin(t);
                    t *= 1000;
                    t *= Math.PI;

                    // Value is between 0 and 1
                    t = Math.abs(t % 1);

                    // Lerp around the color wheel
                    float r = (float) Math.cos(t * 2 * Math.PI + 0) * 127 + 128;
                    float g = (float) Math.cos(t * 2 * Math.PI + 2) * 127 + 128;
                    float b = (float) Math.cos(t * 2 * Math.PI + 4) * 127 + 128;

                    // Convert to hex
                    color = 0xFF000000 | ((int) r << 16) | ((int) g << 8) | (int) b;
                }


                ImGui.pushStyleColor(ImGuiCol.PlotLines,   color);

                ImGui.plotLines(plotName, plotBuffers.get(plotName).get(i).getBuffer(), plotBuffers.get(plotName).get(i).getSize(), 0, values.keySet().stream().toList().get(i), minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);
                ImGui.popStyleColor();
            }
            ImGui.end();
        };

        addOrUpdatePanel(plotName, function);
    }

    public static void plotValues(String plotName, List<Float> values)
    {
        Map<String, Float> valueMap = new HashMap<>();
        for (int i = 0; i < values.size(); i++) {
            valueMap.put("Value " + i, values.get(i));
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
