package org.smalldb.orc;

import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.smalldb.page.Page;
import org.smalldb.page.Tuple;

import java.io.Serializable;

public class PageSerde
        implements Serializable
{
    // BytesColumnVector, DecimalColumnVector, DoubleColumnVector, IntervalDayTimeColumnVector, LongColumnVector, MultiValuedColumnVector, StructColumnVector, TimestampColumnVector, UnionColumnVector
    public VectorizedRowBatch serialize(Page page)
    {
        VectorizedRowBatch orcRowBatch = new VectorizedRowBatch(page.getTupleCount());

        // TODO: need schema?
        for (int tupleIndex = 0; tupleIndex < page.getTupleCount(); ++tupleIndex) {
            Tuple<?> curTuple = page.getTupleAt(tupleIndex);
            ColumnVector orcColumn = TupleSerde.serialize(curTuple);
            orcRowBatch.cols[tupleIndex] = orcColumn;
        }

        return orcRowBatch;
    }

    public Page deserialize(VectorizedRowBatch orcRowBatch)
    {
        return null;
    }
}
