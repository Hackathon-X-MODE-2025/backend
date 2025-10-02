package dev.zendal.sourcebatch.service.spark;

import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.MetadataBuilder;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataFrameStructureGeneratorImpl implements DataFrameStructureGenerator {
    @Override
    public StructType from(Dataset<Row> dataFrame) {
        StructType schema = dataFrame.schema();

        // Используем итератор, чтобы не загружать все строки в память сразу
        Iterator<Row> it = dataFrame.toLocalIterator();

        // для каждого поля собираем уникальные значения
        Map<String, Set<String>> fieldExamples = Arrays.stream(schema.fieldNames())
                .collect(Collectors.toMap(
                        name -> name,
                        name -> new LinkedHashSet<>()
                ));

        while (it.hasNext()) {
            Row row = it.next();
            for (StructField field : schema.fields()) {
                if (fieldExamples.get(field.name()).size() >= 20) continue;
                Object value = row.getAs(field.name());
                if (value != null) {
                    fieldExamples.get(field.name()).add(value.toString());
                }
            }
        }

        // собираем StructType с examples
        StructField[] newFields = Arrays.stream(schema.fields())
                .map(field -> {
                    MetadataBuilder mb = new MetadataBuilder();
                    mb.withMetadata(field.metadata());
                    mb.putStringArray("examples",
                            fieldExamples.get(field.name()).toArray(new String[0])
                    );
                    return new StructField(
                            field.name(),
                            field.dataType(),
                            field.nullable(),
                            mb.build()
                    );
                })
                .toArray(StructField[]::new);

        return new StructType(newFields);
    }
}
