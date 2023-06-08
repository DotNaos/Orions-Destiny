package Burst.Engine.Source.Core.Render;

import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.Util;
import imgui.ImGui;
import org.joml.Vector2i;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class PickingTexture extends Component {
    private int pickingTextureId;
    private int fbo;
    private int depthTexture;
    private boolean showDebug = false;

    public PickingTexture(int width, int height) {
        super();
        if (!init(width, height)) {
            assert false : "Error initializing picking texture";
        }
    }

    public PickingTexture() {
        super();
        if (!init(Window.getWidth(), Window.getHeight())) {
            assert false : "Error initializing picking texture";
        }
    }

    public boolean init(int width, int height) {
        // Generate framebuffer
        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);

        // Create the texture to render the data to, and attach it to our framebuffer
        pickingTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, pickingTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, width, height, 0, GL_RGBA, GL_FLOAT, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.pickingTextureId, 0);

        // Create the texture object for the depth buffer
        glEnable(GL_TEXTURE_2D);
        depthTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depthTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTexture, 0);

        // Disable the reading
        glReadBuffer(GL_NONE);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            assert false : "Error: Framebuffer is not complete";
            return false;
        }

        // Unbind the texture and framebuffer
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        return true;
    }

    public void enableWriting() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
    }

    public void disableWriting() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    public int readPixel(int x, int y) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        float pixels[] = new float[4];
        glReadPixels(x, y, 1, 1, GL_RGBA, GL_FLOAT, pixels);
        return (int) (pixels[0]) - 1;
    }

    public float[] getPickingActorBuffer(Vector2i start, Vector2i end) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        Vector2i size = new Vector2i(end).sub(start).absolute();
        int numPixels = size.x * size.y;
        float pixels[] = new float[4 * numPixels];
        glReadPixels(start.x, start.y, size.x, size.y, GL_RGBA, GL_FLOAT, pixels);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] -= 1;
            // DebugDraw a box around every pixel with a value
        }

        return pixels;
    }
    private void pickingTexturePreview() {
        ImGui.tableNextRow();

        ImGui.tableSetColumnIndex(0);
        ImGui.text("Picking Texture Preview");
        ImGui.tableSetColumnIndex(1);
        // Read all the pixels from the picking texture

        // Load the pixels into a buffer
        float[] pixelBuffer = getPickingActorBuffer();

        // Generate a Texture from the pixels with OpenGL
        Texture texture = new Texture(pixelBuffer, Window.getWidth(), Window.getHeight());

        // Display the texture in ImGui
        ImGui.image(texture.getTexID(), 600, 400);
    }

    /**
     * Modifies the pixels of the picking texture to represent the actor id
     * Add a unique color for each actor
     * @return Buffer of all pixels in the picking texture
     */
    public float[] getPickingActorBuffer() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        float pixels[] = new float[Window.getWidth() * Window.getHeight() * 4];
        glReadPixels(0, 0, Window.getWidth(), Window.getHeight(), GL_RGBA, GL_FLOAT, pixels);
        for (int i = 0; i < pixels.length; i++) {
            final int channel = i % 4 + 1;

            final int red = 1;
            final int green = 2;
            final int blue = 3;
            final int alpha = 4;

            final boolean isRed = channel == red;
            final boolean isGreen = channel == green;
            final boolean isBlue = channel == blue;
            final boolean isAlpha = channel == alpha;

            // set background to white
            if (pixels[i] == 0) {
                pixels[i] = 1;
                continue;
            }

            if (isAlpha) {
                pixels[i] -= 1;
            }
            else
            {
                pixels[i] = Util.generateUniqueColorValue((float) (pixels[i] * Math.sin(channel)), 1);
            }

        }


        return pixels;
    }

    public void imgui() {
        super.imgui();
        if (showDebug) {
            pickingTexturePreview();
        }
    }
}
