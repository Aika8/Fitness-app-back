package spring.first.fitness.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import spring.first.fitness.helpers.ElasticConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = ElasticConstants.POST_INDEX)
public class ElasticPost {
    @Id
    @Field(type = FieldType.Keyword)
    private Long postId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String brief;

    @Field(type = FieldType.Text)
    private String description;
}
