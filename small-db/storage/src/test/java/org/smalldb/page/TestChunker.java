package org.smalldb.page;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.nCopies;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TestChunker
{
    private static final int LENGTH_FROM_OFFSET = 1;
    private static final MockChunkable MOCK_CHUNKABLE = new MockChunkable(10);

    @DataProvider
    public Object[][] getLengthAndOffsets()
    {
        return new Object[][]{
                {1, 0, 0},
                {2, 0, 1},
                {2, 1, 0},
                {2, 1, 1}, // ?
                {5, 4, 1} // ?
        };
    }

    @Test(dataProvider = "getLengthAndOffsets")
    public void testHasNext(int chunkLength, int fromOffset, int lengthFromOffset)
    {
        MOCK_CHUNKABLE.setLength(chunkLength);

        assertThat(new Chunker<>(fromOffset, lengthFromOffset, MOCK_CHUNKABLE)
                .hasNext())
                .isTrue();
    }

    @DataProvider
    public Object[][] getOutOfBoundLengthAndOffsets()
    {
        return new Object[][]{
                {0, 0},
                {0, 1},
                {5, 5},
                {5, 6},
                {5, -1}
        };
    }

    @Test(dataProvider = "getOutOfBoundLengthAndOffsets")
    public void testOutOfBoundsHasNext(int chunkCount, int fromOffset)
    {
        MOCK_CHUNKABLE.setLength(chunkCount);

        assertThatThrownBy(() ->
                new Chunker<>(fromOffset, LENGTH_FROM_OFFSET, MOCK_CHUNKABLE))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage(format("index (%d) must be less than size (%d)", fromOffset, chunkCount));
    }

    @Test
    public void testNext()
    {
//        when(MOCK_CHUNKABLE.getLength()).thenReturn(3);
//        when(MOCK_CHUNKABLE.chunk(0, 3)).thenReturn(MOCK_CHUNKABLE);

//        assertThat(new Chunker<>(0, MOCK_CHUNKABLE).next())
    }

    private static class MockChunkable
            implements Chunkable<MockChunk>
    {
        private int chunkCount;
        private List<MockChunk> chunks;

        public MockChunkable(int chunkCount)
        {
            this.chunkCount = chunkCount;
            this.chunks = List.copyOf(nCopies(chunkCount, new MockChunk()));
        }

        public void setLength(int chunkCount)
        {
            this.chunkCount = chunkCount;
            this.chunks = List.copyOf(nCopies(chunkCount, new MockChunk()));
        }

        @Override
        public int getLength()
        {
            return chunkCount;
        }

        @Override
        public MockChunk chunk(int offset, int length)
        {
            return null;
        }
    }

    private static class MockChunk
    {
    }
}
