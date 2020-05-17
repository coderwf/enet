package com.github.enet.buffer;

import java.io.UnsupportedEncodingException;


public abstract class AbstractByteBuf implements ByteBuf{
    
	//
	int writerIndex = 0, readerIndex = 0;
	int markedWriterIndex = 0, markedReaderIndex = 0;
	
//	void setWriterIx(int writerIx) {
//		this.writerIndex = writerIx;
//	}
//	
//	void setReaderIx(int readerIx) {
//		this.readerIndex = readerIx;
//	}
//	
//	void setMarkedWriterIx(int markedWriterIx) {
//		this.markedWriterIndex = markedWriterIx;
//	}
//	
//	void setMarkedReaderIx(int markedReaderIx) {
//	    this.markedReaderIndex = markedReaderIx;	
//	}
//	
	public final static int MAX_CAPACITY;
	
	static {
	    int maxCapacity = 1024 * 1024 * 20; //20 M
	    try {
	    	maxCapacity = Integer.parseInt(System.getenv("BYTEBUF_MAX_CAP"));
		} catch (Exception e) {
			maxCapacity = 1024 * 1024 * 20;
		}
	    
	    MAX_CAPACITY = maxCapacity;
	}
	
	public final static ByteBuf allocate(int capacity) {
		return new HeapByteBuf(capacity);
	}
	
	public final static ByteBuf wrap(byte []init) {
		return new HeapByteBuf(init);
	}
	
    public final static ByteBuf wrap(String str, String encoding) throws UnsupportedEncodingException {
        return new HeapByteBuf(str, encoding);
    }
    
	public abstract int capacity();

	public abstract boolean hasArray();

	public abstract boolean isDirect();

	public abstract byte[] array();
	
	public int unRead() {
	    return this.writerIndex - this.readerIndex;
	}
    
	public int unWrite() {
	    return capacity() - this.writerIndex;
	}
	
	public int readerIndex() {
		return this.readerIndex;
	}
	
	public int writerIndex() {
		return this.writerIndex;
	}
	
	private ByteBuf _ensureRead(int need) {
		if(this.readerIndex + need > this.writerIndex)
			throw new IndexOutOfBoundsException(
					String.format("ByteBuf readIndex:%d, writeIndex:%d , need:%d", this.readerIndex, this.writerIndex, need)
					);
		return this;
	}
	
	protected abstract void _writeBytes(int from, byte[]writes);
	
	public ByteBuf writeBytes(byte[] writes) {
		_ensureWrite(writes.length);
		this.writerIndex += writes.length;
		_writeBytes(this.writerIndex - writes.length, writes);
		return this;
	}
    
	protected abstract byte[] _readBytes(int from, int length);
	
	public byte[] readBytes(int length) {
		if(length < 0)
			throw new IllegalArgumentException("length of ByteBuf.readBytes must > 0");
		
		_ensureRead(length);
		this.readerIndex += length;
		return _readBytes(this.readerIndex - length, length);
	}
    
	protected abstract int _readInt(int from, boolean bigEndian);
	
	public int readInt() {
	    return readInt(true);
	}
    
	public int readInt(boolean bigEndian) {
		_ensureRead(4);
		this.readerIndex += 4;
		return _readInt(this.readerIndex - 4, bigEndian);
	}
    
	protected abstract void setNewCapacity(int newCapacity);
	
	private void _ensureWrite(int need) {
		int needCapacity = need + this.writerIndex;
		if(needCapacity <= this.capacity())
			return ;
		
		if(needCapacity > MAX_CAPACITY)
			throw new IndexOutOfBoundsException(
					String.format("need %d to write, exceeds max capacity: %d", needCapacity, MAX_CAPACITY));
		
		int newCapacity = needCapacity > (MAX_CAPACITY / 2) ?(needCapacity + MAX_CAPACITY / 20): needCapacity * 2;		
		//
		
		setNewCapacity(newCapacity);
		
	}
	
	protected abstract void _writeInt(int x, int from, boolean bigEndian);
	
	public ByteBuf writeInt(int x) {
		return writeInt(x, true);
	}

	public ByteBuf writeInt(int x, boolean bigEndian) {
		_ensureWrite(4);
		this.writerIndex += 4;
		
		_writeInt(x, this.writerIndex - 4, bigEndian);
		return this;
	}
    
	protected abstract long _readLong(int from, boolean bigEndian);
	
	public long readLong() {
		return readLong(true);
	}

	public long readLong(boolean bigEndian) {
		_ensureRead(8);
		this.readerIndex += 8;
		return _readLong(this.readerIndex - 8, bigEndian);
	}
    
	protected abstract void _writeLong(long x, int from, boolean bigEndian);
	
	public ByteBuf writeLong(long x) {
		return writeLong(x, true);
	}

	public ByteBuf writeLong(long x, boolean bigEndian) {
		_ensureWrite(8);
		this.writerIndex += 8;
		_writeLong(x, this.writerIndex - 8, bigEndian);
		return null;
	}
    
	protected abstract short _readShort(int from, boolean bigEndian);
	
	public short readShort() {
		return readShort(true);
	}

