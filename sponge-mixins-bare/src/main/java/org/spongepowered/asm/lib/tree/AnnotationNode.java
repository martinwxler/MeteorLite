package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.AnnotationVisitor;

import java.util.ArrayList;
import java.util.List;

public class AnnotationNode extends AnnotationVisitor {

    public String desc;
    public List values;


    public AnnotationNode(String desc) {
        this(327680, desc);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int api, String desc) {
        super(api);
        this.desc = desc;
    }

    AnnotationNode(List values) {
        super(327680);
        this.values = values;
    }

    static void accept(AnnotationVisitor av, String name, Object value) {
        if (av != null) {
            if (value instanceof String[]) {
                String[] v = (String[]) ((String[]) value);
                av.visitEnum(name, v[0], v[1]);
            } else if (value instanceof AnnotationNode) {
                AnnotationNode var6 = (AnnotationNode) value;
                var6.accept(av.visitAnnotation(name, var6.desc));
            } else if (value instanceof List) {
                AnnotationVisitor var7 = av.visitArray(name);
                if (var7 != null) {
                    List array = (List) value;

                    for (int j = 0; j < array.size(); ++j) {
                        accept(var7, (String) null, array.get(j));
                    }

                    var7.visitEnd();
                }
            } else {
                av.visit(name, value);
            }
        }

    }

    public void visit(String name, Object value) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }

        if (this.desc != null) {
            this.values.add(name);
        }

        ArrayList l;
        int var6;
        int var7;
        if (value instanceof byte[]) {
            byte[] v = (byte[]) ((byte[]) value);
            l = new ArrayList(v.length);
            byte[] var5 = v;
            var6 = v.length;

            for (var7 = 0; var7 < var6; ++var7) {
                byte f = var5[var7];
                l.add(Byte.valueOf(f));
            }

            this.values.add(l);
        } else if (value instanceof boolean[]) {
            boolean[] var11 = (boolean[]) ((boolean[]) value);
            l = new ArrayList(var11.length);
            boolean[] var17 = var11;
            var6 = var11.length;

            for (var7 = 0; var7 < var6; ++var7) {
                boolean var25 = var17[var7];
                l.add(Boolean.valueOf(var25));
            }

            this.values.add(l);
        } else if (value instanceof short[]) {
            short[] var12 = (short[]) ((short[]) value);
            l = new ArrayList(var12.length);
            short[] var19 = var12;
            var6 = var12.length;

            for (var7 = 0; var7 < var6; ++var7) {
                short var26 = var19[var7];
                l.add(Short.valueOf(var26));
            }

            this.values.add(l);
        } else if (value instanceof char[]) {
            char[] var13 = (char[]) ((char[]) value);
            l = new ArrayList(var13.length);
            char[] var20 = var13;
            var6 = var13.length;

            for (var7 = 0; var7 < var6; ++var7) {
                char var27 = var20[var7];
                l.add(Character.valueOf(var27));
            }

            this.values.add(l);
        } else if (value instanceof int[]) {
            int[] var14 = (int[]) ((int[]) value);
            l = new ArrayList(var14.length);
            int[] var21 = var14;
            var6 = var14.length;

            for (var7 = 0; var7 < var6; ++var7) {
                int var28 = var21[var7];
                l.add(Integer.valueOf(var28));
            }

            this.values.add(l);
        } else if (value instanceof long[]) {
            long[] var15 = (long[]) ((long[]) value);
            l = new ArrayList(var15.length);
            long[] var22 = var15;
            var6 = var15.length;

            for (var7 = 0; var7 < var6; ++var7) {
                long d = var22[var7];
                l.add(Long.valueOf(d));
            }

            this.values.add(l);
        } else if (value instanceof float[]) {
            float[] var16 = (float[]) ((float[]) value);
            l = new ArrayList(var16.length);
            float[] var23 = var16;
            var6 = var16.length;

            for (var7 = 0; var7 < var6; ++var7) {
                float var30 = var23[var7];
                l.add(Float.valueOf(var30));
            }

            this.values.add(l);
        } else if (value instanceof double[]) {
            double[] var18 = (double[]) ((double[]) value);
            l = new ArrayList(var18.length);
            double[] var24 = var18;
            var6 = var18.length;

            for (var7 = 0; var7 < var6; ++var7) {
                double var29 = var24[var7];
                l.add(Double.valueOf(var29));
            }

            this.values.add(l);
        } else {
            this.values.add(value);
        }

    }

    public void visitEnum(String name, String desc, String value) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }

        if (this.desc != null) {
            this.values.add(name);
        }

        this.values.add(new String[]{desc, value});
    }

    public AnnotationVisitor visitAnnotation(String name, String desc) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }

        if (this.desc != null) {
            this.values.add(name);
        }

        AnnotationNode annotation = new AnnotationNode(desc);
        this.values.add(annotation);
        return annotation;
    }

    public AnnotationVisitor visitArray(String name) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }

        if (this.desc != null) {
            this.values.add(name);
        }

        ArrayList array = new ArrayList();
        this.values.add(array);
        return new AnnotationNode(array);
    }

    public void visitEnd() {
    }

    public void check(int api) {
    }

    public void accept(AnnotationVisitor av) {
        if (av != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    String name = (String) this.values.get(i);
                    Object value = this.values.get(i + 1);
                    accept(av, name, value);
                }
            }

            av.visitEnd();
        }

    }
}
