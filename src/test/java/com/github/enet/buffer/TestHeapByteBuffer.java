package com.github.enet.buffer;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class TestHeapByteBuffer {
    
	@Test
	public void testWriteInt() {
		ByteBuf buf = AbstractByteBuf.allocate(16);
		
		buf.writeInt(100);
		buf.writeInt(200, false);
		buf.writeInt(Integer.MAX_VALUE);
		buf.writeInt(Integer.MIN_VALUE, false);
		
		assertEquals(buf.readInt(), 100);
		assertEquals(buf.readInt(false), 200);
		assertEquals(buf.readInt(), Integer.MAX_VALUE);
		assertEquals(buf.readInt(false), Integer.MIN_VALUE);
		
		try {
			buf.readInt();
			assertTrue(false);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		
		buf.writeInt(23);
		
		assertEquals(buf.readInt(), 23);
		assertEquals(buf.capacity(), 40);
		
		assertEquals(buf.hasArray(), true);
		assertEquals(buf.isDirect(), false);
		
		buf.clear();
		
		buf.writeInt(1111);
		assertEquals(buf.unRead(), 4);
		assertEquals(buf.unWrite(), 36);
		buf.readInt();
		
		buf.discardReads();
		assertEquals(buf.unRead(), 0);
		assertEquals(buf.unWrite(), 40);
		
		buf.writeInt(100000);
		buf.writeInt(120);
		buf.clear();
		assertEquals(buf.unRead(), 0);
		assertEquals(buf.unWrite(), 40);
	}
	
	@Test
	public void testWriteLong() {
		ByteBuf buf = AbstractByteBuf.allocate(32);
		buf.writeLong(Long.MAX_VALUE);
		buf.writeLong(Long.MIN_VALUE);
		
		assertEquals(buf.readLong(), Long.MAX_VALUE);
		assertEquals(buf.readLong(), Long.MIN_VALUE);
		
		try {
			buf.readLong();
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
		buf.writeLong(Long.MAX_VALUE, false);
		buf.writeLong(Long.MIN_VALUE, false);
		
		assertEquals(buf.readLong(false), Long.MAX_VALUE);
		assertEquals(buf.readLong(false), Long.MIN_VALUE);
		
		assertEquals(buf.unRead(), 0);
		assertEquals(buf.capacity(), 32);
		assertEquals(buf.unWrite(), 0);
		
		buf.clear();
		assertEquals(buf.capacity(), 32);
		assertEquals(buf.unRead(), 0);
		assertEquals(buf.unWrite(), 32);
		
		buf.writeLong(1);
		buf.writeLong(2);
		buf.writeLong(3);
		buf.writeLong(4);
		buf.writeLong(5);
		
		assertEquals(buf.capacity(), 80);
		assertEquals(buf.unRead(), 40);
		assertEquals(buf.unWrite(), 40);
		
        assertEquals(buf.readLong(), 1);
        assertEquals(buf.readLong(), 2);
        assertEquals(buf.readLong(), 3);
        assertEquals(buf.readLong(), 4);
        
        assertEquals(buf.unRead(), 8);
        buf.discardReads();
        
        assertEquals(buf.unWrite(), 72);
        
        
	}
	
	@Test
	public void testWriteShort() {
		ByteBuf buf = AbstractByteBuf.allocate(8);
		
		buf.writeShort((short)4);
		buf.writeShort((short)5);
		buf.writeShort((short)6);
		buf.writeShort((short)7);
		
		assertEquals(buf.readShort(), 4);
		assertEquals(buf.readShort(), 5);
		assertEquals(buf.readShort(), 6);
		assertEquals(buf.readShort(), 7);
		
		buf.writeShort((short)10);
		
		assertEquals(buf.capacity(), 20);
		assertEquals(buf.unRead(), 2);
		assertEquals(buf.unWrite(), 10);
		buf.discardReads();
		
		assertEquals(buf.unWrite(), 18);
		
		buf.clear();
		buf.writeShort((short)1, false);
		buf.writeShort((short)Short.MAX_VALUE, false);
		buf.writeShort((short)Short.MIN_VALUE, false);
		buf.writeShort((short)Short.MAX_VALUE);
		assertEquals(buf.readShort(false), 1);
		assertEquals(buf.readShort(false), Short.MAX_VALUE);
		assertEquals(buf.readShort(false), Short.MIN_VALUE);
		assertEquals(buf.readShort(), Short.MAX_VALUE);
	}
	
	
	@Test
	public void testWriteFloat() {
		ByteBuf buf = AbstractByteBuf.allocate(8);
		buf.writeFloat(10.44f);
		buf.writeFloat(Float.MAX_VALUE);
		
		assertTrue(buf.readFloat() == 10.44f);
		assertTrue(buf.readFloat() == Float.MAX_VALUE);

		buf.writeFloat(Float.MIN_VALUE);
		
		assertEquals(buf.capacity(), 24);
		
		assertEquals(buf.unRead(), 4);
		assertEquals(buf.unWrite(), 12);
		buf.discardReads();
		assertEquals(buf.unWrite(), 20);
		assertEquals(buf.unRead(), 4);
		
		buf.clear();
		buf.writeFloat(1000.11f, false);
		buf.writeFloat(Float.MAX_VALUE, false);
		buf.writeFloat(Float.MIN_VALUE, false);
		
		assertTrue(buf.readFloat(false) == 1000.11f);
		assertTrue(buf.readFloat(false) == Float.MAX_VALUE);
		assertTrue(buf.readFloat(false) == Float.MIN_VALUE);
		
	}
	
	@Test
	public void testWriteChar() {
		ByteBuf buf = AbstractByteBuf.allocate(2);
		buf.writeChar('a');
		buf.writeChar('b');
		buf.writeChar('c');
		assertEquals(buf.capacity(), 6);
		assertEquals(buf.readChar(), 'a');
		assertEquals(buf.readChar(), 'b');
		assertEquals(buf.readChar(), 'c');
		buf.writeChar('d');
		buf.writeChar('e');
		buf.writeChar('f');
		buf.writeChar('g');
		
		assertEquals(buf.capacity(), 14);
		assertEquals(buf.unRead(), 4);
		assertEquals(buf.unWrite(), 7);
		buf.discardReads();
		assertEquals(buf.unRead(), 4);
		assertEquals(buf.unWrite(), 10);
		
	}
	
	@Test
	public void testWriteByte() {
		ByteBuf buf = AbstractByteBuf.allocate(2);
		buf.writeByte((byte)1);
		buf.writeByte((byte)1);
		buf.writeByte((byte)1);
		assertEquals(buf.capacity(), 6);
		assertEquals(buf.readByte(), (byte)1);
		assertEquals(buf.readByte(), (byte)1);
		assertEquals(buf.readByte(), (byte)1);
		buf.writeByte((byte)1);
		buf.writeByte((byte)1);
		buf.writeByte((byte)1);
		buf.writeByte((byte)1);
		
		assertEquals(buf.capacity(), 14);
		assertEquals(buf.unRead(), 4);
		assertEquals(buf.unWrite(), 7);
		buf.discardReads();
		assertEquals(buf.unRead(), 4);
		assertEquals(buf.unWrite(), 10);
	}
	
	@Test
	public void testWriteString() {
		ByteBuf buf = AbstractByteBuf.allocate(2);
	    String str = "你好,123";
	    try {
			buf.writeString(str, "utf-8");
			assertTrue(true);
		} catch (UnsupportedEncodingException e) {
			assertTrue(false);
		}
	    
	    String readString = null;
	    
	    try {
			 readString = buf.readString(buf.unRead(), "utf-8");
		} catch (UnsupportedEncodingException e) {
		    assertTrue(false);
		}
	    
	    assertTrue(str.equals(readString));
	    
	}
	
	@Test
	public void testInte() {
		ByteBuf buf = AbstractByteBuf.allocate(100);
		buf.writeInt(100);
		buf.writeLong(2000L);
		buf.writeFloat(100.f);
		buf.writeDouble(10000d);
		
		assertEquals(buf.readInt(), 100);
		
		ByteBuf slice = buf.readSlice();
		
		assertEquals(slice.readLong(), 2000L);
		assertTrue(slice.readFloat() == 100f);
		assertTrue(slice.readDouble() == 10000d);
	}
	
}
