/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.apple.jobjc;
import com.apple.jobjc.JObjCRuntime.Width;
// Auto generated by PrimitiveCoder.hs
// Do not edit by hand.
public abstract class PrimitiveCoder<T> extends Coder<T>{
    public PrimitiveCoder(int ffiTypeCode, String objCEncoding, Class jclass, Class jprim){
        super(ffiTypeCode, objCEncoding, jclass, jprim);
    }
    public final boolean popBoolean(NativeArgumentBuffer args){
        return popBoolean(args.runtime, args.retValPtr);
    }
    public abstract boolean popBoolean(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, boolean x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, boolean x);


    public final byte popByte(NativeArgumentBuffer args){
        return popByte(args.runtime, args.retValPtr);
    }
    public abstract byte popByte(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, byte x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, byte x);


    public final char popChar(NativeArgumentBuffer args){
        return popChar(args.runtime, args.retValPtr);
    }
    public abstract char popChar(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, char x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, char x);


    public final short popShort(NativeArgumentBuffer args){
        return popShort(args.runtime, args.retValPtr);
    }
    public abstract short popShort(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, short x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, short x);


    public final int popInt(NativeArgumentBuffer args){
        return popInt(args.runtime, args.retValPtr);
    }
    public abstract int popInt(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, int x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, int x);


    public final long popLong(NativeArgumentBuffer args){
        return popLong(args.runtime, args.retValPtr);
    }
    public abstract long popLong(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, long x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, long x);


    public final float popFloat(NativeArgumentBuffer args){
        return popFloat(args.runtime, args.retValPtr);
    }
    public abstract float popFloat(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, float x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, float x);


    public final double popDouble(NativeArgumentBuffer args){
        return popDouble(args.runtime, args.retValPtr);
    }
    public abstract double popDouble(JObjCRuntime runtime, long addr);

    public final void push(NativeArgumentBuffer args, double x){
        push(args.runtime, args.argValuesPtr, x);
        args.didPutArgValue(sizeof());
    }
    public abstract void push(JObjCRuntime runtime, long addr, double x);


// native BOOL -> java boolean
public static final class BoolCoder extends PrimitiveCoder<Boolean>{
    public static final BoolCoder INST = new BoolCoder();
    public BoolCoder(){ super(FFI_SINT8, "B", Boolean.class, boolean.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, boolean x){
        rt.unsafe.putByte(addr, (byte) (x ? 1 : 0));
    }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){
        return rt.unsafe.getByte(addr) != 0;
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 1;
    }
    @Override public void push(JObjCRuntime rt, long addr, Boolean x){ push(rt, addr, (boolean) x); }
    @Override public Boolean pop(JObjCRuntime rt, long addr){ return popBoolean(rt, addr); }
    // proxies for mixed encoding

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, (x != 0)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)(popBoolean(rt, addr) ? 1 : 0)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, (x != 0)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)(popBoolean(rt, addr) ? 1 : 0)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, (x != 0)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)(popBoolean(rt, addr) ? 1 : 0)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, (x != 0)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)(popBoolean(rt, addr) ? 1 : 0)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, (x != 0)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)(popBoolean(rt, addr) ? 1 : 0)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, (x != 0)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)(popBoolean(rt, addr) ? 1 : 0)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, (x != 0)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)(popBoolean(rt, addr) ? 1 : 0)); }


}

