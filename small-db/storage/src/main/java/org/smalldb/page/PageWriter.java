package org.smalldb.page;

import java.io.IOException;

public interface PageWriter
{
    void write(Page page) throws IOException;

    void close() throws IOException;
}