	public short readShort(boolean bigEndian) {
		_ensureRead(2);
		this.readerIndex += 2;
		return _readShort(this.readerIndex - 2, bigEndian);
	}
    
	protected abstract void _writeShort(short s, int from, boolean bigEndian);
	
	public ByteBuf writeShort(short x) {
		return writeShort(x, true);
	}

	public ByteBuf writeShort(short x, boolean bigEndian) {
		_ensureWrite(2);
		this.writerIndex += 2;
		_writeShort(x, this.writerIndex - 2, bigEndian);
		return this;
	}

	public double readDouble() {
		return readDouble(true);
	}
    
	protected abstract double _readDouble(int from, boolean bigEndian);
	
	public double readDouble(boolean bigEndian) {
		_ensureRead(8);
		this.readerIndex += 8;
		return _readDouble(this.readerIndex - 8, bigEndian);
	}
    
	protected abstract void _writeDouble(double x, int from, boolean bigEndian);
	
	public ByteBuf writeDouble(double x) {
		return writeDouble(x, true);
	}

	public ByteBuf writeDouble(double x, boolean bigEndian) {
		_ensureWrite(8);
		this.writerIndex += 8;
		_writeDouble(x, this.writerIndex - 8, bigEndian);
		return this;
	}
    
	protected abstract float _readFloat(int from, boolean bigEndian);
	
	public float readFloat() {
		return readFloat(true);
	}

	public float readFloat(boolean bigEndian) {
		_ensureRead(4);
		this.readerIndex += 4;
		return _readFloat(this.readerIndex - 4, bigEndian);
	}
    
	protected abstract void _writeFloat(float x, int from, boolean bigEndian);
	
	public ByteBuf writeFloat(float x) {
		return writeFloat(x, true);
	}

	public ByteBuf writeFloat(float x, boolean bigEndian) {
		_ensureWrite(4);
		this.writerIndex += 4;
		_writeFloat(x, this.writerIndex - 4, bigEndian);
		return this;
	}
    
	protected abstract char _readChar(int from);
	
	public char readChar() {
		_ensureRead(1);
		this.readerIndex += 1;
		return _readChar(this.readerIndex - 1);
	}
    
	protected abstract void _writeChar(char c, int from);
	
	public ByteBuf writeChar(char c) {
		_ensureWrite(1);
		this.writerIndex += 1;
		_writeChar(c, this.writerIndex - 1);
		return this;
	}
    
	protected abstract byte _readByte(int from);
	
	public byte readByte() {
		_ensureRead(1);
		this.readerIndex += 1;
		return _readByte(this.readerIndex - 1);
	}
    
	protected abstract void _writeByte(byte b, int from);
	
	public ByteBuf writeByte(byte b) {
		_ensureWrite(1);
		this.writerIndex += 1;
		_writeByte(b, this.writerIndex - 1);
		return this;
	}
    
	protected abstract String _readString(int from, int bytesN, String encoding) throws UnsupportedEncodingException;
	
	public String readString(int bytesN, String encoding) throws UnsupportedEncodingException{
		_ensureRead(bytesN);
		this.readerIndex += bytesN;
		return _readString(this.readerIndex - bytesN, bytesN, encoding);
	}
    
	public abstract ByteBuf writeString(String s, String encoding) throws UnsupportedEncodingException;
		

	public ByteBuf markRead() {
		this.markedReaderIndex = this.readerIndex;
	    return this;
	}

	public ByteBuf resetRead() {
	    this.readerIndex = this.markedReaderIndex > this.writerIndex ? this.writerIndex: this.markedReaderIndex;
	    return this;
	}

	public ByteBuf markWrite() {
		this.markedWriterIndex = this.writerIndex;
		return this;
	}

	public ByteBuf resetWrite() {
		this.writerIndex = this.markedWriterIndex < this.readerIndex? this.readerIndex: this.markedWriterIndex;
		return this;
	}

	public ByteBuf markRW() {
		markRead();
		markWrite();
		return this;
	}

	public ByteBuf resetRW() {
		resetWrite();
		resetRead();
		return this;
	}

	public ByteBuf clear() {
	    this.readerIndex = this.writerIndex = this.markedReaderIndex = this.markedWriterIndex = 0;
	    return this;
	}
    
	public abstract ByteBuf _moveUnreadsToHead(int readIx, int writeIx);
	
	public ByteBuf discardReads() {
		this.markedReaderIndex = this.markedWriterIndex = 0;
		_moveUnreadsToHead(this.readerIndex, this.writerIndex);
		this.writerIndex -= this.readerIndex;
		this.readerIndex = 0;
		return this;
	}
    
	
	public ByteBuf skipReadBytes(int skip) {
		this.readerIndex += skip;
		if(this.readerIndex > this.writerIndex)
			this.readerIndex = this.writerIndex;
	    return this;
	}
	
	protected abstract ByteBuf _readSlice(int from, int len);
	
	public ByteBuf readSlice(int length) {
		length = Math.min(length, this.writerIndex - this.readerIndex);
		ByteBuf slice =  _readSlice(this.readerIndex, length);
		this.readerIndex += length;
        return slice;
	}
		
	public ByteBuf readSlice() {
	    return readSlice(this.writerIndex - this.readerIndex);
	}
}
