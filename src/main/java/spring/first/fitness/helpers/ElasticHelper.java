package spring.first.fitness.helpers;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import spring.first.fitness.util.DateUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class ElasticHelper {


    private ElasticHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static NativeSearchQueryBuilder nativeQueryBuilder(Map<String, Object> filter,
                                                              Pageable pageable,
                                                              String idxName) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        applyFilterMap(filter, boolQueryBuilder, idxName);
        applyPageable(pageable, queryBuilder);

        queryBuilder.withQuery(boolQueryBuilder);
        return queryBuilder;
    }


    public static void applyFilterMap(Map<String, Object> filter, BoolQueryBuilder boolQueryBuilder, String idxName) {
        if (filter != null && !filter.isEmpty()) {
            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                boolQueryBuilder.must(
                        isKeyword(idxName, entry.getKey()) ?
                                QueryBuilders.termQuery(entry.getKey(), entry.getValue()) :
                                isDate(entry.getValue()) ?
                                        QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()) :
                                        isBool(entry.getValue()) ?
                                                QueryBuilders.termQuery(entry.getKey(), entry.getValue()) :
                                                isNumeric(idxName, entry.getKey(), entry.getValue()) ?
                                                        QueryBuilders.termQuery(entry.getKey(), entry.getValue()) :

                                                        QueryBuilders.matchPhrasePrefixQuery(entry.getKey(), entry.getValue())
                );
            }
        }
    }


    public static void applyPageable(Pageable pageable,
                                     NativeSearchQueryBuilder queryBuilder) {
        if (pageable != null && pageable.isPaged()) {
            queryBuilder.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
        }
    }


    private static boolean isKeyword(String idxName, String fieldName) {
        Set<Class<?>> idxClasses = ElasticConstants.getByIdxName(idxName);

        for (Class<?> c : idxClasses) {
            Field field;

            try {
                field = c.getDeclaredField(fieldName);

                org.springframework.data.elasticsearch.annotations.Field typeAnn =
                        field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);

                return typeAnn != null && typeAnn.type().equals(FieldType.Keyword);
            } catch (NoSuchFieldException ignored) {
            }
        }

        return false;
    }

    private static boolean isDate(Object fieldVal) {
        if (fieldVal instanceof String)
            return DateUtil.isValidDate((String) fieldVal);

        return false;
    }

    private static boolean isBool(Object fieldVal) {
        return fieldVal instanceof Boolean ||
                "true".equals(fieldVal) ||
                "false".equals(fieldVal);
    }

    private static boolean isNumeric(String idxName, String fieldName, Object fieldVal) {
        if (fieldVal instanceof Integer ||
                fieldVal instanceof Double ||
                fieldVal instanceof Float) {
            Set<Class<?>> idxClasses = ElasticConstants.getByIdxName(idxName);

            for (Class<?> c : idxClasses) {
                Field field;

                try {
                    field = c.getDeclaredField(fieldName);

                    org.springframework.data.elasticsearch.annotations.Field typeAnn =
                            field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);

                    return typeAnn != null && (
                            typeAnn.type().equals(FieldType.Integer) ||
                                    typeAnn.type().equals(FieldType.Float) ||
                                    typeAnn.type().equals(FieldType.Double)
                    );
                } catch (NoSuchFieldException ignored) {
                }
            }
        }

        return false;
    }


}
