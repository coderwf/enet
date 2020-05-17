package com.github.enet.buffer;

import sun.security.util.AbstractAlgorithmConstraints;

public class Bits {
    
	 // ------------------- int -----------------------------
	 private static byte int3(int x) { return (byte)(x >> 24); }
	 private static byte int2(int x) { return (byte)(x >> 16); }
	 private static byte int1(int x) { return (byte)(x >>  8); }
	 private static byte int0(int x) { return (byte)(x      ); }
	 
	 static private int makeInt(byte b3, byte b2, byte b1, byte b0) {
	        return (((b3       ) << 24) |
	                ((b2 & 0xff) << 16) |
	                ((b1 & 0xff) <<  8) |
	                ((b0 & 0xff)      ));
	    }
	 
	 private static int putIntB(byte[]src, int from, int x) {
		 src[from    ] = int3(x);
		 src[from + 1] = int2(x);
		 src[from + 2] = int1(x);
		 src[from + 3] = int0(x);
		 return 4;
	 }
	 
	 private static int putIntL(byte[]src, int from, int x) {
		 src[from    ] = int0(x);
		 src[from + 1] = int1(x);
		 src[from + 2] = int2(x);
		 src[from + 3] = int3(x);
		 return 4;
	 }
	 
	 private static int getIntL(byte[]src, int from) {
	     return makeInt(
	    		 src[from + 3],
	    		 src[from + 2],
	    		 src[from + 1],
	    		 src[from    ]
	    		 );
	 }
	 
	 private static int getIntB(byte[]src, int from) {
	     return makeInt(
	    		 src[from    ],
	    		 src[from + 1],
	    		 src[from + 2],
	    		 src[from + 3]
	    		 );
	 }
	 
	 public static int putInt(byte[]src, int from, int x, boolean bigEndian) {
	     return bigEndian? putIntB(src, from, x): putIntL(src, from, x);
	 }
	 
	 public static int getInt(byte[]src, int from, boolean bigEndian) {
	     return bigEndian? getIntB(src, from): getIntL(src, from);
	 }
	 
	 
	 // -----------------------long ----------------------------
	 private static byte long7(long x) { return (byte)(x >> 56); }
	 private static byte long6(long x) { return (byte)(x >> 48); }
	 private static byte long5(long x) { return (byte)(x >> 40); }
	 private static byte long4(long x) { return (byte)(x  >>32); }
	 private static byte long3(long x) { return (byte)(x >> 24); }
	 private static byte long2(long x) { return (byte)(x >> 16); }
	 private static byte long1(long x) { return (byte)(x >>8  ); }
	 private static byte long0(long x) { return (byte)(x      ); }
	 
	 static private long makeLong(byte b7, byte b6, byte b5, byte b4,
			                     byte b3, byte b2, byte b1, byte b0) {
	        return ((((long)b7       ) << 56) |
	                (((long)b6 & 0xff) << 48) |
	                (((long)b5 & 0xff) << 40) |
	                (((long)b4 & 0xff) << 32) |
	                (((long)b3 & 0xff) << 24) |
	                (((long)b2 & 0xff) << 16) |
	                (((long)b1 & 0xff) <<  8) |
	                (((long)b0 & 0xff)     ));
	    }
	 
	 private static int putLongB(byte[]src, int from, long x) {
		 src[from    ] = long7(x);
		 src[from + 1] = long6(x);
		 src[from + 2] = long5(x);
		 src[from + 3] = long4(x);
		 src[from + 4] = long3(x);
		 src[from + 5] = long2(x);
		 src[from + 6] = long1(x);
		 src[from + 7] = long0(x);
		 return 8;
	 }
	 
	 private static int putLongL(byte[]src, int from, long x) {
		 src[from    ] = long0(x);
		 src[from + 1] = long1(x);
		 src[from + 2] = long2(x);
		 src[from + 3] = long3(x);
		 src[from + 4] = long4(x);
		 src[from + 5] = long5(x);
		 src[from + 6] = long6(x);
		 src[from + 7] = long7(x);
		 return 8;
	 }
	 
	 private static long getLongL(byte[]src, int from) {
	     return makeLong(
	    		 src[from + 7],
	    		 src[from + 6],
	    		 src[from + 5],
	    		 src[from + 4],
	    		 src[from + 3],
	    		 src[from + 2],
	    		 src[from + 1],
	    		 src[from    ]
	    		 );
	 }
	 
	 private static long getLongB(byte[]src, int from) {
	     return makeLong(
	    		 src[from    ],
	    		 src[from + 1],
	    		 src[from + 2],
	    		 src[from + 3],
	    		 src[from + 4],
	    		 src[from + 5],
	    		 src[from + 6],
	    		 src[from + 7]
	    		 );
	 }
	 
	 public static int putLong(byte[]src, int from, long x, boolean bigEndian) {
		 return bigEndian? putLongB(src, from, x): putLongL(src, from, x);
	 }
	 
	 public static long getLong(byte[]src, int from, boolean bigEndian) {
		return bigEndian? getLongB(src, from): getLongL(src, from);
	 }
	 
	 // ------------- double -------------
	 public static int putDouble(byte[]src, int from, double x, boolean bigEndian) {
		 return bigEndian? putLongB(src, from, Double.doubleToRawLongBits(x)): putLongL(src, from, Double.doubleToRawLongBits(x));
	 }
	 
	 public static double getDouble(byte[]src, int from, boolean bigEndian) {
		 return bigEndian? Double.longBitsToDouble(getLongB(src, from)): Double.longBitsToDouble(getLongL(src, from));
	 }
	 
	 // ---------------long --------------
	 public static int putFloat(byte[]src, int from, float x, boolean bigEndian) {
		 return bigEndian? putIntB(src, from, Float.floatToRawIntBits(x)): putIntL(src, from, Float.floatToRawIntBits(x));
	 }
	 
	 public static float getFloat(byte[]src, int from, boolean bigEndian) {
		 if(bigEndian)
			 return Float.intBitsToFloat(getIntB(src, from));
		 else
			 return Float.intBitsToFloat(getIntL(src, from));
	 }
	 
	 // ---------- short ----------
	 private static byte short1(short x) { return (byte)(x >> 8); }
	 private static byte short0(short x) { return (byte)(x     ); }
	 
	 static private short makeShort(byte b1, byte b0) {
	        return (short) (((b1       ) << 8) |
                   ((b0 & 0xff)      ));
	    }
	 
	 private static int putShortB(byte[]src, int from, short x) {
		 src[from    ] = short1(x);
		 src[from + 1] = short0(x);
		 return 2;
	 }
	 
	 private static int putShortL(byte[]src, int from, short x) {
		 src[from    ] = short0(x);
		 src[from + 1] = short1(x);
		 return 2;
	 }
	 
	 private static short getShortL(byte[]src, int from) {
	     return makeShort(
	    		 src[from + 1],
	    		 src[from    ]
	    		 );
	 }
	 
	 private static short getShortB(byte[]src, int from) {
	     return makeShort(
	    		 src[from    ],
	    		 src[from + 1]
	    		 );
	 }
	 
	 public static int putShort(byte[]src, int from, short x, boolean bigEndian) {
	     return bigEndian? putShortB(src, from, x): putShortL(src, from, x);
	 }
	 
	 public static short getShort(byte[]src, int from, boolean bigEndian) {
	     return bigEndian? getShortB(src, from): getShortL(src, from);
	 }
}
