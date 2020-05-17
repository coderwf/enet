package com.github.enet.buffer;

import java.io.UnsupportedEncodingException;


class HeapByteBuf extends AbstractByteBuf{
    
	private int capacity;
		
	private byte ba[] ; //byte array
	
	HeapByteBuf(int capacity) {
		this.capacity = capacity;
		this.ba = new byte[capacity];
	}
	
	
	HeapByteBuf(byte []init) {
		this.capacity = init.length;
		this.ba = init;
	}
	
	HeapByteBuf(String init, String encoding) throws UnsupportedEncodingException {
		byte bytes[] = init.getBytes(encoding);
		this.capacity = bytes.length;
		this.ba = bytes;
	}
	
	@Override
	public int capacity() {
		return this.capacity;
	}


	@Override
	public boolean hasArray() {
		return true;
	}

	@Override
	public boolean isDirect() {
		return false;
	}

	@Override
	public byte[] array() {
		return ba;
	}

	@Override
	protected void _writeBytes(int from, byte[] writes) {
		System.arraycopy(writes, 0, ba, from, writes.length);
	}

	@Override
	protected byte[] _readBytes(int from, int length) {
		byte[] read = new byte[length];
		System.arraycopy(ba, from, read, 0, length);
		return read;
	}

	@Override
	protected int _readInt(int from, boolean bigEndian) {
		return Bits.getInt(ba, from, bigEndian);
	}


	@Override
	protected long _readLong(int from, boolean bigEndian) {
		return Bits.getLong(ba, from, bigEndian);
	}

	@Override
	protected void _writeLong(long x, int from, boolean bigEndian) {
        Bits.putLong(ba, from, x, bigEndian);
	}

	@Override
	protected short _readShort(int from, boolean bigEndian) {
		return Bits.getShort(ba, from, bigEndian);
	}

	@Override
	protected void _writeShort(short s, int from, boolean bigEndian) {
		Bits.putShort(ba, from, s, bigEndian);
	}

	@Override
	protected double _readDouble(int from, boolean bigEndian) {
		return Bits.getDouble(ba, from, bigEndian);
	}

	@Override
	protected void _writeDouble(double x, int from, boolean bigEndian) {
		Bits.putDouble(ba, from, x, bigEndian);
	}

	@Override
	protected float _readFloat(int from, boolean bigEndian) {
		return Bits.getFloat(ba, from, bigEndian);
	}

	@Override
	protected void _writeFloat(float x, int from, boolean bigEndian) {
		Bits.putFloat(ba, from, x, bigEndian);
	}

	@Override
	protected char _readChar(int from) {
		return (char)(ba[from]);
	}

	@Override
	protected void _writeChar(char c, int from) {
		ba[from] = (byte)c;
	}

	@Override
	protected byte _readByte(int from) {
		return ba[from];
	}

	@Override
	protected void _writeByte(byte b, int from) {
        ba[from] = b;		
	}

	@Override
	protected String _readString(int from, int bytesN, String encoding) throws UnsupportedEncodingException {
		return new String(ba, from, bytesN, encoding);
	}

	@Override
	public ByteBuf writeString(String s, String encoding) throws UnsupportedEncodingException{
		//可否直接将string编码到ba中,减少一次数组拷贝
		byte[] strBytes = s.getBytes(encoding);
		writeBytes(strBytes);
		return this;
	}

	@Override
	public ByteBuf _moveUnreadsToHead(int readIx, int writeIx) {
	    System.arraycopy(ba, readIx, ba, 0, writeIx - readIx);
	    return this;
	}

	@Override
	protected void _writeInt(int x, int from, boolean bigEndian) {
		Bits.putInt(ba, from, x, bigEndian);
	}

	
	@Override
	protected void setNewCapacity(int newCapacity) {
		if(newCapacity < this.capacity)
			return ;
		byte [] newBa = new byte[newCapacity];
		this.capacity = newCapacity;
		System.arraycopy(ba, 0, newBa, 0, this.writerIndex);
		this.ba = newBa;
	}


	@Override
	protected ByteBuf _readSlice(int from, int len) {
	    AbstractByteBuf newBuf = new HeapByteBuf(ba);
	    newBuf.readerIndex = this.readerIndex;
	    newBuf.writerIndex = this.readerIndex + len;
	    newBuf.markRW();
	    return newBuf;
	}

}
