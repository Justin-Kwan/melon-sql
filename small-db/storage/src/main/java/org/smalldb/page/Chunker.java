package org.smalldb.page;

import static com.google.common.base.Preconditions.checkElementIndex;
import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;

public class Chunker<T>
{
    private int currentOffset;
    private final int lengthFromOffset;
    private final Chunkable<T> chunkable;

    // TODO: handle data safety here
    public Chunker(int fromOffset, int lengthFromOffset, Chunkable<T> chunkable)
    {
        this.chunkable = requireNonNull(chunkable, "chunkable is null");
        this.currentOffset = checkElementIndex(fromOffset, chunkable.getLength());
        this.lengthFromOffset = checkElementIndex(lengthFromOffset, chunkable.getLength());
    }

    public int getCurrentOffset()
    {
        return currentOffset;
    }

    public boolean hasNext()
    {
        return currentOffset < chunkable.getLength();
    }

    public T next()
    {
        T chunk = chunkable.chunk(currentOffset, lengthFromOffset);
        currentOffset = min(currentOffset + lengthFromOffset, chunkable.getLength());
        return chunk;
    }
}
