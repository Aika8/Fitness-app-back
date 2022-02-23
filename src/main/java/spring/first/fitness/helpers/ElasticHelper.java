package spring.first.fitness.helpers;

import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
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
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) filter;
            float i = filter.size();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                    boolQueryBuilder.should(
                            QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()).boost(i)
                    );
                    i--;
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
}
