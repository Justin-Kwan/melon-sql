package org.smalldb.page;

import java.util.Arrays;

public class Page
{
    private final Tuple[] symbolTuples;

    public Page(Tuple[] symbolTuples)
    {
        this.symbolTuples = Arrays.copyOf(symbolTuples, symbolTuples.length);
    }


}

public class Value
