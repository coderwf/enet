package com.github.enet.buffer;

import java.io.UnsupportedEncodingException;

public interface ByteBuf {
    
	//self
	public int capacity();
		
	public boolean hasArray();
	
	public boolean isDirect();
	
	public byte[] array();
	
	public int unRead();
	
	public int unWrite();
    
	//bytes
    public ByteBuf readSlice(int length);
	
    public ByteBuf readSlice();
    
	public ByteBuf writeBytes(byte[] writes) ;
	
	public byte[] readBytes(int len);
	
	//int
	public int readInt();
			
	public int readInt(boolean bigEndian);
	
	public ByteBuf writeInt(int x);
	
	public ByteBuf writeInt(int x, boolean bigEndian);
	
	//long
	public long readLong();
	
	public long readLong(boolean bigEndian);
	
    public ByteBuf writeLong(long x);
    
    public ByteBuf writeLong(long x, boolean bigEndian);
    
    //short
    public short readShort();
    
    public short readShort(boolean bigEndian);
    
    public ByteBuf writeShort(short s);
    
    public ByteBuf writeShort(short s, boolean bigEndian);
    
    //double
    public double readDouble();
    
    public double readDouble(boolean bigEndian);
    
    public ByteBuf writeDouble(double d);
    
    public ByteBuf writeDouble(double d, boolean bigEndian);
    
    //float
    public float readFloat();
    
    public float readFloat(boolean bigEndian);
    
    public ByteBuf writeFloat(float f);
    
    public ByteBuf writeFloat(float f, boolean bigEndian);
    
    
    //char
    public char readChar();
    
    public ByteBuf writeChar(char c);
    
    //byte
    public byte readByte();
    
    public ByteBuf writeByte(byte b);
    
    //string
    public String readString(int bytesN, String encoding) throws UnsupportedEncodingException;
    
    public ByteBuf writeString(String s, String encoding) throws UnsupportedEncodingException;
    
	//index
	public ByteBuf markRead();
	
	public ByteBuf resetRead();
	
	public ByteBuf markWrite();
	
	public ByteBuf resetWrite();
	
	public ByteBuf markRW();
	
	public ByteBuf resetRW();
	
	public ByteBuf clear();
	
	public ByteBuf discardReads();
	
	//skip
	public ByteBuf skipReadBytes(int skip);
	
}