// native schar -> java byte
public static final class SCharCoder extends PrimitiveCoder<Byte>{
    public static final SCharCoder INST = new SCharCoder();
    public SCharCoder(){ super(FFI_SINT8, "c", Byte.class, byte.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, byte x){
        rt.unsafe.putByte(addr, x);
    }
    @Override public byte popByte(JObjCRuntime rt, long addr){
        return rt.unsafe.getByte(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 1;
    }
    @Override public void push(JObjCRuntime rt, long addr, Byte x){ push(rt, addr, (byte) x); }
    @Override public Byte pop(JObjCRuntime rt, long addr){ return popByte(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((byte)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popByte(rt, addr) != 0); }


    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((byte)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((byte)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((byte)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((byte)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((byte)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((byte)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popByte(rt, addr)); }


}

// native uchar -> java byte
public static final class UCharCoder extends PrimitiveCoder<Byte>{
    public static final UCharCoder INST = new UCharCoder();
    public UCharCoder(){ super(FFI_UINT8, "C", Byte.class, byte.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, byte x){
        rt.unsafe.putByte(addr, x);
    }
    @Override public byte popByte(JObjCRuntime rt, long addr){
        return rt.unsafe.getByte(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 1;
    }
    @Override public void push(JObjCRuntime rt, long addr, Byte x){ push(rt, addr, (byte) x); }
    @Override public Byte pop(JObjCRuntime rt, long addr){ return popByte(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((byte)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popByte(rt, addr) != 0); }


    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((byte)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((byte)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((byte)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((byte)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((byte)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popByte(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((byte)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popByte(rt, addr)); }


}

// native sshort -> java short
public static final class SShortCoder extends PrimitiveCoder<Short>{
    public static final SShortCoder INST = new SShortCoder();
    public SShortCoder(){ super(FFI_SINT16, "s", Short.class, short.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, short x){
        rt.unsafe.putShort(addr, (short) x);
    }
    @Override public short popShort(JObjCRuntime rt, long addr){
        return rt.unsafe.getShort(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 2;
    }
    @Override public void push(JObjCRuntime rt, long addr, Short x){ push(rt, addr, (short) x); }
    @Override public Short pop(JObjCRuntime rt, long addr){ return popShort(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((short)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popShort(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((short)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((short)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popShort(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((short)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((short)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((short)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((short)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popShort(rt, addr)); }


}

// native ushort -> java short
public static final class UShortCoder extends PrimitiveCoder<Short>{
    public static final UShortCoder INST = new UShortCoder();
    public UShortCoder(){ super(FFI_UINT16, "S", Short.class, short.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, short x){
        rt.unsafe.putShort(addr, (short) x);
    }
    @Override public short popShort(JObjCRuntime rt, long addr){
        return rt.unsafe.getShort(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 2;
    }
    @Override public void push(JObjCRuntime rt, long addr, Short x){ push(rt, addr, (short) x); }
    @Override public Short pop(JObjCRuntime rt, long addr){ return popShort(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((short)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popShort(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((short)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((short)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popShort(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((short)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((short)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((short)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popShort(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((short)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popShort(rt, addr)); }


}

// native sint -> java int
public static final class SIntCoder extends PrimitiveCoder<Integer>{
    public static final SIntCoder INST = new SIntCoder();
    public SIntCoder(){ super(FFI_SINT32, "i", Integer.class, int.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, int x){
        rt.unsafe.putInt(addr, (int) x);
    }
    @Override public int popInt(JObjCRuntime rt, long addr){
        return rt.unsafe.getInt(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 4;
    }
    @Override public void push(JObjCRuntime rt, long addr, Integer x){ push(rt, addr, (int) x); }
    @Override public Integer pop(JObjCRuntime rt, long addr){ return popInt(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((int)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popInt(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((int)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((int)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((int)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popInt(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((int)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((int)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((int)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popInt(rt, addr)); }


}

// native uint -> java int
public static final class UIntCoder extends PrimitiveCoder<Integer>{
    public static final UIntCoder INST = new UIntCoder();
    public UIntCoder(){ super(FFI_UINT32, "I", Integer.class, int.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, int x){
        rt.unsafe.putInt(addr, (int) x);
    }
    @Override public int popInt(JObjCRuntime rt, long addr){
        return rt.unsafe.getInt(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 4;
    }
    @Override public void push(JObjCRuntime rt, long addr, Integer x){ push(rt, addr, (int) x); }
    @Override public Integer pop(JObjCRuntime rt, long addr){ return popInt(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((int)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popInt(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((int)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((int)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((int)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popInt(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((int)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((int)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popInt(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((int)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popInt(rt, addr)); }


}

// native slong -> java long
public static final class SLongCoder extends PrimitiveCoder<Long>{
    public static final SLongCoder INST = new SLongCoder();
    public SLongCoder(){ super((JObjCRuntime.IS64 ? (FFI_SINT64) : (FFI_SINT32)), "l", Long.class, long.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, long x){
        if(JObjCRuntime.IS64){ rt.unsafe.putLong(addr, (long) x); }else{ rt.unsafe.putInt(addr, (int) x); }
    }
    @Override public long popLong(JObjCRuntime rt, long addr){
        return (JObjCRuntime.IS64 ? (rt.unsafe.getLong(addr)) : (rt.unsafe.getInt(addr)));
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        switch(w){
            case W32: return 4;
            case W64: return 8;

        default: return -1;
        }

    }
    @Override public void push(JObjCRuntime rt, long addr, Long x){ push(rt, addr, (long) x); }
    @Override public Long pop(JObjCRuntime rt, long addr){ return popLong(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((long)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popLong(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((long)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((long)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((long)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((long)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popLong(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((long)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((long)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popLong(rt, addr)); }


}

// native ulong -> java long
public static final class ULongCoder extends PrimitiveCoder<Long>{
    public static final ULongCoder INST = new ULongCoder();
    public ULongCoder(){ super((JObjCRuntime.IS64 ? (FFI_UINT64) : (FFI_UINT32)), "L", Long.class, long.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, long x){
        if(JObjCRuntime.IS64){ rt.unsafe.putLong(addr, (long) x); }else{ rt.unsafe.putInt(addr, (int) x); }
    }
    @Override public long popLong(JObjCRuntime rt, long addr){
        return (JObjCRuntime.IS64 ? (rt.unsafe.getLong(addr)) : (rt.unsafe.getInt(addr)));
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        switch(w){
            case W32: return 4;
            case W64: return 8;

        default: return -1;
        }

    }
    @Override public void push(JObjCRuntime rt, long addr, Long x){ push(rt, addr, (long) x); }
    @Override public Long pop(JObjCRuntime rt, long addr){ return popLong(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((long)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popLong(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((long)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((long)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((long)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((long)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popLong(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((long)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((long)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popLong(rt, addr)); }


}

// native slonglong -> java long
public static final class SLongLongCoder extends PrimitiveCoder<Long>{
    public static final SLongLongCoder INST = new SLongLongCoder();
    public SLongLongCoder(){ super(FFI_SINT64, "q", Long.class, long.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, long x){
        rt.unsafe.putLong(addr, (long) x);
    }
    @Override public long popLong(JObjCRuntime rt, long addr){
        return rt.unsafe.getLong(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 8;
    }
    @Override public void push(JObjCRuntime rt, long addr, Long x){ push(rt, addr, (long) x); }
    @Override public Long pop(JObjCRuntime rt, long addr){ return popLong(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((long)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popLong(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((long)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((long)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((long)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((long)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popLong(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((long)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((long)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popLong(rt, addr)); }


}

// native ulonglong -> java long
public static final class ULongLongCoder extends PrimitiveCoder<Long>{
    public static final ULongLongCoder INST = new ULongLongCoder();
    public ULongLongCoder(){ super(FFI_UINT64, "Q", Long.class, long.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, long x){
        rt.unsafe.putLong(addr, (long) x);
    }
    @Override public long popLong(JObjCRuntime rt, long addr){
        return rt.unsafe.getLong(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 8;
    }
    @Override public void push(JObjCRuntime rt, long addr, Long x){ push(rt, addr, (long) x); }
    @Override public Long pop(JObjCRuntime rt, long addr){ return popLong(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((long)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popLong(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((long)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((long)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((long)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((long)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popLong(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((long)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popLong(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((long)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popLong(rt, addr)); }


}

// native float -> java float
public static final class FloatCoder extends PrimitiveCoder<Float>{
    public static final FloatCoder INST = new FloatCoder();
    public FloatCoder(){ super(FFI_FLOAT, "f", Float.class, float.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, float x){
        rt.unsafe.putFloat(addr, (float) x);
    }
    @Override public float popFloat(JObjCRuntime rt, long addr){
        return rt.unsafe.getFloat(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 4;
    }
    @Override public void push(JObjCRuntime rt, long addr, Float x){ push(rt, addr, (float) x); }
    @Override public Float pop(JObjCRuntime rt, long addr){ return popFloat(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((float)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popFloat(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((float)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popFloat(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((float)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popFloat(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((float)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popFloat(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((float)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popFloat(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((float)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popFloat(rt, addr)); }


    @Override public void push(JObjCRuntime rt, long addr, double x){ push(rt, addr, ((float)x)); }
    @Override public double popDouble(JObjCRuntime rt, long addr){ return ((double)popFloat(rt, addr)); }


}

// native double -> java double
public static final class DoubleCoder extends PrimitiveCoder<Double>{
    public static final DoubleCoder INST = new DoubleCoder();
    public DoubleCoder(){ super(FFI_DOUBLE, "d", Double.class, double.class); }
    // compile time
    @Override public void push(JObjCRuntime rt, long addr, double x){
        rt.unsafe.putDouble(addr, (double) x);
    }
    @Override public double popDouble(JObjCRuntime rt, long addr){
        return rt.unsafe.getDouble(addr);
    }
    // for runtime coding
    @Override public int sizeof(Width w){
        return 8;
    }
    @Override public void push(JObjCRuntime rt, long addr, Double x){ push(rt, addr, (double) x); }
    @Override public Double pop(JObjCRuntime rt, long addr){ return popDouble(rt, addr); }
    // proxies for mixed encoding
    @Override public void push(JObjCRuntime rt, long addr, boolean x){ push(rt, addr, ((double)(x ? 1 : 0))); }
    @Override public boolean popBoolean(JObjCRuntime rt, long addr){ return (popDouble(rt, addr) != 0); }

    @Override public void push(JObjCRuntime rt, long addr, byte x){ push(rt, addr, ((double)x)); }
    @Override public byte popByte(JObjCRuntime rt, long addr){ return ((byte)popDouble(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, char x){ push(rt, addr, ((double)x)); }
    @Override public char popChar(JObjCRuntime rt, long addr){ return ((char)popDouble(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, short x){ push(rt, addr, ((double)x)); }
    @Override public short popShort(JObjCRuntime rt, long addr){ return ((short)popDouble(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, int x){ push(rt, addr, ((double)x)); }
    @Override public int popInt(JObjCRuntime rt, long addr){ return ((int)popDouble(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, long x){ push(rt, addr, ((double)x)); }
    @Override public long popLong(JObjCRuntime rt, long addr){ return ((long)popDouble(rt, addr)); }

    @Override public void push(JObjCRuntime rt, long addr, float x){ push(rt, addr, ((double)x)); }
    @Override public float popFloat(JObjCRuntime rt, long addr){ return ((float)popDouble(rt, addr)); }



}

}
