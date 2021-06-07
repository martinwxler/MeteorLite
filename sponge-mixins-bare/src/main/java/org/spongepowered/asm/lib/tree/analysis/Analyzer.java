package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Analyzer implements Opcodes {

    private final Interpreter interpreter;
    private int n;
    private InsnList insns;
    private List[] handlers;
    private Frame[] frames;
    private Subroutine[] subroutines;
    private boolean[] queued;
    private int[] queue;
    private int top;


    public Analyzer(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Frame[] analyze(String owner, MethodNode m) throws AnalyzerException {
        if ((m.access & 1280) != 0) {
            this.frames = (Frame[]) (new Frame[0]);
            return this.frames;
        } else {
            this.n = m.instructions.size();
            this.insns = m.instructions;
            this.handlers = (List[]) (new List[this.n]);
            this.frames = (Frame[]) (new Frame[this.n]);
            this.subroutines = new Subroutine[this.n];
            this.queued = new boolean[this.n];
            this.queue = new int[this.n];
            this.top = 0;

            int current;
            for (int main = 0; main < m.tryCatchBlocks.size(); ++main) {
                TryCatchBlockNode subroutineCalls = (TryCatchBlockNode) m.tryCatchBlocks.get(main);
                int subroutineHeads = this.insns.indexOf(subroutineCalls.start);
                current = this.insns.indexOf(subroutineCalls.end);

                for (int handler = subroutineHeads; handler < current; ++handler) {
                    Object args = this.handlers[handler];
                    if (args == null) {
                        args = new ArrayList();
                        this.handlers[handler] = (List) args;
                    }

                    ((List) args).add(subroutineCalls);
                }
            }

            Subroutine var23 = new Subroutine((LabelNode) null, m.maxLocals, (JumpInsnNode) null);
            ArrayList var24 = new ArrayList();
            HashMap var25 = new HashMap();
            this.findSubroutine(0, var23, var24);

            while (!var24.isEmpty()) {
                JumpInsnNode var26 = (JumpInsnNode) var24.remove(0);
                Subroutine var28 = (Subroutine) var25.get(var26.label);
                if (var28 == null) {
                    var28 = new Subroutine(var26.label, m.maxLocals, var26);
                    var25.put(var26.label, var28);
                    this.findSubroutine(this.insns.indexOf(var26.label), var28, var24);
                } else {
                    var28.callers.add(var26);
                }
            }

            for (current = 0; current < this.n; ++current) {
                if (this.subroutines[current] != null && this.subroutines[current].start == null) {
                    this.subroutines[current] = null;
                }
            }

            Frame var27 = this.newFrame(m.maxLocals, m.maxStack);
            Frame var29 = this.newFrame(m.maxLocals, m.maxStack);
            var27.setReturn(this.interpreter.newValue(Type.getReturnType(m.desc)));
            Type[] var30 = Type.getArgumentTypes(m.desc);
            int local = 0;
            if ((m.access & 8) == 0) {
                Type insn = Type.getObjectType(owner);
                var27.setLocal(local++, this.interpreter.newValue(insn));
            }

            int var31;
            for (var31 = 0; var31 < var30.length; ++var31) {
                var27.setLocal(local++, this.interpreter.newValue(var30[var31]));
                if (var30[var31].getSize() == 2) {
                    var27.setLocal(local++, this.interpreter.newValue((Type) null));
                }
            }

            while (local < m.maxLocals) {
                var27.setLocal(local++, this.interpreter.newValue((Type) null));
            }

            this.merge(0, var27, (Subroutine) null);
            this.init(owner, m);

            while (this.top > 0) {
                var31 = this.queue[--this.top];
                Frame f = this.frames[var31];
                Subroutine subroutine = this.subroutines[var31];
                this.queued[var31] = false;
                AbstractInsnNode insnNode = null;

                try {
                    insnNode = m.instructions.get(var31);
                    int e = insnNode.getOpcode();
                    int insnType = insnNode.getType();
                    int i;
                    if (insnType != 8 && insnType != 15 && insnType != 14) {
                        var27.init(f).execute(insnNode, this.interpreter);
                        subroutine = subroutine == null ? null : subroutine.copy();
                        if (insnNode instanceof JumpInsnNode) {
                            JumpInsnNode insnHandlers = (JumpInsnNode) insnNode;
                            if (e != 167 && e != 168) {
                                this.merge(var31 + 1, var27, subroutine);
                                this.newControlFlowEdge(var31, var31 + 1);
                            }

                            i = this.insns.indexOf(insnHandlers.label);
                            if (e == 168) {
                                this.merge(i, var27, new Subroutine(insnHandlers.label, m.maxLocals, insnHandlers));
                            } else {
                                this.merge(i, var27, subroutine);
                            }

                            this.newControlFlowEdge(var31, i);
                        } else {
                            int tcb;
                            LabelNode type;
                            if (insnNode instanceof LookupSwitchInsnNode) {
                                LookupSwitchInsnNode var32 = (LookupSwitchInsnNode) insnNode;
                                i = this.insns.indexOf(var32.dflt);
                                this.merge(i, var27, subroutine);
                                this.newControlFlowEdge(var31, i);

                                for (tcb = 0; tcb < var32.labels.size(); ++tcb) {
                                    type = (LabelNode) var32.labels.get(tcb);
                                    i = this.insns.indexOf(type);
                                    this.merge(i, var27, subroutine);
                                    this.newControlFlowEdge(var31, i);
                                }
                            } else if (insnNode instanceof TableSwitchInsnNode) {
                                TableSwitchInsnNode var33 = (TableSwitchInsnNode) insnNode;
                                i = this.insns.indexOf(var33.dflt);
                                this.merge(i, var27, subroutine);
                                this.newControlFlowEdge(var31, i);

                                for (tcb = 0; tcb < var33.labels.size(); ++tcb) {
                                    type = (LabelNode) var33.labels.get(tcb);
                                    i = this.insns.indexOf(type);
                                    this.merge(i, var27, subroutine);
                                    this.newControlFlowEdge(var31, i);
                                }
                            } else {
                                int var34;
                                if (e == 169) {
                                    if (subroutine == null) {
                                        throw new AnalyzerException(insnNode, "RET instruction outside of a sub routine");
                                    }

                                    for (var34 = 0; var34 < subroutine.callers.size(); ++var34) {
                                        JumpInsnNode var36 = (JumpInsnNode) subroutine.callers.get(var34);
                                        tcb = this.insns.indexOf(var36);
                                        if (this.frames[tcb] != null) {
                                            this.merge(tcb + 1, this.frames[tcb], var27, this.subroutines[tcb], subroutine.access);
                                            this.newControlFlowEdge(var31, tcb + 1);
                                        }
                                    }
                                } else if (e != 191 && (e < 172 || e > 177)) {
                                    if (subroutine != null) {
                                        if (insnNode instanceof VarInsnNode) {
                                            var34 = ((VarInsnNode) insnNode).var;
                                            subroutine.access[var34] = true;
                                            if (e == 22 || e == 24 || e == 55 || e == 57) {
                                                subroutine.access[var34 + 1] = true;
                                            }
                                        } else if (insnNode instanceof IincInsnNode) {
                                            var34 = ((IincInsnNode) insnNode).var;
                                            subroutine.access[var34] = true;
                                        }
                                    }

                                    this.merge(var31 + 1, var27, subroutine);
                                    this.newControlFlowEdge(var31, var31 + 1);
                                }
                            }
                        }
                    } else {
                        this.merge(var31 + 1, f, subroutine);
                        this.newControlFlowEdge(var31, var31 + 1);
                    }

                    List var35 = this.handlers[var31];
                    if (var35 != null) {
                        for (i = 0; i < var35.size(); ++i) {
                            TryCatchBlockNode var37 = (TryCatchBlockNode) var35.get(i);
                            Type var38;
                            if (var37.type == null) {
                                var38 = Type.getObjectType("java/lang/Throwable");
                            } else {
                                var38 = Type.getObjectType(var37.type);
                            }

                            int jump = this.insns.indexOf(var37.handler);
                            if (this.newControlFlowExceptionEdge(var31, var37)) {
                                var29.init(f);
                                var29.clearStack();
                                var29.push(this.interpreter.newValue(var38));
                                this.merge(jump, var29, subroutine);
                            }
                        }
                    }
                } catch (AnalyzerException var21) {
                    throw new AnalyzerException(var21.node, "Error at instruction " + var31 + ": " + var21.getMessage(), var21);
                } catch (Exception var22) {
                    throw new AnalyzerException(insnNode, "Error at instruction " + var31 + ": " + var22.getMessage(), var22);
                }
            }

            return this.frames;
        }
    }

    private void findSubroutine(int insn, Subroutine sub, List calls) throws AnalyzerException {
        while (insn >= 0 && insn < this.n) {
            if (this.subroutines[insn] != null) {
                return;
            }

            this.subroutines[insn] = sub.copy();
            AbstractInsnNode node = this.insns.get(insn);
            int i;
            if (node instanceof JumpInsnNode) {
                if (node.getOpcode() == 168) {
                    calls.add(node);
                } else {
                    JumpInsnNode insnHandlers = (JumpInsnNode) node;
                    this.findSubroutine(this.insns.indexOf(insnHandlers.label), sub, calls);
                }
            } else {
                LabelNode tcb;
                if (node instanceof TableSwitchInsnNode) {
                    TableSwitchInsnNode var8 = (TableSwitchInsnNode) node;
                    this.findSubroutine(this.insns.indexOf(var8.dflt), sub, calls);

                    for (i = var8.labels.size() - 1; i >= 0; --i) {
                        tcb = (LabelNode) var8.labels.get(i);
                        this.findSubroutine(this.insns.indexOf(tcb), sub, calls);
                    }
                } else if (node instanceof LookupSwitchInsnNode) {
                    LookupSwitchInsnNode var9 = (LookupSwitchInsnNode) node;
                    this.findSubroutine(this.insns.indexOf(var9.dflt), sub, calls);

                    for (i = var9.labels.size() - 1; i >= 0; --i) {
                        tcb = (LabelNode) var9.labels.get(i);
                        this.findSubroutine(this.insns.indexOf(tcb), sub, calls);
                    }
                }
            }

            List var10 = this.handlers[insn];
            if (var10 != null) {
                for (i = 0; i < var10.size(); ++i) {
                    TryCatchBlockNode var11 = (TryCatchBlockNode) var10.get(i);
                    this.findSubroutine(this.insns.indexOf(var11.handler), sub, calls);
                }
            }

            switch (node.getOpcode()) {
                case 167:
                case 169:
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 191:
                    return;
                case 168:
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 185:
                case 186:
                case 187:
                case 188:
                case 189:
                case 190:
                default:
                    ++insn;
            }
        }

        throw new AnalyzerException((AbstractInsnNode) null, "Execution can fall off end of the code");
    }

    public Frame[] getFrames() {
        return this.frames;
    }

    public List getHandlers(int insn) {
        return this.handlers[insn];
    }

    protected void init(String owner, MethodNode m) throws AnalyzerException {
    }

    protected Frame newFrame(int nLocals, int nStack) {
        return new Frame(nLocals, nStack);
    }

    protected Frame newFrame(Frame src) {
        return new Frame(src);
    }

    protected void newControlFlowEdge(int insn, int successor) {
    }

    protected boolean newControlFlowExceptionEdge(int insn, int successor) {
        return true;
    }

    protected boolean newControlFlowExceptionEdge(int insn, TryCatchBlockNode tcb) {
        return this.newControlFlowExceptionEdge(insn, this.insns.indexOf(tcb.handler));
    }

    private void merge(int insn, Frame frame, Subroutine subroutine) throws AnalyzerException {
        Frame oldFrame = this.frames[insn];
        Subroutine oldSubroutine = this.subroutines[insn];
        boolean changes;
        if (oldFrame == null) {
            this.frames[insn] = this.newFrame(frame);
            changes = true;
        } else {
            changes = oldFrame.merge(frame, this.interpreter);
        }

        if (oldSubroutine == null) {
            if (subroutine != null) {
                this.subroutines[insn] = subroutine.copy();
                changes = true;
            }
        } else if (subroutine != null) {
            changes |= oldSubroutine.merge(subroutine);
        }

        if (changes && !this.queued[insn]) {
            this.queued[insn] = true;
            this.queue[this.top++] = insn;
        }

    }

    private void merge(int insn, Frame beforeJSR, Frame afterRET, Subroutine subroutineBeforeJSR, boolean[] access) throws AnalyzerException {
        Frame oldFrame = this.frames[insn];
        Subroutine oldSubroutine = this.subroutines[insn];
        afterRET.merge(beforeJSR, access);
        boolean changes;
        if (oldFrame == null) {
            this.frames[insn] = this.newFrame(afterRET);
            changes = true;
        } else {
            changes = oldFrame.merge(afterRET, this.interpreter);
        }

        if (oldSubroutine != null && subroutineBeforeJSR != null) {
            changes |= oldSubroutine.merge(subroutineBeforeJSR);
        }

        if (changes && !this.queued[insn]) {
            this.queued[insn] = true;
            this.queue[this.top++] = insn;
        }

    }
}
