package org.smalldb.orc;

import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.smalldb.page.Tuple;

public class TupleSerde
{
    private TupleSerde()
    {
    }

    public static ColumnVector serialize(Tuple<?> tuple)
    {
        return switch (tuple.getDataType()) {
            case INT, TINYINT, BIGINT, DECIMAL, SMALLINT -> null;
            case BOOLEAN -> BooleanTupleSerde.serialize((Tuple<Boolean>) tuple);
            case VARCHAR, NULL -> null;
        };
    }

    private static class BooleanTupleSerde
    {
        // something similar to strategy pattern?
        public static LongColumnVector serialize(Tuple<Boolean> tuple)
        {
            LongColumnVector orcBooleanColumn = new LongColumnVector();

            for (int valueIndex = 0; valueIndex < tuple.getLength(); ++valueIndex) {
                long booleanValue = tuple.getValueAt(valueIndex) ? 1 : 0;
                orcBooleanColumn.vector[valueIndex] = booleanValue;
                orcBooleanColumn.isNull[valueIndex] = tuple.isValueNullAt(valueIndex);
            }

            return orcBooleanColumn;
        }
    }
}
