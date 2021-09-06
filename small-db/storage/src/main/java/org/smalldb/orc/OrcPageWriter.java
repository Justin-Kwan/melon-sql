package org.smalldb.orc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.smalldb.page.Chunker;
import org.smalldb.page.Page;
import org.smalldb.page.PageWriter;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class OrcPageWriter
        implements PageWriter
{
    private final Options options;
    private final PageSerde pageSerializer;
    private final Writer orcFileWriter;

    public OrcPageWriter(Options options, PageSerde pageSerializer, Writer orcFileWriter)
            throws IOException
    {
        this.options = requireNonNull(options, "options is null");
        this.pageSerializer = requireNonNull(pageSerializer, "pageSerializer is null");
        this.orcFileWriter = requireNonNull(orcFileWriter, "orcFileWriter is null");
//        WriterImpl
    }

    private static OrcPageWriter create(Options options, PageSerde pageSerializer)
            throws IOException
    {
        Configuration orcConfig = new Configuration();
        TypeDescription schema = TypeDescription.fromString("struct<is_new:boolean>");

        LocalFileSystem fs = new LocalFileSystem();
        fs.initialize(URI.create("file:///"), orcConfig);

        Writer orcFileWriter = OrcFile.createWriter(
                new Path("my-file.orc"),
                OrcFile.writerOptions(orcConfig).setSchema(schema).fileSystem(fs));

        return new OrcPageWriter(options, pageSerializer, orcFileWriter);
    }

    // size of each chunk is minimum between max parquet row groups
    // and "rows" in Page
    public void write(Page page)
            throws IOException
    {
        if (page.isEmpty()) {
            return;
        }

        Chunker<Page> chunker = new Chunker<>(0, 5, page);

        // write the tuple to buffer (need to know the type). Flush the buffer.
        while (chunker.hasNext()) {
            Page pageChunk = chunker.next();
            VectorizedRowBatch orcRowBatch = pageSerializer.serialize(pageChunk);

            System.out.println("row batch column count: " + orcRowBatch.numCols);
            System.out.println("row batch columns: " + Arrays.toString(orcRowBatch.cols));
            System.out.println("column value count: " + orcRowBatch.cols[0].isNull.length);

            this.orcFileWriter.addRowBatch(orcRowBatch);
        }
    }

    public void close()
            throws IOException
    {
        this.orcFileWriter.close();
    }

    interface Options
    {
        long getBlockSizeInBytes();

        long getPageSizeInBytes();
    }
}
