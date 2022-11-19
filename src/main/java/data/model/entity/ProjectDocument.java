package data.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import data.model.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProjectDocument implements JsonObject {

    @JsonProperty("DocTypeDesc")
    private String documentTypeDescription;

    @JsonProperty("DocType")
    private String documentType;

    @JsonProperty("EntityID")
    private String entityId;

    @JsonProperty("DocURL")
    private String documentUrl;

    @JsonProperty("DocDate")
    private String documentDate;
}
