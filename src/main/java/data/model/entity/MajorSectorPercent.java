package data.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import data.model.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MajorSectorPercent implements JsonObject {

    @JsonProperty("Name")
    private String name;

    @JsonRawValue
    @JsonProperty("Percent")
    private String percent;
}
