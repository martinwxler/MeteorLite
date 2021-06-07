package org.spongepowered.asm.lib;

class MethodWriter extends MethodVisitor {

    static final int ACC_CONSTRUCTOR = 524288;
    static final int SAME_FRAME = 0;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
    static final int RESERVED = 128;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
    static final int CHOP_FRAME = 248;
    static final int SAME_FRAME_EXTENDED = 251;
    static final int APPEND_FRAME = 252;
    static final int FULL_FRAME = 255;
    static final int FRAMES = 0;
    static final int INSERTED_FRAMES = 1;
    static final int MAXS = 2;
    static final int NOTHING = 3;
    final ClassWriter cw;
    private final int name;
    private final int desc;
    private final String descriptor;
    private final int compute;
    String signature;
    int classReaderOffset;
    int classReaderLength;
    int exceptionCount;
    int[] exceptions;
    private int access;
    private ByteVector annd;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private AnnotationWriter[] panns;
    private AnnotationWriter[] ipanns;
    private int synthetics;
    private Attribute attrs;
    private ByteVector code = new ByteVector();
    private int maxStack;
    private int maxLocals;
    private int currentLocals;
    private int frameCount;
    private ByteVector stackMap;
    private int previousFrameOffset;
    private int[] previousFrame;
    private int[] frame;
    private int handlerCount;
    private Handler firstHandler;
    private Handler lastHandler;
    private int methodParametersCount;
    private ByteVector methodParameters;
    private int localVarCount;
    private ByteVector localVar;
    private int localVarTypeCount;
    private ByteVector localVarType;
    private int lineNumberCount;
    private ByteVector lineNumber;
    private int lastCodeOffset;
    private AnnotationWriter ctanns;
    private AnnotationWriter ictanns;
    private Attribute cattrs;
    private int subroutines;
    private Label labels;
    private Label previousBlock;
    private Label currentBlock;
    private int stackSize;
    private int maxStackSize;


    MethodWriter(ClassWriter cw, int access, String name, String desc, String signature, String[] exceptions, int compute) {
        super(327680);
        if (cw.firstMethod == null) {
            cw.firstMethod = this;
        } else {
            cw.lastMethod.mv = this;
        }

        cw.lastMethod = this;
        this.cw = cw;
        this.access = access;
        if ("<init>".equals(name)) {
            this.access |= 524288;
        }

        this.name = cw.newUTF8(name);
        this.desc = cw.newUTF8(desc);
        this.descriptor = desc;
        this.signature = signature;
        int size;
        if (exceptions != null && exceptions.length > 0) {
            this.exceptionCount = exceptions.length;
            this.exceptions = new int[this.exceptionCount];

            for (size = 0; size < this.exceptionCount; ++size) {
                this.exceptions[size] = cw.newClass(exceptions[size]);
            }
        }

        this.compute = compute;
        if (compute != 3) {
            size = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
            if ((access & 8) != 0) {
                --size;
            }

            this.maxLocals = size;
            this.currentLocals = size;
            this.labels = new Label();
            this.labels.status |= 8;
            this.visitLabel(this.labels);
        }

    }

    public void visitParameter(String name, int access) {
        if (this.methodParameters == null) {
            this.methodParameters = new ByteVector();
        }

        ++this.methodParametersCount;
        this.methodParameters.putShort(name == null ? 0 : this.cw.newUTF8(name)).putShort(access);
    }

