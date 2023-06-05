package Burst.Engine.Source.Core.UI;

import Burst.Engine.Config.Shader_Config;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Render.Framebuffer;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Render.ViewportRenderer;
import Burst.Engine.Source.Core.EventSystem.EventSystem;
import Burst.Engine.Source.Core.EventSystem.Events.Event;
import Burst.Engine.Source.Core.EventSystem.Observer;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.Scene.SceneType;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiLayer;
import org.joml.Vector4f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Observer {
    private static boolean isPlaying = false;
    private static boolean imguiActive = false;
    private static Window window = null;
    private static Scene currentScene;
    private final String title;
    private int width, height;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;
    private Framebuffer framebuffer;
    private long audioContext;
    private long audioDevice;
    private PickingTexture pickingTexture;


    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Burst";
        EventSystem.addObserver(this);
    }

    public static void changeScene(SceneType sceneType) {
        currentScene = new Scene();
        currentScene.init(sceneType);
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Physics2D getPhysics() {
        return currentScene.getGame().getPhysics();
    }

    public static Scene getScene() {
        return currentScene;
    }


    public static boolean isIsPlaying() {
        return isPlaying;
    }

    public static int getWidth() {
        return 1920;//get().width;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static int getHeight() {
        return 1080;//get().height;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static Framebuffer getFramebuffer() {
        return get().framebuffer;
    }

    public static ImGuiLayer getImguiLayer() {
        return get().imguiLayer;
    }

    public static boolean isImguiActive() {
        return imguiActive;
    }

    public void run() {
        System.out.println("LWJGL Version: " + Version.getVersion() + "!");

        init();
        loop();

        // Destroy the audio context
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // Initialize the audio device
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        assert alCapabilities.OpenAL10 : "Audio library not supported.";


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        this.framebuffer = new Framebuffer(ViewportRenderer.getViewportSize().x, ViewportRenderer.getViewportSize().y);
        this.pickingTexture = new PickingTexture(ViewportRenderer.getViewportSize().x, ViewportRenderer.getViewportSize().y);
        glViewport(0, 0, ViewportRenderer.getViewportSize().x, ViewportRenderer.getViewportSize().y);

        this.imguiLayer = new ImGuiLayer(glfwWindow);
        this.imguiLayer.initImGui();

        Window.changeScene(SceneType.START_MENU);
    }

    public void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;


        Shader defaultShader = AssetManager.getAssetFromType(Shader_Config.SHADER_DEFAULT, Shader.class);
        Shader pickingShader = AssetManager.getAssetFromType(Shader_Config.SHADER_PICKING, Shader.class);

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);
            pickingTexture.enableWriting();

            glViewport(0, 0, Window.getWidth() , Window.getHeight());
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            ViewportRenderer.bindShader(pickingShader);
            currentScene.render();

            pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            DebugDraw.beginFrame();

            this.framebuffer.bind();
            Vector4f clearColor = currentScene.getViewport().clearColor;
            glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                ViewportRenderer.bindShader(defaultShader);
                currentScene.update(dt);
                currentScene.render();
                DebugDraw.draw();
            }
            this.framebuffer.unbind();

            imguiActive = true;
            this.imguiLayer.update(dt, currentScene);
            imguiActive = false;

            KeyListener.endFrame();
            MouseListener.endFrame();
            glfwSwapBuffers(glfwWindow);

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public void setCursor(int cursor) {
        glfwSetCursor(glfwWindow, cursor);
    }

    @Override
    public void onNotify(Actor object, Event event) {
        switch (event.type) {
            case GameEngineStartPlay -> {
                isPlaying = true;
                currentScene.getGame().saveLevel();
                Window.changeScene(SceneType.GAME);
            }
            case GameEngineStopPlay -> {
                isPlaying = false;
                Window.changeScene(SceneType.EDITOR);
            }
            case LoadLevel -> {
                Window.changeScene(SceneType.GAME);
            }
            case SaveLevel -> {
                assert currentScene.getGame() != null;
                currentScene.getGame().saveLevel();
            }
        }
    }
}
