package org.smalldb.page;

import java.io.IOException;

public class Tuple
{
    private final RecordId id;
    private final byte[] data;
    private final TupleHeader header;

    public Tuple(RecordId id, TupleHeader header, byte[] data)
    {
        this.id = id;
        this.header = header;
        this.data = data;
    }

    public static Tuple ofDecimal(RecordId id, TupleHeader header, double value)
            throws IOException
    {
        return new Tuple(id, header, doubleToByteArray(value));
    }

    private static byte[] doubleToByteArray(final double i)
            throws IOException
    {
        long data = Double.doubleToRawLongBits(i);

        return new byte[] {
                (byte) ((data >> 56) & 0xff),
                (byte) ((data >> 48) & 0xff),
                (byte) ((data >> 40) & 0xff),
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    public RecordId getId()
    {
        return id;
    }

    public byte[] getData()
    {
        return data;
    }

    public static class TupleHeader
    {

    }

    public static class RecordId
    {

    }
}
