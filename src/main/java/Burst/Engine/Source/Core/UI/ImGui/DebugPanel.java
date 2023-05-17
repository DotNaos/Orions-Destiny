package Burst.Engine.Source.Core.UI.ImGui;

import Burst.Engine.Source.Core.UI.PlotBuffer;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import org.joml.Vector3f;

import java.util.*;

/**
 * 
 * The <code>DebugPanel</code> class provides a way to display debug information
 * in an ImGui window.
 * It contains static methods to plot float values in graphs, where each plot
 * has a name.
 * <p>
 * These methods accept different number of float values to plot, and allow to
 * plot a single value, an array of values or a map of values.
 * </p>
 * <p>
 * The class stores the plot values in buffers of a fixed size (2000), that get
 * automatically truncated when new values are added to keep the buffer size
 * constant.
 * The plot scale can be customized by changing the <code>minScale</code> and
 * <code>maxScale</code> constants,
 * the default range is set to [-100, 100].
 * </p>
 * <p>
 * It also stores a list of all the debug panels that have been registered, each
 * of them containing different plots, and provides a way to update and display
 * these panels in an ImGui window.
 * </p>
 * <p>
 * To add a new value to a plot use the method <code>plotValue</code>, providing
 * the plot name and the value to add as arguments.
 * To plot multiple values on a graph with the same plot name, use one of the
 * methods <code>plotValues</code> that take a variable number of float
 * arguments.
 * </p>
 * <p>
 * To create a new debug panel, register it by passing a <code>Runnable</code>
 * that adds all the desired plots to the panel to the
 * <code>registerDebugPanel</code> method.
 * Then call the <code>update</code> method to update all the registered debug
 * panels.
 * Finally, call the <code>display</code> method to show all the registered
 * debug panels in an ImGui window.
 * </p>
 * 
 * @param plotValue          method used to add a new value to a plot
 * @param plotValues         method used to plot multiple values on a graph with
 *                           the same plot name
 * @param registerDebugPanel method used to register a new debug panel
 * @param update             method used to update all the registered debug
 *                           panels
 * @param display            method used to show all the registered debug panels
 *                           in an ImGui window
 */
public class DebugPanel {
    /**
     * The maximum number of values that can be stored in a plot buffer.
     */
    private final static int bufferSize = 2000;

    /**
     * The maximum scale value for a plot.
     */
    public static int maxScale = 100;

    /**
     * The minimum scale value for a plot.
     */
    public static int minScale = -100;

    /**
     * A list of all the debug panels that have been registered.
     */
    private static List<Runnable> debugPanels = new ArrayList<>();

    /**
     * A list of the names of all the debug panels that have been registered.
     */
    private static List<String> debugPanelNames = new ArrayList<>();

    /**
     * A map of all the plot buffers that have been created.
     */
    private static Map<String, List<PlotBuffer>> plotBuffers = new HashMap<>();

    /**
     * Adds a new value to the specified plot.
     *
     * @param plotName The name of the plot to add the value to.
     * @param value    The value to add to the plot.
     */
    public static void plotValue(String plotName, float value) {
        List<Float> values = new ArrayList<>();
        values.add(value);
        plotValues(plotName, values);
    }

    /**
     * Plots two values on a graph with the given plot name.
     *
     * @param plotName the name of the plot to add the values to
     * @param v1       the first value to plot
     * @param v2       the second value to plot
     */
    public static void plotValues(String plotName, float v1, float v2) {
        List<Float> values = new ArrayList<>();
        values.add(v1);
        values.add(v2);
        plotValues(plotName, values);
    }

    /**
     * Plots three values on a graph with the given plot name.
     *
     * @param plotName the name of the plot to add the values to
     * @param v1       the first value to plot
     * @param v2       the second value to plot
     * @param v3       the third value to plot
     */
    public static void plotValues(String plotName, float v1, float v2, float v3) {
        List<Float> values = new ArrayList<>();
        values.add(v1);
        values.add(v2);
        values.add(v3);
        plotValues(plotName, values);
    }

    /**
     * Plots an array of values on a graph with the given plot name.
     *
     * @param plotName the name of the plot to add the values to
     * @param values   the array of values to plot
     */
    public static void plotValues(String plotName, float[] values) {
        List<Float> valueList = new ArrayList<>();
        for (float value : values) {
            valueList.add(value);
        }
        plotValues(plotName, valueList);
    }