    public AnnotationVisitor visitAnnotationDefault() {
        this.annd = new ByteVector();
        return new AnnotationWriter(this.cw, false, this.annd, (ByteVector) null, 0);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        bv.putShort(this.cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
        if (visible) {
            aw.next = this.anns;
            this.anns = aw;
        } else {
            aw.next = this.ianns;
            this.ianns = aw;
        }

        return aw;
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        AnnotationWriter.putTarget(typeRef, typePath, bv);
        bv.putShort(this.cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.tanns;
            this.tanns = aw;
        } else {
            aw.next = this.itanns;
            this.itanns = aw;
        }

        return aw;
    }

    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(desc)) {
            this.synthetics = Math.max(this.synthetics, parameter + 1);
            return new AnnotationWriter(this.cw, false, bv, (ByteVector) null, 0);
        } else {
            bv.putShort(this.cw.newUTF8(desc)).putShort(0);
            AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
            if (visible) {
                if (this.panns == null) {
                    this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
                }

                aw.next = this.panns[parameter];
                this.panns[parameter] = aw;
            } else {
                if (this.ipanns == null) {
                    this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
                }

                aw.next = this.ipanns[parameter];
                this.ipanns[parameter] = aw;
            }

            return aw;
        }
    }

    public void visitAttribute(Attribute attr) {
        if (attr.isCodeAttribute()) {
            attr.next = this.cattrs;
            this.cattrs = attr;
        } else {
            attr.next = this.attrs;
            this.attrs = attr;
        }

    }

    public void visitCode() {
    }

    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        if (this.compute != 0) {
            if (this.compute == 1) {
                if (this.currentBlock.frame == null) {
                    this.currentBlock.frame = new CurrentFrame();
                    this.currentBlock.frame.owner = this.currentBlock;
                    this.currentBlock.frame.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), nLocal);
                    this.visitImplicitFirstFrame();
                } else {
                    if (type == -1) {
                        this.currentBlock.frame.set(this.cw, nLocal, local, nStack, stack);
                    }

                    this.visitFrame(this.currentBlock.frame);
                }
            } else {
                int delta;
                int i;
                if (type == -1) {
                    if (this.previousFrame == null) {
                        this.visitImplicitFirstFrame();
                    }

                    this.currentLocals = nLocal;
                    delta = this.startFrame(this.code.length, nLocal, nStack);

                    for (i = 0; i < nLocal; ++i) {
                        if (local[i] instanceof String) {
                            this.frame[delta++] = 24117248 | this.cw.addType((String) local[i]);
                        } else if (local[i] instanceof Integer) {
                            this.frame[delta++] = ((Integer) local[i]).intValue();
                        } else {
                            this.frame[delta++] = 25165824 | this.cw.addUninitializedType("", ((Label) local[i]).position);
                        }
                    }

                    for (i = 0; i < nStack; ++i) {
                        if (stack[i] instanceof String) {
                            this.frame[delta++] = 24117248 | this.cw.addType((String) stack[i]);
                        } else if (stack[i] instanceof Integer) {
                            this.frame[delta++] = ((Integer) stack[i]).intValue();
                        } else {
                            this.frame[delta++] = 25165824 | this.cw.addUninitializedType("", ((Label) stack[i]).position);
                        }
                    }

                    this.endFrame();
                } else {
                    if (this.stackMap == null) {
                        this.stackMap = new ByteVector();
                        delta = this.code.length;
                    } else {
                        delta = this.code.length - this.previousFrameOffset - 1;
                        if (delta < 0) {
                            if (type == 3) {
                                return;
                            }

                            throw new IllegalStateException();
                        }
                    }

                    label95:
                    switch (type) {
                        case 0:
                            this.currentLocals = nLocal;
                            this.stackMap.putByte(255).putShort(delta).putShort(nLocal);

                            for (i = 0; i < nLocal; ++i) {
                                this.writeFrameType(local[i]);
                            }

                            this.stackMap.putShort(nStack);
                            i = 0;

                            while (true) {
                                if (i >= nStack) {
                                    break label95;
                                }

                                this.writeFrameType(stack[i]);
                                ++i;
                            }
                        case 1:
                            this.currentLocals += nLocal;
                            this.stackMap.putByte(251 + nLocal).putShort(delta);
                            i = 0;

                            while (true) {
                                if (i >= nLocal) {
                                    break label95;
                                }

                                this.writeFrameType(local[i]);
                                ++i;
                            }
                        case 2:
                            this.currentLocals -= nLocal;
                            this.stackMap.putByte(251 - nLocal).putShort(delta);
                            break;
                        case 3:
                            if (delta < 64) {
                                this.stackMap.putByte(delta);
                            } else {
                                this.stackMap.putByte(251).putShort(delta);
                            }
                            break;
                        case 4:
                            if (delta < 64) {
                                this.stackMap.putByte(64 + delta);
                            } else {
                                this.stackMap.putByte(247).putShort(delta);
                            }

                            this.writeFrameType(stack[0]);
                    }

                    this.previousFrameOffset = this.code.length;
                    ++this.frameCount;
                }
            }

            this.maxStack = Math.max(this.maxStack, nStack);
            this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
        }
    }

    public void visitInsn(int opcode) {
        this.lastCodeOffset = this.code.length;
        this.code.putByte(opcode);
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                int size = this.stackSize + Frame.SIZE[opcode];
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }

                this.stackSize = size;
            } else {
                this.currentBlock.frame.execute(opcode, 0, (ClassWriter) null, (Item) null);
            }

            if (opcode >= 172 && opcode <= 177 || opcode == 191) {
                this.noSuccessor();
            }
        }

    }

    public void visitIntInsn(int opcode, int operand) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                if (opcode != 188) {
                    int size = this.stackSize + 1;
                    if (size > this.maxStackSize) {
                        this.maxStackSize = size;
                    }

                    this.stackSize = size;
                }
            } else {
                this.currentBlock.frame.execute(opcode, operand, (ClassWriter) null, (Item) null);
            }
        }

        if (opcode == 17) {
            this.code.put12(opcode, operand);
        } else {
            this.code.put11(opcode, operand);
        }

    }

    public void visitVarInsn(int opcode, int var) {
        this.lastCodeOffset = this.code.length;
        int opt;
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                if (opcode == 169) {
                    this.currentBlock.status |= 256;
                    this.currentBlock.inputStackTop = this.stackSize;
                    this.noSuccessor();
                } else {
                    opt = this.stackSize + Frame.SIZE[opcode];
                    if (opt > this.maxStackSize) {
                        this.maxStackSize = opt;
                    }

                    this.stackSize = opt;
                }
            } else {
                this.currentBlock.frame.execute(opcode, var, (ClassWriter) null, (Item) null);
            }
        }

        if (this.compute != 3) {
            if (opcode != 22 && opcode != 24 && opcode != 55 && opcode != 57) {
                opt = var + 1;
            } else {
                opt = var + 2;
            }

            if (opt > this.maxLocals) {
                this.maxLocals = opt;
            }
        }

        if (var < 4 && opcode != 169) {
            if (opcode < 54) {
                opt = 26 + (opcode - 21 << 2) + var;
            } else {
                opt = 59 + (opcode - 54 << 2) + var;
            }

            this.code.putByte(opt);
        } else if (var >= 256) {
            this.code.putByte(196).put12(opcode, var);
        } else {
            this.code.put11(opcode, var);
        }

        if (opcode >= 54 && this.compute == 0 && this.handlerCount > 0) {
            this.visitLabel(new Label());
        }

    }

    public void visitTypeInsn(int opcode, String type) {
        this.lastCodeOffset = this.code.length;
        Item i = this.cw.newClassItem(type);
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                if (opcode == 187) {
                    int size = this.stackSize + 1;
                    if (size > this.maxStackSize) {
                        this.maxStackSize = size;
                    }

                    this.stackSize = size;
                }
            } else {
                this.currentBlock.frame.execute(opcode, this.code.length, this.cw, i);
            }
        }

        this.code.put12(opcode, i.index);
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        this.lastCodeOffset = this.code.length;
        Item i = this.cw.newFieldItem(owner, name, desc);
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                char c = desc.charAt(0);
                int size;
                switch (opcode) {
                    case 178:
                        size = this.stackSize + (c != 68 && c != 74 ? 1 : 2);
                        break;
                    case 179:
                        size = this.stackSize + (c != 68 && c != 74 ? -1 : -2);
                        break;
                    case 180:
                        size = this.stackSize + (c != 68 && c != 74 ? 0 : 1);
                        break;
                    default:
                        size = this.stackSize + (c != 68 && c != 74 ? -2 : -3);
                }

                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }

                this.stackSize = size;
            } else {
                this.currentBlock.frame.execute(opcode, 0, this.cw, i);
            }
        }

        this.code.put12(opcode, i.index);
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        this.lastCodeOffset = this.code.length;
        Item i = this.cw.newMethodItem(owner, name, desc, itf);
        int argSize = i.intVal;
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                if (argSize == 0) {
                    argSize = Type.getArgumentsAndReturnSizes(desc);
                    i.intVal = argSize;
                }

                int size;
                if (opcode == 184) {
                    size = this.stackSize - (argSize >> 2) + (argSize & 3) + 1;
                } else {
                    size = this.stackSize - (argSize >> 2) + (argSize & 3);
                }

                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }

                this.stackSize = size;
            } else {
                this.currentBlock.frame.execute(opcode, 0, this.cw, i);
            }
        }

        if (opcode == 185) {
            if (argSize == 0) {
                argSize = Type.getArgumentsAndReturnSizes(desc);
                i.intVal = argSize;
            }

            this.code.put12(185, i.index).put11(argSize >> 2, 0);
        } else {
            this.code.put12(opcode, i.index);
        }

    }

    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        this.lastCodeOffset = this.code.length;
        Item i = this.cw.newInvokeDynamicItem(name, desc, bsm, bsmArgs);
        int argSize = i.intVal;
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                if (argSize == 0) {
                    argSize = Type.getArgumentsAndReturnSizes(desc);
                    i.intVal = argSize;
                }

                int size = this.stackSize - (argSize >> 2) + (argSize & 3) + 1;
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }

                this.stackSize = size;
            } else {
                this.currentBlock.frame.execute(186, 0, this.cw, i);
            }
        }

        this.code.put12(186, i.index);
        this.code.putShort(0);
    }

    public void visitJumpInsn(int opcode, Label label) {
        boolean isWide = opcode >= 200;
        opcode = isWide ? opcode - 33 : opcode;
        this.lastCodeOffset = this.code.length;
        Label nextInsn = null;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(opcode, 0, (ClassWriter) null, (Item) null);
                Label var10000 = label.getFirst();
                var10000.status |= 16;
                this.addSuccessor(0, label);
                if (opcode != 167) {
                    nextInsn = new Label();
                }
            } else if (this.compute == 1) {
                this.currentBlock.frame.execute(opcode, 0, (ClassWriter) null, (Item) null);
            } else if (opcode == 168) {
                if ((label.status & 512) == 0) {
                    label.status |= 512;
                    ++this.subroutines;
                }

                this.currentBlock.status |= 128;
                this.addSuccessor(this.stackSize + 1, label);
                nextInsn = new Label();
            } else {
                this.stackSize += Frame.SIZE[opcode];
                this.addSuccessor(this.stackSize, label);
            }
        }

        if ((label.status & 2) != 0 && label.position - this.code.length < -32768) {
            if (opcode == 167) {
                this.code.putByte(200);
            } else if (opcode == 168) {
                this.code.putByte(201);
            } else {
                if (nextInsn != null) {
                    nextInsn.status |= 16;
                }

                this.code.putByte(opcode <= 166 ? (opcode + 1 ^ 1) - 1 : opcode ^ 1);
                this.code.putShort(8);
                this.code.putByte(200);
            }

            label.put(this, this.code, this.code.length - 1, true);
        } else if (isWide) {
            this.code.putByte(opcode + 33);
            label.put(this, this.code, this.code.length - 1, true);
        } else {
            this.code.putByte(opcode);
            label.put(this, this.code, this.code.length - 1, false);
        }

        if (this.currentBlock != null) {
            if (nextInsn != null) {
                this.visitLabel(nextInsn);
            }

            if (opcode == 167) {
                this.noSuccessor();
            }
        }

    }

    public void visitLabel(Label label) {
        this.cw.hasAsmInsns |= label.resolve(this, this.code.length, this.code.data);
        if ((label.status & 1) == 0) {
            if (this.compute == 0) {
                if (this.currentBlock != null) {
                    if (label.position == this.currentBlock.position) {
                        this.currentBlock.status |= label.status & 16;
                        label.frame = this.currentBlock.frame;
                        return;
                    }

                    this.addSuccessor(0, label);
                }

                this.currentBlock = label;
                if (label.frame == null) {
                    label.frame = new Frame();
                    label.frame.owner = label;
                }

                if (this.previousBlock != null) {
                    if (label.position == this.previousBlock.position) {
                        this.previousBlock.status |= label.status & 16;
                        label.frame = this.previousBlock.frame;
                        this.currentBlock = this.previousBlock;
                        return;
                    }

                    this.previousBlock.successor = label;
                }

                this.previousBlock = label;
            } else if (this.compute == 1) {
                if (this.currentBlock == null) {
                    this.currentBlock = label;
                } else {
                    this.currentBlock.frame.owner = label;
                }
            } else if (this.compute == 2) {
                if (this.currentBlock != null) {
                    this.currentBlock.outputStackMax = this.maxStackSize;
                    this.addSuccessor(this.stackSize, label);
                }

                this.currentBlock = label;
                this.stackSize = 0;
                this.maxStackSize = 0;
                if (this.previousBlock != null) {
                    this.previousBlock.successor = label;
                }

                this.previousBlock = label;
            }

        }
    }

    public void visitLdcInsn(Object cst) {
        this.lastCodeOffset = this.code.length;
        Item i = this.cw.newConstItem(cst);
        int index;
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                if (i.type != 5 && i.type != 6) {
                    index = this.stackSize + 1;
                } else {
                    index = this.stackSize + 2;
                }

                if (index > this.maxStackSize) {
                    this.maxStackSize = index;
                }

                this.stackSize = index;
            } else {
                this.currentBlock.frame.execute(18, 0, this.cw, i);
            }
        }

        index = i.index;
        if (i.type != 5 && i.type != 6) {
            if (index >= 256) {
                this.code.put12(19, index);
            } else {
                this.code.put11(18, index);
            }
        } else {
            this.code.put12(20, index);
        }

    }

    public void visitIincInsn(int var, int increment) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null && (this.compute == 0 || this.compute == 1)) {
            this.currentBlock.frame.execute(132, var, (ClassWriter) null, (Item) null);
        }

        if (this.compute != 3) {
            int n = var + 1;
            if (n > this.maxLocals) {
                this.maxLocals = n;
            }
        }

        if (var <= 255 && increment <= 127 && increment >= -128) {
            this.code.putByte(132).put11(var, increment);
        } else {
            this.code.putByte(196).put12(132, var).putShort(increment);
        }

    }

    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        this.lastCodeOffset = this.code.length;
        int source = this.code.length;
        this.code.putByte(170);
        this.code.putByteArray((byte[]) null, 0, (4 - this.code.length % 4) % 4);
        dflt.put(this, this.code, source, true);
        this.code.putInt(min).putInt(max);

        for (int i = 0; i < labels.length; ++i) {
            labels[i].put(this, this.code, source, true);
        }

        this.visitSwitchInsn(dflt, labels);
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.lastCodeOffset = this.code.length;
        int source = this.code.length;
        this.code.putByte(171);
        this.code.putByteArray((byte[]) null, 0, (4 - this.code.length % 4) % 4);
        dflt.put(this, this.code, source, true);
        this.code.putInt(labels.length);

        for (int i = 0; i < labels.length; ++i) {
            this.code.putInt(keys[i]);
            labels[i].put(this, this.code, source, true);
        }

        this.visitSwitchInsn(dflt, labels);
    }

    private void visitSwitchInsn(Label dflt, Label[] labels) {
        if (this.currentBlock != null) {
            int i;
            if (this.compute == 0) {
                this.currentBlock.frame.execute(171, 0, (ClassWriter) null, (Item) null);
                this.addSuccessor(0, dflt);
                Label var10000 = dflt.getFirst();
                var10000.status |= 16;

                for (i = 0; i < labels.length; ++i) {
                    this.addSuccessor(0, labels[i]);
                    var10000 = labels[i].getFirst();
                    var10000.status |= 16;
                }
            } else {
                --this.stackSize;
                this.addSuccessor(this.stackSize, dflt);

                for (i = 0; i < labels.length; ++i) {
                    this.addSuccessor(this.stackSize, labels[i]);
                }
            }

            this.noSuccessor();
        }

    }

    public void visitMultiANewArrayInsn(String desc, int dims) {
        this.lastCodeOffset = this.code.length;
        Item i = this.cw.newClassItem(desc);
        if (this.currentBlock != null) {
            if (this.compute != 0 && this.compute != 1) {
                this.stackSize += 1 - dims;
            } else {
                this.currentBlock.frame.execute(197, dims, this.cw, i);
            }
        }

        this.code.put12(197, i.index).putByte(dims);
    }

    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        typeRef = typeRef & -16776961 | this.lastCodeOffset << 8;
        AnnotationWriter.putTarget(typeRef, typePath, bv);
        bv.putShort(this.cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.ctanns;
            this.ctanns = aw;
        } else {
            aw.next = this.ictanns;
            this.ictanns = aw;
        }

        return aw;
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        ++this.handlerCount;
        Handler h = new Handler();
        h.start = start;
        h.end = end;
        h.handler = handler;
        h.desc = type;
        h.type = type != null ? this.cw.newClass(type) : 0;
        if (this.lastHandler == null) {
            this.firstHandler = h;
        } else {
            this.lastHandler.next = h;
        }

        this.lastHandler = h;
    }

    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        AnnotationWriter.putTarget(typeRef, typePath, bv);
        bv.putShort(this.cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.ctanns;
            this.ctanns = aw;
        } else {
            aw.next = this.ictanns;
            this.ictanns = aw;
        }

        return aw;
    }

    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        if (signature != null) {
            if (this.localVarType == null) {
                this.localVarType = new ByteVector();
            }

            ++this.localVarTypeCount;
            this.localVarType.putShort(start.position).putShort(end.position - start.position).putShort(this.cw.newUTF8(name)).putShort(this.cw.newUTF8(signature)).putShort(index);
        }

        if (this.localVar == null) {
            this.localVar = new ByteVector();
        }

        ++this.localVarCount;
        this.localVar.putShort(start.position).putShort(end.position - start.position).putShort(this.cw.newUTF8(name)).putShort(this.cw.newUTF8(desc)).putShort(index);
        if (this.compute != 3) {
            char c = desc.charAt(0);
            int n = index + (c != 74 && c != 68 ? 1 : 2);
            if (n > this.maxLocals) {
                this.maxLocals = n;
            }
        }

    }

    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        bv.putByte(typeRef >>> 24).putShort(start.length);

        int aw;
        for (aw = 0; aw < start.length; ++aw) {
            bv.putShort(start[aw].position).putShort(end[aw].position - start[aw].position).putShort(index[aw]);
        }

        if (typePath == null) {
            bv.putByte(0);
        } else {
            aw = typePath.b[typePath.offset] * 2 + 1;
            bv.putByteArray(typePath.b, typePath.offset, aw);
        }

        bv.putShort(this.cw.newUTF8(desc)).putShort(0);
        AnnotationWriter var10 = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
        if (visible) {
            var10.next = this.ctanns;
            this.ctanns = var10;
        } else {
            var10.next = this.ictanns;
            this.ictanns = var10;
        }

        return var10;
    }

    public void visitLineNumber(int line, Label start) {
        if (this.lineNumber == null) {
            this.lineNumber = new ByteVector();
        }

        ++this.lineNumberCount;
        this.lineNumber.putShort(start.position);
        this.lineNumber.putShort(line);
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        Handler handler;
        Label max;
        Label stack;
        Label l;
        int blockMax;
        Edge b;
        Label var15;
        if (this.compute == 0) {
            for (handler = this.firstHandler; handler != null; handler = handler.next) {
                max = handler.start.getFirst();
                stack = handler.handler.getFirst();
                l = handler.end.getFirst();
                String start = handler.desc == null ? "java/lang/Throwable" : handler.desc;
                blockMax = 24117248 | this.cw.addType(start);

                for (stack.status |= 16; max != l; max = max.successor) {
                    b = new Edge();
                    b.info = blockMax;
                    b.successor = stack;
                    b.next = max.successors;
                    max.successors = b;
                }
            }

            Frame var12 = this.labels.frame;
            var12.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), this.maxLocals);
            this.visitFrame(var12);
            int var13 = 0;
            l = this.labels;

            while (l != null) {
                var15 = l;
                l = l.next;
                var15.next = null;
                var12 = var15.frame;
                if ((var15.status & 16) != 0) {
                    var15.status |= 32;
                }

                var15.status |= 64;
                blockMax = var12.inputStack.length + var15.outputStackMax;
                if (blockMax > var13) {
                    var13 = blockMax;
                }

                for (b = var15.successors; b != null; b = b.next) {
                    Label end = b.successor.getFirst();
                    boolean frameIndex = var12.merge(this.cw, end.frame, b.info);
                    if (frameIndex && end.next == null) {
                        end.next = l;
                        l = end;
                    }
                }
            }

            for (var15 = this.labels; var15 != null; var15 = var15.successor) {
                var12 = var15.frame;
                if ((var15.status & 32) != 0) {
                    this.visitFrame(var12);
                }

                if ((var15.status & 64) == 0) {
                    Label var17 = var15.successor;
                    int var19 = var15.position;
                    int var20 = (var17 == null ? this.code.length : var17.position) - 1;
                    if (var20 >= var19) {
                        var13 = Math.max(var13, 1);

                        int var21;
                        for (var21 = var19; var21 < var20; ++var21) {
                            this.code.data[var21] = 0;
                        }

                        this.code.data[var20] = -65;
                        var21 = this.startFrame(var19, 0, 1);
                        this.frame[var21] = 24117248 | this.cw.addType("java/lang/Throwable");
                        this.endFrame();
                        this.firstHandler = Handler.remove(this.firstHandler, var15, var17);
                    }
                }
            }

            handler = this.firstHandler;

            for (this.handlerCount = 0; handler != null; handler = handler.next) {
                ++this.handlerCount;
            }

            this.maxStack = var13;
        } else if (this.compute == 2) {
            for (handler = this.firstHandler; handler != null; handler = handler.next) {
                max = handler.start;
                stack = handler.handler;

                for (l = handler.end; max != l; max = max.successor) {
                    Edge var16 = new Edge();
                    var16.info = Integer.MAX_VALUE;
                    var16.successor = stack;
                    if ((max.status & 128) == 0) {
                        var16.next = max.successors;
                        max.successors = var16;
                    } else {
                        var16.next = max.successors.next.next;
                        max.successors.next.next = var16;
                    }
                }
            }

            int var14;
            if (this.subroutines > 0) {
                var14 = 0;
                this.labels.visitSubroutine((Label) null, 1L, this.subroutines);

                for (stack = this.labels; stack != null; stack = stack.successor) {
                    if ((stack.status & 128) != 0) {
                        l = stack.successors.next.successor;
                        if ((l.status & 1024) == 0) {
                            ++var14;
                            l.visitSubroutine((Label) null, (long) var14 / 32L << 32 | 1L << var14 % 32, this.subroutines);
                        }
                    }
                }

                for (stack = this.labels; stack != null; stack = stack.successor) {
                    if ((stack.status & 128) != 0) {
                        for (l = this.labels; l != null; l = l.successor) {
                            l.status &= -2049;
                        }

                        var15 = stack.successors.next.successor;
                        var15.visitSubroutine(stack, 0L, this.subroutines);
                    }
                }
            }

            var14 = 0;
            stack = this.labels;

            while (stack != null) {
                l = stack;
                stack = stack.next;
                int var18 = l.inputStackTop;
                blockMax = var18 + l.outputStackMax;
                if (blockMax > var14) {
                    var14 = blockMax;
                }

                b = l.successors;
                if ((l.status & 128) != 0) {
                    b = b.next;
                }

                for (; b != null; b = b.next) {
                    l = b.successor;
                    if ((l.status & 8) == 0) {
                        l.inputStackTop = b.info == Integer.MAX_VALUE ? 1 : var18 + b.info;
                        l.status |= 8;
                        l.next = stack;
                        stack = l;
                    }
                }
            }

            this.maxStack = Math.max(maxStack, var14);
        } else {
            this.maxStack = maxStack;
            this.maxLocals = maxLocals;
        }

    }

    public void visitEnd() {
    }

    private void addSuccessor(int info, Label successor) {
        Edge b = new Edge();
        b.info = info;
        b.successor = successor;
        b.next = this.currentBlock.successors;
        this.currentBlock.successors = b;
    }

    private void noSuccessor() {
        if (this.compute == 0) {
            Label l = new Label();
            l.frame = new Frame();
            l.frame.owner = l;
            l.resolve(this, this.code.length, this.code.data);
            this.previousBlock.successor = l;
            this.previousBlock = l;
        } else {
            this.currentBlock.outputStackMax = this.maxStackSize;
        }

        if (this.compute != 1) {
            this.currentBlock = null;
        }

    }

    private void visitFrame(Frame f) {
        int nTop = 0;
        int nLocal = 0;
        int nStack = 0;
        int[] locals = f.inputLocals;
        int[] stacks = f.inputStack;

        int i;
        int t;
        for (i = 0; i < locals.length; ++i) {
            t = locals[i];
            if (t == 16777216) {
                ++nTop;
            } else {
                nLocal += nTop + 1;
                nTop = 0;
            }

            if (t == 16777220 || t == 16777219) {
                ++i;
            }
        }

        for (i = 0; i < stacks.length; ++i) {
            t = stacks[i];
            ++nStack;
            if (t == 16777220 || t == 16777219) {
                ++i;
            }
        }

        int frameIndex = this.startFrame(f.owner.position, nLocal, nStack);

        for (i = 0; nLocal > 0; --nLocal) {
            t = locals[i];
            this.frame[frameIndex++] = t;
            if (t == 16777220 || t == 16777219) {
                ++i;
            }

            ++i;
        }

        for (i = 0; i < stacks.length; ++i) {
            t = stacks[i];
            this.frame[frameIndex++] = t;
            if (t == 16777220 || t == 16777219) {
                ++i;
            }
        }

        this.endFrame();
    }

    private void visitImplicitFirstFrame() {
        int frameIndex = this.startFrame(0, this.descriptor.length() + 1, 0);
        if ((this.access & 8) == 0) {
            if ((this.access & 524288) == 0) {
                this.frame[frameIndex++] = 24117248 | this.cw.addType(this.cw.thisName);
            } else {
                this.frame[frameIndex++] = 6;
            }
        }

        int i = 1;

        while (true) {
            int j = i;
            switch (this.descriptor.charAt(i++)) {
                case 66:
                case 67:
                case 73:
                case 83:
                case 90:
                    this.frame[frameIndex++] = 1;
                    break;
                case 68:
                    this.frame[frameIndex++] = 3;
                    break;
                case 69:
                case 71:
                case 72:
                case 75:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                default:
                    this.frame[1] = frameIndex - 3;
                    this.endFrame();
                    return;
                case 70:
                    this.frame[frameIndex++] = 2;
                    break;
                case 74:
                    this.frame[frameIndex++] = 4;
                    break;
                case 76:
                    while (this.descriptor.charAt(i) != 59) {
                        ++i;
                    }

                    this.frame[frameIndex++] = 24117248 | this.cw.addType(this.descriptor.substring(j + 1, i++));
                    break;
                case 91:
                    while (this.descriptor.charAt(i) == 91) {
                        ++i;
                    }

                    if (this.descriptor.charAt(i) == 76) {
                        ++i;

                        while (this.descriptor.charAt(i) != 59) {
                            ++i;
                        }
                    }

                    int var10001 = frameIndex++;
                    ++i;
                    this.frame[var10001] = 24117248 | this.cw.addType(this.descriptor.substring(j, i));
            }
        }
    }

    private int startFrame(int offset, int nLocal, int nStack) {
        int n = 3 + nLocal + nStack;
        if (this.frame == null || this.frame.length < n) {
            this.frame = new int[n];
        }

        this.frame[0] = offset;
        this.frame[1] = nLocal;
        this.frame[2] = nStack;
        return 3;
    }

    private void endFrame() {
        if (this.previousFrame != null) {
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
            }

            this.writeFrame();
            ++this.frameCount;
        }

        this.previousFrame = this.frame;
        this.frame = null;
    }

    private void writeFrame() {
        int clocalsSize = this.frame[1];
        int cstackSize = this.frame[2];
        if ((this.cw.version & '\uffff') < 50) {
            this.stackMap.putShort(this.frame[0]).putShort(clocalsSize);
            this.writeFrameTypes(3, 3 + clocalsSize);
            this.stackMap.putShort(cstackSize);
            this.writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
        } else {
            int localsSize = this.previousFrame[1];
            int type = 255;
            int k = 0;
            int delta;
            if (this.frameCount == 0) {
                delta = this.frame[0];
            } else {
                delta = this.frame[0] - this.previousFrame[0] - 1;
            }

            if (cstackSize == 0) {
                k = clocalsSize - localsSize;
                switch (k) {
                    case -3:
                    case -2:
                    case -1:
                        type = 248;
                        localsSize = clocalsSize;
                        break;
                    case 0:
                        type = delta < 64 ? 0 : 251;
                        break;
                    case 1:
                    case 2:
                    case 3:
                        type = 252;
                }
            } else if (clocalsSize == localsSize && cstackSize == 1) {
                type = delta < 63 ? 64 : 247;
            }

            if (type != 255) {
                int l = 3;

                for (int j = 0; j < localsSize; ++j) {
                    if (this.frame[l] != this.previousFrame[l]) {
                        type = 255;
                        break;
                    }

                    ++l;
                }
            }

            switch (type) {
                case 0:
                    this.stackMap.putByte(delta);
                    break;
                case 64:
                    this.stackMap.putByte(64 + delta);
                    this.writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
                    break;
                case 247:
                    this.stackMap.putByte(247).putShort(delta);
                    this.writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
                    break;
                case 248:
                    this.stackMap.putByte(251 + k).putShort(delta);
                    break;
                case 251:
                    this.stackMap.putByte(251).putShort(delta);
                    break;
                case 252:
                    this.stackMap.putByte(251 + k).putShort(delta);
                    this.writeFrameTypes(3 + localsSize, 3 + clocalsSize);
                    break;
                default:
                    this.stackMap.putByte(255).putShort(delta).putShort(clocalsSize);
                    this.writeFrameTypes(3, 3 + clocalsSize);
                    this.stackMap.putShort(cstackSize);
                    this.writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
            }

        }
    }

    private void writeFrameTypes(int start, int end) {
        for (int i = start; i < end; ++i) {
            int t = this.frame[i];
            int d = t & -268435456;
            if (d == 0) {
                int sb = t & 1048575;
                switch (t & 267386880) {
                    case 24117248:
                        this.stackMap.putByte(7).putShort(this.cw.newClass(this.cw.typeTable[sb].strVal1));
                        break;
                    case 25165824:
                        this.stackMap.putByte(8).putShort(this.cw.typeTable[sb].intVal);
                        break;
                    default:
                        this.stackMap.putByte(sb);
                }
            } else {
                StringBuilder var7 = new StringBuilder();
                d >>= 28;

                while (d-- > 0) {
                    var7.append('[');
                }

                if ((t & 267386880) == 24117248) {
                    var7.append('L');
                    var7.append(this.cw.typeTable[t & 1048575].strVal1);
                    var7.append(';');
                } else {
                    switch (t & 15) {
                        case 1:
                            var7.append('I');
                            break;
                        case 2:
                            var7.append('F');
                            break;
                        case 3:
                            var7.append('D');
                            break;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        default:
                            var7.append('J');
                            break;
                        case 9:
                            var7.append('Z');
                            break;
                        case 10:
                            var7.append('B');
                            break;
                        case 11:
                            var7.append('C');
                            break;
                        case 12:
                            var7.append('S');
                    }
                }

                this.stackMap.putByte(7).putShort(this.cw.newClass(var7.toString()));
            }
        }

    }

    private void writeFrameType(Object type) {
        if (type instanceof String) {
            this.stackMap.putByte(7).putShort(this.cw.newClass((String) type));
        } else if (type instanceof Integer) {
            this.stackMap.putByte(((Integer) type).intValue());
        } else {
            this.stackMap.putByte(8).putShort(((Label) type).position);
        }

    }

    final int getSize() {
        if (this.classReaderOffset != 0) {
            return 6 + this.classReaderLength;
        } else {
            int size = 8;
            if (this.code.length > 0) {
                if (this.code.length > '\uffff') {
                    throw new RuntimeException("Method code too large!");
                }

                this.cw.newUTF8("Code");
                size += 18 + this.code.length + 8 * this.handlerCount;
                if (this.localVar != null) {
                    this.cw.newUTF8("LocalVariableTable");
                    size += 8 + this.localVar.length;
                }

                if (this.localVarType != null) {
                    this.cw.newUTF8("LocalVariableTypeTable");
                    size += 8 + this.localVarType.length;
                }

                if (this.lineNumber != null) {
                    this.cw.newUTF8("LineNumberTable");
                    size += 8 + this.lineNumber.length;
                }

                if (this.stackMap != null) {
                    boolean i = (this.cw.version & '\uffff') >= 50;
                    this.cw.newUTF8(i ? "StackMapTable" : "StackMap");
                    size += 8 + this.stackMap.length;
                }

                if (this.ctanns != null) {
                    this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
                    size += 8 + this.ctanns.getSize();
                }

                if (this.ictanns != null) {
                    this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
                    size += 8 + this.ictanns.getSize();
                }

                if (this.cattrs != null) {
                    size += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
                }
            }

            if (this.exceptionCount > 0) {
                this.cw.newUTF8("Exceptions");
                size += 8 + 2 * this.exceptionCount;
            }

            if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
                this.cw.newUTF8("Synthetic");
                size += 6;
            }

            if ((this.access & 131072) != 0) {
                this.cw.newUTF8("Deprecated");
                size += 6;
            }

            if (this.signature != null) {
                this.cw.newUTF8("Signature");
                this.cw.newUTF8(this.signature);
                size += 8;
            }

            if (this.methodParameters != null) {
                this.cw.newUTF8("MethodParameters");
                size += 7 + this.methodParameters.length;
            }

            if (this.annd != null) {
                this.cw.newUTF8("AnnotationDefault");
                size += 6 + this.annd.length;
            }

            if (this.anns != null) {
                this.cw.newUTF8("RuntimeVisibleAnnotations");
                size += 8 + this.anns.getSize();
            }

            if (this.ianns != null) {
                this.cw.newUTF8("RuntimeInvisibleAnnotations");
                size += 8 + this.ianns.getSize();
            }

            if (this.tanns != null) {
                this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
                size += 8 + this.tanns.getSize();
            }

            if (this.itanns != null) {
                this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
                size += 8 + this.itanns.getSize();
            }

            int var3;
            if (this.panns != null) {
                this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
                size += 7 + 2 * (this.panns.length - this.synthetics);

                for (var3 = this.panns.length - 1; var3 >= this.synthetics; --var3) {
                    size += this.panns[var3] == null ? 0 : this.panns[var3].getSize();
                }
            }

            if (this.ipanns != null) {
                this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
                size += 7 + 2 * (this.ipanns.length - this.synthetics);

                for (var3 = this.ipanns.length - 1; var3 >= this.synthetics; --var3) {
                    size += this.ipanns[var3] == null ? 0 : this.ipanns[var3].getSize();
                }
            }

            if (this.attrs != null) {
                size += this.attrs.getSize(this.cw, (byte[]) null, 0, -1, -1);
            }

            return size;
        }
    }

    final void put(ByteVector out) {
        boolean FACTOR = true;
        int mask = 917504 | (this.access & 262144) / 64;
        out.putShort(this.access & ~mask).putShort(this.name).putShort(this.desc);
        if (this.classReaderOffset != 0) {
            out.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
        } else {
            int attributeCount = 0;
            if (this.code.length > 0) {
                ++attributeCount;
            }

            if (this.exceptionCount > 0) {
                ++attributeCount;
            }

            if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
                ++attributeCount;
            }

            if ((this.access & 131072) != 0) {
                ++attributeCount;
            }

            if (this.signature != null) {
                ++attributeCount;
            }

            if (this.methodParameters != null) {
                ++attributeCount;
            }

            if (this.annd != null) {
                ++attributeCount;
            }

            if (this.anns != null) {
                ++attributeCount;
            }

            if (this.ianns != null) {
                ++attributeCount;
            }

            if (this.tanns != null) {
                ++attributeCount;
            }

            if (this.itanns != null) {
                ++attributeCount;
            }

            if (this.panns != null) {
                ++attributeCount;
            }

            if (this.ipanns != null) {
                ++attributeCount;
            }

            if (this.attrs != null) {
                attributeCount += this.attrs.getCount();
            }

            out.putShort(attributeCount);
            int i;
            if (this.code.length > 0) {
                i = 12 + this.code.length + 8 * this.handlerCount;
                if (this.localVar != null) {
                    i += 8 + this.localVar.length;
                }

                if (this.localVarType != null) {
                    i += 8 + this.localVarType.length;
                }

                if (this.lineNumber != null) {
                    i += 8 + this.lineNumber.length;
                }

                if (this.stackMap != null) {
                    i += 8 + this.stackMap.length;
                }

                if (this.ctanns != null) {
                    i += 8 + this.ctanns.getSize();
                }

                if (this.ictanns != null) {
                    i += 8 + this.ictanns.getSize();
                }

                if (this.cattrs != null) {
                    i += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
                }

                out.putShort(this.cw.newUTF8("Code")).putInt(i);
                out.putShort(this.maxStack).putShort(this.maxLocals);
                out.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
                out.putShort(this.handlerCount);
                if (this.handlerCount > 0) {
                    for (Handler zip = this.firstHandler; zip != null; zip = zip.next) {
                        out.putShort(zip.start.position).putShort(zip.end.position).putShort(zip.handler.position).putShort(zip.type);
                    }
                }

                attributeCount = 0;
                if (this.localVar != null) {
                    ++attributeCount;
                }

                if (this.localVarType != null) {
                    ++attributeCount;
                }

                if (this.lineNumber != null) {
                    ++attributeCount;
                }

                if (this.stackMap != null) {
                    ++attributeCount;
                }

                if (this.ctanns != null) {
                    ++attributeCount;
                }

                if (this.ictanns != null) {
                    ++attributeCount;
                }

                if (this.cattrs != null) {
                    attributeCount += this.cattrs.getCount();
                }

                out.putShort(attributeCount);
                if (this.localVar != null) {
                    out.putShort(this.cw.newUTF8("LocalVariableTable"));
                    out.putInt(this.localVar.length + 2).putShort(this.localVarCount);
                    out.putByteArray(this.localVar.data, 0, this.localVar.length);
                }

                if (this.localVarType != null) {
                    out.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
                    out.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
                    out.putByteArray(this.localVarType.data, 0, this.localVarType.length);
                }

                if (this.lineNumber != null) {
                    out.putShort(this.cw.newUTF8("LineNumberTable"));
                    out.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
                    out.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
                }

                if (this.stackMap != null) {
                    boolean var7 = (this.cw.version & '\uffff') >= 50;
                    out.putShort(this.cw.newUTF8(var7 ? "StackMapTable" : "StackMap"));
                    out.putInt(this.stackMap.length + 2).putShort(this.frameCount);
                    out.putByteArray(this.stackMap.data, 0, this.stackMap.length);
                }

                if (this.ctanns != null) {
                    out.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
                    this.ctanns.put(out);
                }

                if (this.ictanns != null) {
                    out.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
                    this.ictanns.put(out);
                }

                if (this.cattrs != null) {
                    this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, out);
                }
            }

            if (this.exceptionCount > 0) {
                out.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
                out.putShort(this.exceptionCount);

                for (i = 0; i < this.exceptionCount; ++i) {
                    out.putShort(this.exceptions[i]);
                }
            }

            if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
                out.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
            }

            if ((this.access & 131072) != 0) {
                out.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
            }

            if (this.signature != null) {
                out.putShort(this.cw.newUTF8("Signature")).putInt(2).putShort(this.cw.newUTF8(this.signature));
            }

            if (this.methodParameters != null) {
                out.putShort(this.cw.newUTF8("MethodParameters"));
                out.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
                out.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
            }

            if (this.annd != null) {
                out.putShort(this.cw.newUTF8("AnnotationDefault"));
                out.putInt(this.annd.length);
                out.putByteArray(this.annd.data, 0, this.annd.length);
            }

            if (this.anns != null) {
                out.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
                this.anns.put(out);
            }

            if (this.ianns != null) {
                out.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
                this.ianns.put(out);
            }

            if (this.tanns != null) {
                out.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
                this.tanns.put(out);
            }

            if (this.itanns != null) {
                out.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.itanns.put(out);
            }

            if (this.panns != null) {
                out.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
                AnnotationWriter.put(this.panns, this.synthetics, out);
            }

            if (this.ipanns != null) {
                out.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
                AnnotationWriter.put(this.ipanns, this.synthetics, out);
            }

            if (this.attrs != null) {
                this.attrs.put(this.cw, (byte[]) null, 0, -1, -1, out);
            }

        }
    }
}
