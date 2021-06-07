package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

import java.util.List;

public abstract class Interpreter {

    protected final int api;


    protected Interpreter(int api) {
        this.api = api;
    }

    public abstract Value newValue(Type var1);

    public abstract Value newOperation(AbstractInsnNode var1) throws AnalyzerException;

    public abstract Value copyOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException;

    public abstract Value unaryOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException;

    public abstract Value binaryOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException;

    public abstract Value ternaryOperation(AbstractInsnNode var1, Value var2, Value var3, Value var4) throws AnalyzerException;

    public abstract Value naryOperation(AbstractInsnNode var1, List var2) throws AnalyzerException;

    public abstract void returnOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException;

    public abstract Value merge(Value var1, Value var2);
}
