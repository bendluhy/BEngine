package engine.render;

import static org.lwjgl.opengl.GL11.glRectf;

public class Shapes {
    public static void rect(float x, float y, float w, float h)
    {
        glRectf(x, y, x + w, y + h);
    }
}
