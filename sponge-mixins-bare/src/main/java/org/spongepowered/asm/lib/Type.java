package org.spongepowered.asm.lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {

    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final int METHOD = 11;
    public static final Type VOID_TYPE = new Type(0, (char[]) null, 1443168256, 1);
    public static final Type BOOLEAN_TYPE = new Type(1, (char[]) null, 1509950721, 1);
    public static final Type CHAR_TYPE = new Type(2, (char[]) null, 1124075009, 1);
    public static final Type BYTE_TYPE = new Type(3, (char[]) null, 1107297537, 1);
    public static final Type SHORT_TYPE = new Type(4, (char[]) null, 1392510721, 1);
    public static final Type INT_TYPE = new Type(5, (char[]) null, 1224736769, 1);
    public static final Type FLOAT_TYPE = new Type(6, (char[]) null, 1174536705, 1);
    public static final Type LONG_TYPE = new Type(7, (char[]) null, 1241579778, 1);
    public static final Type DOUBLE_TYPE = new Type(8, (char[]) null, 1141048066, 1);
    private final int sort;
    private final char[] buf;
    private final int off;
    private final int len;


    private Type(int sort, char[] buf, int off, int len) {
        this.sort = sort;
        this.buf = buf;
        this.off = off;
        this.len = len;
    }

    public static Type getType(String typeDescriptor) {
        return getType(typeDescriptor.toCharArray(), 0);
    }

    public static Type getObjectType(String internalName) {
        char[] buf = internalName.toCharArray();
        return new Type(buf[0] == 91 ? 9 : 10, buf, 0, buf.length);
    }

    public static Type getMethodType(String methodDescriptor) {
        return getType(methodDescriptor.toCharArray(), 0);
    }

    public static Type getMethodType(Type returnType, Type... argumentTypes) {
        return getType(getMethodDescriptor(returnType, argumentTypes));
    }

    public static Type getType(Class c) {
        return c.isPrimitive() ? (c == Integer.TYPE ? INT_TYPE : (c == Void.TYPE ? VOID_TYPE : (c == Boolean.TYPE ? BOOLEAN_TYPE : (c == Byte.TYPE ? BYTE_TYPE : (c == Character.TYPE ? CHAR_TYPE : (c == Short.TYPE ? SHORT_TYPE : (c == Double.TYPE ? DOUBLE_TYPE : (c == Float.TYPE ? FLOAT_TYPE : LONG_TYPE)))))))) : getType(getDescriptor(c));
    }

    public static Type getType(Constructor c) {
        return getType(getConstructorDescriptor(c));
    }

    public static Type getType(Method m) {
        return getType(getMethodDescriptor(m));
    }

    public static Type[] getArgumentTypes(String methodDescriptor) {
        char[] buf = methodDescriptor.toCharArray();
        int off = 1;
        int size = 0;

        while (true) {
            char args = buf[off++];
            if (args == 41) {
                Type[] var5 = new Type[size];
                off = 1;

                for (size = 0; buf[off] != 41; ++size) {
                    var5[size] = getType(buf, off);
                    off += var5[size].len + (var5[size].sort == 10 ? 2 : 0);
                }

                return var5;
            }

            if (args == 76) {
                while (buf[off++] != 59) {
                    ;
                }

                ++size;
            } else if (args != 91) {
                ++size;
            }
        }
    }

    public static Type[] getArgumentTypes(Method method) {
        Class[] classes = method.getParameterTypes();
        Type[] types = new Type[classes.length];

        for (int i = classes.length - 1; i >= 0; --i) {
            types[i] = getType(classes[i]);
        }

        return types;
    }

    public static Type getReturnType(String methodDescriptor) {
        char[] buf = methodDescriptor.toCharArray();
        int off = 1;

        while (true) {
            char car = buf[off++];
            if (car == ')') {
                return getType(buf, off);
            }
        }
    }

    public static Type getReturnType(Method method) {
        return getType(method.getReturnType());
    }

    public static int getArgumentsAndReturnSizes(String desc) {
        int n = 1;
        int c = 1;

        while (true) {
            char car = desc.charAt(c++);
            if (car == 41) {
                car = desc.charAt(c);
                return n << 2 | (car == 86 ? 0 : (car != 68 && car != 74 ? 1 : 2));
            }

            if (car == 76) {
                while (desc.charAt(c++) != 59) {
                    ;
                }

                ++n;
            } else if (car != 91) {
                if (car != 68 && car != 74) {
                    ++n;
                } else {
                    n += 2;
                }
            } else {
                while ((car = desc.charAt(c)) == 91) {
                    ++c;
                }

                if (car == 68 || car == 74) {
                    --n;
                }
            }
        }
    }

    private static Type getType(char[] buf, int off) {
        int len;
        switch (buf[off]) {
            case 66:
                return BYTE_TYPE;
            case 67:
                return CHAR_TYPE;
            case 68:
                return DOUBLE_TYPE;
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
            case 87:
            case 88:
            case 89:
            default:
                return new Type(11, buf, off, buf.length - off);
            case 70:
                return FLOAT_TYPE;
            case 73:
                return INT_TYPE;
            case 74:
                return LONG_TYPE;
            case 76:
                for (len = 1; buf[off + len] != 59; ++len) {
                    ;
                }

                return new Type(10, buf, off + 1, len - 1);
            case 83:
                return SHORT_TYPE;
            case 86:
                return VOID_TYPE;
            case 90:
                return BOOLEAN_TYPE;
            case 91:
                for (len = 1; buf[off + len] == 91; ++len) {
                    ;
                }

                if (buf[off + len] == 76) {
                    ++len;

                    while (buf[off + len] != 59) {
                        ++len;
                    }
                }

                return new Type(9, buf, off, len + 1);
        }
    }

    public static String getMethodDescriptor(Type returnType, Type... argumentTypes) {
        StringBuilder buf = new StringBuilder();
        buf.append('(');

        for (int i = 0; i < argumentTypes.length; ++i) {
            argumentTypes[i].getDescriptor(buf);
        }

        buf.append(')');
        returnType.getDescriptor(buf);
        return buf.toString();
    }

    public static String getInternalName(Class c) {
        return c.getName().replace('.', '/');
    }

    public static String getDescriptor(Class c) {
        StringBuilder buf = new StringBuilder();
        getDescriptor(buf, c);
        return buf.toString();
    }

    public static String getConstructorDescriptor(Constructor c) {
        Class[] parameters = c.getParameterTypes();
        StringBuilder buf = new StringBuilder();
        buf.append('(');

        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }

        return buf.append(")V").toString();
    }

    public static String getMethodDescriptor(Method m) {
        Class[] parameters = m.getParameterTypes();
        StringBuilder buf = new StringBuilder();
        buf.append('(');

        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }

        buf.append(')');
        getDescriptor(buf, m.getReturnType());
        return buf.toString();
    }

    private static void getDescriptor(StringBuilder buf, Class c) {
        Class d;
        for (d = c; !d.isPrimitive(); d = d.getComponentType()) {
            if (!d.isArray()) {
                buf.append('L');
                String name = d.getName();
                int len = name.length();

                for (int i = 0; i < len; ++i) {
                    char car = name.charAt(i);
                    buf.append(car == 46 ? '/' : car);
                }

                buf.append(';');
                return;
            }

            buf.append('[');
        }

        char var7;
        if (d == Integer.TYPE) {
            var7 = 73;
        } else if (d == Void.TYPE) {
            var7 = 86;
        } else if (d == Boolean.TYPE) {
            var7 = 90;
        } else if (d == Byte.TYPE) {
            var7 = 66;
        } else if (d == Character.TYPE) {
            var7 = 67;
        } else if (d == Short.TYPE) {
            var7 = 83;
        } else if (d == Double.TYPE) {
            var7 = 68;
        } else if (d == Float.TYPE) {
            var7 = 70;
        } else {
            var7 = 74;
        }

        buf.append(var7);
    }

    public int getSort() {
        return this.sort;
    }

    public int getDimensions() {
        int i;
        for (i = 1; this.buf[this.off + i] == 91; ++i) {
            ;
        }

        return i;
    }

    public Type getElementType() {
        return getType(this.buf, this.off + this.getDimensions());
    }

    public String getClassName() {
        switch (this.sort) {
            case 0:
                return "void";
            case 1:
                return "boolean";
            case 2:
                return "char";
            case 3:
                return "byte";
            case 4:
                return "short";
            case 5:
                return "int";
            case 6:
                return "float";
            case 7:
                return "long";
            case 8:
                return "double";
            case 9:
                StringBuilder sb = new StringBuilder(this.getElementType().getClassName());

                for (int i = this.getDimensions(); i > 0; --i) {
                    sb.append("[]");
                }

                return sb.toString();
            case 10:
                return (new String(this.buf, this.off, this.len)).replace('/', '.');
            default:
                return null;
        }
    }

    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }

    public Type[] getArgumentTypes() {
        return getArgumentTypes(this.getDescriptor());
    }

    public Type getReturnType() {
        return getReturnType(this.getDescriptor());
    }

    public int getArgumentsAndReturnSizes() {
        return getArgumentsAndReturnSizes(this.getDescriptor());
    }

    public String getDescriptor() {
        StringBuilder buf = new StringBuilder();
        this.getDescriptor(buf);
        return buf.toString();
    }

    private void getDescriptor(StringBuilder buf) {
        if (this.buf == null) {
            buf.append((char) ((this.off & -16777216) >>> 24));
        } else if (this.sort == 10) {
            buf.append('L');
            buf.append(this.buf, this.off, this.len);
            buf.append(';');
        } else {
            buf.append(this.buf, this.off, this.len);
        }

    }

    public int getSize() {
        return this.buf == null ? this.off & 255 : 1;
    }

    public int getOpcode(int opcode) {
        return opcode != 46 && opcode != 79 ? opcode + (this.buf == null ? (this.off & 16711680) >> 16 : 4) : opcode + (this.buf == null ? (this.off & '\uff00') >> 8 : 4);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Type)) {
            return false;
        } else {
            Type t = (Type) o;
            if (this.sort != t.sort) {
                return false;
            } else {
                if (this.sort >= 9) {
                    if (this.len != t.len) {
                        return false;
                    }

                    int i = this.off;
                    int j = t.off;

                    for (int end = i + this.len; i < end; ++j) {
                        if (this.buf[i] != t.buf[j]) {
                            return false;
                        }

                        ++i;
                    }
                }

                return true;
            }
        }
    }

    public int hashCode() {
        int hc = 13 * this.sort;
        if (this.sort >= 9) {
            int i = this.off;

            for (int end = i + this.len; i < end; ++i) {
                hc = 17 * (hc + this.buf[i]);
            }
        }

        return hc;
    }

    public String toString() {
        return this.getDescriptor();
    }

}
