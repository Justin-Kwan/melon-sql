package org.smalldb.page;

public class Page
{
    private final PageId id;
    private final int size;
    private final int capacity;
    private final boolean isDirty;
    private final Tuple[] tuples;

    public Page(PageId id, int size, int capacity, Tuple[] tuples)
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

    public int getSize()
    {
        return this.size;
    }

    public int getTupleCount()
    {
        return this.tuples.length;
    }

    public boolean isDirty()
    {
        return isDirty;
    }

    public Tuple[] getTuples()
    {
        return tuples.clone();
    }

    public Page chunk(int rowOffset, int length)
    {
        if (rowOffset > getTupleCount() || length > getTupleCount() || rowOffset + length > getTupleCount()) {
            throw new IllegalArgumentException();
        }

        Tuple[] tupleChunks = new Tuple[getTupleCount()];

        for (int i = 0; i < getTupleCount(); ++i) {
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
            super(id, -1, -1, new Tuple[0]);
        }

        public boolean isNull()
        {
            return true;
        }
    }
}
