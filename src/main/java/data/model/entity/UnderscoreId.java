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
public class UnderscoreId implements JsonObject {

    @JsonProperty("$oid")
    private String dollarOId;
}
