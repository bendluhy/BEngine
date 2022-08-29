import engine.BEngine;

public class Main {
    public static void main(String[] args)
    {
        BEngine engine = BEngine.get();
        engine.setTitle("BEngine Development");
        engine.setDimensions(1920,1080);
        engine.run();
    }
}
