package org.smalldb.memory;

import org.smalldb.page.Page;

public interface PageCache
{
    void insert(Page page);

    Page read(Page page);
}
