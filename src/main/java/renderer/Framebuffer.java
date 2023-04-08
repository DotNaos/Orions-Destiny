package renderer;

import static org.lwjgl.opengl.GL30.*;

public class Framebuffer {
    private int fboID = 0;
    private Texture texture = null;

    public int getFboID() {
        return fboID;
    }

    public int getTextureID() {
        return texture.getTexID();
    }

    public void bind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, this.fboID);
    }

    public void unbind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public Framebuffer(int width, int height)
    {
        // Generate framebuffer
        this.fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, this.fboID);

        // Create texture to render to, and attach it to framebuffer
        this.texture = new Texture(width, height);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.texture.getTexID(), 0);

        // Create renderbuffer store the depth info
        int rboID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
        {
            assert false : "Error: Framebuffer not complete!";
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

    }


}
