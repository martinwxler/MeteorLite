package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.MethodVisitor;

import java.util.Map;

public class IincInsnNode extends AbstractInsnNode {

    public int var;
    public int incr;


    public IincInsnNode(int var, int incr) {
        super(132);
        this.var = var;
        this.incr = incr;
    }

    public int getType() {
        return 10;
    }

    public void accept(MethodVisitor mv) {
        mv.visitIincInsn(this.var, this.incr);
        this.acceptAnnotations(mv);
    }

    public AbstractInsnNode clone(Map labels) {
        return (new IincInsnNode(this.var, this.incr)).cloneAnnotations(this);
    }
}
