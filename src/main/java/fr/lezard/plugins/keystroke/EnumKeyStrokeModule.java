package fr.lezard.plugins.keystroke;

public enum EnumKeyStrokeModule {
    ZQSD(KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT),
    ZQSD_MOUSE(KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.LMB, KeyStroke.RMB),
    ;

    private final KeyStroke[] keyStrokes;
    private int width, height;

    private EnumKeyStrokeModule(KeyStroke... keyStrokes){
        this.keyStrokes = keyStrokes;

        for(KeyStroke k : keyStrokes){
            this.width = Math.max(this.width, k.getX() + k.getWidth());
            this.height = Math.max(this.height, k.getY() + k.getHeight());
        }
    }

    public KeyStroke[] getKeyStrokes() {
        return keyStrokes;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
