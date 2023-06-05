package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import javax.swing.plaf.TextUI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.stb.STBImage.*;

public class Texture extends Asset {
    private transient int texID;
    private int width, height;
    private transient boolean initialized = false;

    public Texture(String filepath) {
        super(filepath);
    }

    public Texture() {
        super("Generated");
        texID = -1;
        width = -1;
        height = -1;
    }


    public Texture(int width, int height) {
        super("Generated");

        // Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);
    }

    @Override
    public void init() {
        // Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);





        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(false);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);

            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, this.width, this.height,
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height,
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
            }
        } else {
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";
            return;
        }

        stbi_image_free(image);
        initialized = true;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return this.width;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTexID() {
        return texID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Texture)) return false;
        Texture oTex = (Texture) o;
        return oTex.getWidth() == this.width && oTex.getHeight() == this.height &&
                oTex.getTexID() == this.texID &&
                oTex.getFilepath().equals(this.filepath);
    }


    public int readPixel(int x, int y) {
//        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        float pixels[] = new float[3];
        glReadPixels(x, y, 1, 1, GL_RGB, GL_FLOAT, pixels);

        return (int) (pixels[0]) - 1;
    }

    public float[] readPixels(Vector2i start, Vector2i end) {
//        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glReadBuffer(GL_COLOR_ATTACHMENT0);

        Vector2i size = new Vector2i(end).sub(start).absolute();
        int numPixels = size.x * size.y;
        float pixels[] = new float[3 * numPixels];
        glReadPixels(start.x, start.y, size.x, size.y, GL_RGB, GL_FLOAT, pixels);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] -= 1;
        }

        return pixels;
    }

    @Override
    public Asset build() {
        return this;
    }

    public boolean isInitialized() {
        return initialized;
    }
}
