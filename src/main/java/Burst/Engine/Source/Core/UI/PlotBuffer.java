package Burst.Engine.Source.Core.UI;

public class PlotBuffer {
    private float[] buffer;

    private int size = 0;

    public PlotBuffer(int size) {
        this.size = size;
        buffer = new float[size];
    }

    public void update(float value) {
        for (int i = 0; i < buffer.length - 1; i++) {
            buffer[i] = buffer[i + 1];
        }
        buffer[buffer.length - 1] = value;
    }

    public float[] getBuffer() {
        return buffer;
    }

    public int getSize() {
        return size;
    }



}
