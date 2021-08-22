package org.smalldb.orc;

import org.smalldb.page.BooleanTuple;
import org.smalldb.page.Page;
import org.smalldb.page.PageId;
import org.smalldb.page.RecordId;
import org.smalldb.page.Tuple;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestOrcPageWriter
{
    @Test
    public void testWrite()
            throws IOException
    {
        OrcPageWriter writer = new OrcPageWriter(
                new OrcPageWriter.Options()
                {
                    @Override
                    public long getBlockSizeInBytes()
                    {
                        return 0;
                    }

                    @Override
                    public long getPageSizeInBytes()
                    {
                        return 0;
                    }
                },
                new PageSerde()
        );

        writer.write(new Page(
                PageId.ofNull(),
                1000000,
                1000000,
                new Tuple[]{
                        BooleanTuple.create(
                                new RecordId(),
                                new boolean[]{true, false, true},
                                new boolean[]{false, false, true})
                }));

        writer.close();
    }
}
