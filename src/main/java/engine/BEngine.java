package engine;

import engine.input.KeyListener;
import engine.input.MouseListener;
import engine.render.Shapes;
import engine.render.Texture;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import java.awt.*;

import static java.awt.event.KeyEvent.*;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class BEngine {
    private int width, height;
    private String title;
    private Long glfwWindow;
    private static BEngine window = null;
    private boolean fullscreen;
    private BEngine(int width, int height, String title, boolean fullscreen) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.fullscreen = fullscreen;
    }

    public void setTitle(String Title) {
        BEngine.window.title = Title;
    }

    public static BEngine get() {
        if (BEngine.window == null) {
            BEngine.window = new BEngine(800, 800, "BEngine Application",true);
        }
        return BEngine.window;
    }
    public void setDimensions(int x, int y)
    {
        this.width = x;
        this.height = y;
    }
    public void run()
    {
        System.out.println("Starting BEngine " + Version.getVersion());
        init();
        loop();
        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and the free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    private void init()
    {
        //Setup Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Init GLFW
        if(!glfwInit())
        {
            throw new IllegalStateException("Couldn't Initialize GLFW");
        }
        //Config GLFW
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create Window
        //4th arg is fullscreen
        glfwWindow = glfwCreateWindow(this.width, this.height,this.title, this.fullscreen ? glfwGetPrimaryMonitor() : 0, NULL);
        if(glfwWindow == NULL)
        {
            throw new IllegalStateException("Failed to create GLFW window");
        }
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow,MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //Make OPENGL context current
        glfwMakeContextCurrent(glfwWindow);

        //Enable V-Sync
        glfwSwapInterval(1);
        //Make window visible
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();
        glViewport(0,0,1920,1080);
        glOrtho( 0.f, 1920, 1080, 0.f, 0.f, 1.f );

    }
    private void loop()
    {
        while(!glfwWindowShouldClose(glfwWindow))
        {
            //Poll
            if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_ALT) && KeyListener.isKeyPressed(GLFW_KEY_F4))
            {
                glfwSetWindowShouldClose(glfwWindow, true);
            }
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            Shapes.rect(50,50,200,200);
            glfwSwapBuffers(glfwWindow);
        }
    }
}
