package org.smalldb.page;

public interface Chunkable<T>
{
    int getLength();

    T chunk(int offset, int length);
}
