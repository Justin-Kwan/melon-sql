package org.smalldb.disk;

import org.apache.hadoop.hive.common.io.encoded.EncodedColumnBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.impl.OutStream;
import org.jetbrains.annotations.NotNull;
import org.smalldb.page.Tuple;

import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class BufferedTupleOutputStream
//        extends FilterOutputStream
//        implements DataOutput
{
//    private ByteBuffer byteBuffer;
//    private int writtenTupleCount;
//    OutStream
//
//    /**
//     * Creates an output stream filter built on top of the specified
//     * underlying output stream.
//     *
//     * @param underlyingOutputStream
//     *            the underlying output stream to be assigned to
//     *            the field {@code this.out} for later use, or
//     *            {@code null} if this instance is to be
//     *            created without an underlying stream.
//     */
//    public BufferedTupleOutputStream(OutputStream underlyingOutputStream)
//    {
//        super(underlyingOutputStream);
//        OrcFile.createWriter()
//        this.writtenTupleCount = 0;
//    }
//
//    public int getSize()
//    {
//        return byteBuffer.position();
//    }
//
//    public void write(Tuple tuple)
//    {
//
//        // do data serialization?
//    }
//
//    @Override
//    public void write(byte @NotNull [] b, int off, int len) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeBoolean(boolean v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeByte(int v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeShort(int v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeChar(int v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeInt(int v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeLong(long v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeFloat(float v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeDouble(double v) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeBytes(@NotNull String s) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeChars(@NotNull String s) throws IOException
//    {
//
//    }
//
//    @Override
//    public void writeUTF(@NotNull String s) throws IOException
//    {
//
//    }
}
