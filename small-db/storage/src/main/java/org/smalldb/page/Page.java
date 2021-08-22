package org.smalldb.page;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkPositionIndexes;

public class Page
        implements Chunkable<Page>
{
    private final PageId id;
    private final int size;
    private final int capacity;
    private final boolean isDirty;
    private final Tuple<?>[] tuples;

    public Page(PageId id, int size, int capacity, Tuple<?>[] tuples)
    {
        this.id = id;
        this.size = size;
        this.capacity = capacity;
        this.isDirty = false;
        this.tuples = tuples.clone();
    }

    public static Page ofEmpty(PageId id)
    {
        return new Page(id, 0, 0, new Tuple[0]);
    }

    public PageId getId()
    {
        return id;
    }

    @Override
    public int getLength()
    {
        return this.tuples.length;
    }

    public boolean isEmpty()
    {
        return getTupleCount() == 0;
    }

    public boolean isDirty()
    {
        return isDirty;
    }

    public int getTupleCount()
    {
        return this.tuples.length;
    }

    public Tuple<?>[] getTuples()
    {
        return tuples.clone();
    }

    public Tuple<?> getTupleAt(int position)
    {
        checkElementIndex(position, tuples.length);
        return tuples[position];
    }

    @Override
    public Page chunk(int rowOffset, int length)
    {
        checkPositionIndexes(rowOffset, rowOffset + length, tuples.length);
        Tuple<?>[] tupleChunks = new Tuple[getTupleCount()];

        // tuples are chunked (sliced) horizontally
        for (int i = 0; i < tuples.length; ++i) {
            tupleChunks[i] = tuples[i].chunk(rowOffset, length);
        }

        return new Page(id, size, capacity, tupleChunks);
    }

    public boolean isNull()
    {
        return false;
    }

    public static Page ofNull()
    {
        return new NullPage();
    }

    static class NullPage
            extends Page
    {
        private NullPage()
        {
            super(PageId.NullPageId.ofNull(), -1, -1, new Tuple[0]);
        }

        public boolean isNull()
        {
            return true;
        }
    }
}
