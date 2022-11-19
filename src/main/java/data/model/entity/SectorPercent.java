package data.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SectorPercent {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Percent")
    private Integer percent;
}
