package org.smalldb.page;

import com.google.common.primitives.Booleans;
import org.smalldb.datatype.DataType;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static java.util.Objects.requireNonNull;

public class BooleanTuple
        extends Tuple<Boolean>
{
    private final boolean[] values;
    private final boolean[] valueNullity;

    private BooleanTuple(RecordId id, ValueRange valueRange, boolean[] values, boolean[] valueNullity)
    {
        super(id, DataType.BOOLEAN, valueRange);

        checkArgument(values.length == valueNullity.length);
        checkElementIndex(valueRange.fromOffset(), values.length);
        checkElementIndex(valueRange.toOffset(), values.length);

        this.values = values;
        this.valueNullity = valueNullity;
    }

    public static BooleanTuple ofEmpty(RecordId id)
    {
        return new BooleanTuple(
                id,
                new ValueRange(0, 0),
                new boolean[0],
                new boolean[0]);
    }

    public static BooleanTuple create(RecordId id, boolean[] values, boolean[] valueNullity)
    {
        return new BooleanTuple(
                id,
                new ValueRange(0, values.length - 1),
                requireNonNull(values, "values is null").clone(),
                requireNonNull(valueNullity, "valueNullity is null").clone());
    }

    @Override
    public List<Boolean> getValues()
    {
        return Booleans.asList(values);
    }

    public List<Boolean> getValueNullities()
    {
        return Booleans.asList(valueNullity);
    }

    @Override
    public boolean isValueNullAt(int index)
    {
        return valueNullity[index];
    }

    @Override
    public Boolean getValueAt(int index)
    {
        verifyIndicesWithinValuesRange(index);
        return values[index];
    }

    @Override
    public int getLength()
    {
        return values.length;
    }

    // TODO: fix
    @Override
    public int getSizeInBytes()
    {
        return values.length;
    }

    @Override
    public BooleanTuple chunk(int fromOffset, int length)
    {
        return new BooleanTuple(
                getId(),
                getValueRange().createSubrange(fromOffset, fromOffset + length),
                values,
                valueNullity);
    }
}