    /**
     * Plots a map of values on a graph with the given plot name.
     *
     * @param plotName the name of the plot to add the values to
     * @param values   the map of values to plot
     */
    public static void plotValues(String plotName, Map<String, Float> values) {
        Runnable function = () -> {
            ImGui.begin(plotName);
            int color;
            for (int i = 0; i < values.size(); i++) {
                addOrUpdatePlot(plotName, values.values().stream().toList());
                // Change the color of the plot line
                // For the first 3 values, use the X, Y, Z axis colors
                if (i < 3) {
                    // X, Y, Z axis : Red, Green, Blue
                    color = 0xFF000000 | 0x0000FF << 8 * i;
                } else {
                    // Other values : Random color
                    // hash the name of the value
                    int seed = values.keySet().stream().toList().get(i).hashCode();

                    if (seed < 0) {
                        seed *= -1;
                    }
                    float t = (float) ((seed % 10 + seed % 100) * seed) / 199;

                    // increase the difference between the colors
                    for (int j = 0; j < 4; j++) {
                        t *= 1000;
                        t = (float) Math.sin(t);
                        t *= 1000;
                        t *= Math.PI;
                        t /= 100;
                    }
                    
                    // Make sure t is between 0 and 1
                    t = Math.abs(t % 1);

                    // Lerp around the color wheel
                    float r = (float) Math.cos(t * 2 * Math.PI + 0) * 127 + 128;
                    float g = (float) Math.cos(t * 2 * Math.PI + 2) * 127 + 128;
                    float b = (float) Math.cos(t * 2 * Math.PI + 4) * 127 + 128;

                    // Convert to hex
                    color = 0xFF000000 | ((int) r << 16) | ((int) g << 8) | (int) b;
                }

                // Update the plot line color
                ImGui.pushStyleColor(ImGuiCol.PlotLines, color);

                // Plot the line
                ImGui.plotLines(plotName, plotBuffers.get(plotName).get(i).getBuffer(),
                        plotBuffers.get(plotName).get(i).getSize(), 0, values.keySet().stream().toList().get(i),
                        minScale, maxScale, ImGui.getWindowWidth(), ImGui.getWindowWidth() / 2);

                // Stops using the style color
                ImGui.popStyleColor();
            }
            ImGui.end();
        };

        // Add or update the debug panel
        addOrUpdatePanel(plotName, function);
    }

    /**
     * Plots a list of values on a graph with the given plot name.
     *
     * @param plotName the name of the plot to add the values to
     * @param values   the list of values to plot
     */
    public static void plotValues(String plotName, List<Float> values) {
        Map<String, Float> valueMap = new HashMap<>();

        // Loop through the values and add them to the map
        for (int i = 0; i < values.size(); i++) {
            valueMap.put("Value " + i, values.get(i));
        }
        plotValues(plotName, valueMap);
    }

    /**
     * Adds or updates a debug panel with the given name and function.
     *
     * @param name     the name of the debug panel
     * @param function the function to run when the debug panel is updated
     */
    private static void addOrUpdatePanel(String name, Runnable function) {
        int index = debugPanelNames.indexOf(name);
        if (index != -1) {
            // If the panel already exists, update it
            debugPanels.set(index, function);
        } else {
            // If the panel doesn't exist, add it
            registerDebugPanel(name, function);
        }
        Window.getImguiLayer().update(0, Window.getScene());
    }

    /**
     * Registers a debug panel with the given name and function.
     * 
     * @param name     the name of the debug panel
     * @param function the function to run when the debug panel is updated
     */

    private static void registerDebugPanel(String name, Runnable function) {
        debugPanelNames.add(name);
        debugPanels.add(function);
    }

    /**
     * Adds or updates a plot with the given name and list of values.
     *
     * @param name   the name of the plot
     * @param values the list of values to add to the plot
     */
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
                // Add a new buffer for each value
                plotBuffers.get(name).add(new PlotBuffer(bufferSize));
            }
        }

    }

    /**
     * Renders all debug panels.
     */
    public static void imgui() {

        // Iterate through all the debug panels execute their functions
        for (Runnable panel : debugPanels) {
            panel.run();
        }
    }

}
