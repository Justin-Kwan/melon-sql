package org.smalldb.page;

public class Chunker<T>
{
    private int currentOffset;
    private final Chunkable<T> chunkable;

    // TODO: handle data safety here
    public Chunker(int offset, Chunkable<T> chunkable)
    {
        this.currentOffset = offset;
        this.chunkable = chunkable;
    }

    public boolean hasNext()
    {
        return currentOffset < chunkable.getLength();
    }

    public T next()
    {
        T chunk = chunkable.chunk(currentOffset, chunkable.getLength());
        currentOffset += chunkable.getLength();
        return chunk;
    }
}
