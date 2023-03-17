package Engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;
    private long glfwWindow;
    private float r, g, b, a;
    private boolean fadeToBlack;

    private static Window window = null;

    private Window()
    {
        this.width  = 1920;
        this.height = 1080;
        this.title = "Orions-Destiny";
        this.r = 1.0f;
        this.g = 0.1f;
        this.b = 0.3f;
        this.a = 1.0f;
    }

    public static Window get()
    {
        if(Window.window == null)
        {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run()
    {
        System.out.println("Window is running! Version: " + Version.getVersion());

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }



    public void init()
    {
        // error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    // Called every frame
    public void loop()
    {
        System.out.println("Window is looping!");
        while(!glfwWindowShouldClose(glfwWindow)) {

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            if(fadeToBlack){
                r = Math.max(r - 0.01f, 0.0f);
                g = Math.max(g - 0.01f, 0.0f);
                b = Math.max(b - 0.01f, 0.0f);
                a = Math.max(a - 0.01f, 0.0f);
            }


            if(MouseListener.isDragging())
            {
                System.out.println("Dragging!");
            }
            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE))
            {
                System.out.println("Space is pressed!");
                fadeToBlack = true;
            }


            if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE))
            {
                glfwSetWindowShouldClose(glfwWindow, true);
            }

            glfwSwapBuffers(glfwWindow); // swap the color buffers
        }
    }
}
