package org.smalldb.page;

import org.smalldb.datatype.DataType;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public abstract class Tuple<T>
        implements Chunkable<Tuple<T>>
{
    private final RecordId id;
    private final DataType dataType;
    private final ValueRange valueRange;

    protected Tuple(RecordId id, DataType dataType, ValueRange valueRange)
    {
        this.id = requireNonNull(id, "id is null");
        this.dataType = requireNonNull(dataType, "dataType is null");
        this.valueRange = requireNonNull(valueRange, "valueRange is null");
    }

    public RecordId getId()
    {
        return id;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public int getLength()
    {
        return valueRange.getLength();
    }

    public abstract List<T> getValues();

    // TODO: update value incrementally
    public abstract int getSizeInBytes();

    public abstract T getValueAt(int index);

    public abstract boolean isValueNullAt(int index);

    @Override
    public abstract Tuple<T> chunk(int offset, int length);

    protected ValueRange getValueRange()
    {
        return valueRange;
    }

    protected void verifyIndicesWithinValuesRange(int... indices)
    {
        for (int index : indices) {
            checkElementIndex(index, getLength());
        }
    }

    protected record ValueRange(int fromOffset, int toOffset)
    {
        public ValueRange
        {
            checkArgument(fromOffset <= toOffset, format(
                    "tuple starting index '%d' must be less or equal to ending index '%d'",
                    fromOffset,
                    toOffset));
        }

        public ValueRange createSubrange(int fromOffset, int toOffset)
        {
            checkArgument(
                    this.fromOffset <= fromOffset && toOffset <= this.toOffset,
                    format(
                            "new range '(%d, %d)' must be within current range '(%d, %d)'",
                            fromOffset,
                            toOffset,
                            this.fromOffset,
                            this.toOffset));

            return new ValueRange(fromOffset, toOffset);
        }

        public int getLength()
        {
            return abs(toOffset - fromOffset) + 1;
        }
    }

    public class Iterator
    {
    }
}
