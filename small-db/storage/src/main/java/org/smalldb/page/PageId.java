package org.smalldb.page;

import static java.lang.String.format;

public class PageId
{
    private final int id;

    public PageId(int id)
    {
        this.id = id;
    }

    public int toInt()
    {
        return id;
    }

    public String toString()
    {
        return format("(id: %d)", id);
    }

    public boolean isEqualTo(PageId pageId)
    {
        return toInt() == pageId.toInt();
    }

    public static PageId ofNull()
    {
        return new NullPageId();
    }

    static class NullPageId
            extends PageId
    {
        public NullPageId()
        {
            super(-1);
        }
    }
}
