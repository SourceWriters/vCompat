package net.sourcewriters.minecraft.vcompat.reflection.wrapper;

import java.io.IOException;
import java.io.Writer;

public abstract class ConsoleReaderWrapper {
    
    public abstract Writer getOutput();
    
    public abstract boolean isAnsiSupported();
    
    public abstract boolean isJLineSupported();
    
    public abstract void flush() throws IOException;
    
    public abstract void drawLine() throws IOException;
    
}
