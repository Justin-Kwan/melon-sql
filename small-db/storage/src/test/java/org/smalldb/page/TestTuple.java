package org.smalldb.page;

import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TestTuple
{
    private static final RecordId TEST_TUPLE_ID = new RecordId();

    public static class TestBooleanTuple
    {
        private static final BooleanTuple TEST_BOOLEAN_TUPLE = BooleanTuple.create(
                TEST_TUPLE_ID,
                new boolean[]{true, true, true, false},
                new boolean[]{false, false, false, false});

        @Test
        public void testChunk()
        {
            BooleanTuple tupleChunk = TEST_BOOLEAN_TUPLE.chunk(0, 2);

            assertThat(tupleChunk.getValueRange())
                    .isEqualToComparingFieldByField(new Tuple.ValueRange(0, 2));

            assertThat(tupleChunk.getValues())
                    .isEqualTo(TEST_BOOLEAN_TUPLE.getValues());

            assertThat(tupleChunk.getValueNullities())
                    .isEqualTo(TEST_BOOLEAN_TUPLE.getValueNullities());

            assertThatThrownBy(() -> TEST_BOOLEAN_TUPLE.chunk(-1, 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("new range '(-1, 1)' must be within current range '(0, 3)'");

            assertThatThrownBy(() -> TEST_BOOLEAN_TUPLE.chunk(4, 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("new range '(4, 6)' must be within current range '(0, 3)'");

            assertThatThrownBy(() -> TEST_BOOLEAN_TUPLE.chunk(0, 4))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("new range '(0, 4)' must be within current range '(0, 3)'");

            assertThatThrownBy(() -> TEST_BOOLEAN_TUPLE.chunk(1, -3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("tuple starting index '1' must be less or equal to ending index '-2'");
        }

        @Test
        public void testGetValuesAt()
        {
            assertThat(TEST_BOOLEAN_TUPLE.getValueAt(0))
                    .isEqualTo(true);

            assertThat(TEST_BOOLEAN_TUPLE.getValueAt(3))
                    .isEqualTo(false);

            assertThatThrownBy(() -> TEST_BOOLEAN_TUPLE.getValueAt(-1))
                    .isInstanceOf(IndexOutOfBoundsException.class);

            assertThatThrownBy(() -> TEST_BOOLEAN_TUPLE.getValueAt(4))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }
}
