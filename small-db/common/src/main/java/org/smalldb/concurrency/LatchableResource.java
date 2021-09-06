package org.smalldb.concurrency;

public interface LatchableResource
{
    void latchRead();

    void unlatchRead();

    void latchWrite();

    void unlatchWrite();
}
