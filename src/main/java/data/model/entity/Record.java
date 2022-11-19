package data.model.entity;

import com.fasterxml.jackson.annotation.*;
import data.model.JsonObject;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Record implements JsonObject {

    @JsonProperty("_id")
    private UnderscoreId underscoreId;

    @JsonRawValue
    @JsonProperty("approvalfy")
    private String approvalFiscalYear;

    @JsonProperty("board_approval_month")
    private String boardApprovalMonth;

    @JsonProperty("boardapprovaldate")
    private String boardApprovalDate;

    @JsonProperty("borrower")
    private String borrower;

    @JsonProperty("closingdate")
    private String projectClosingDate;

    @JsonProperty("country_namecode")
    private String countryNameCode;

    @JsonProperty("countrycode")
    private String countryCode;

    @JsonProperty("countryname")
    private String countryName;

    @JsonProperty("countryshortname")
    private String countryShortName;

    @JsonProperty("docty")
    private String documentType;

    @JsonProperty("envassesmentcategorycode")
    private String environmentAssessmentCategoryCode;

    @JsonRawValue
    @JsonProperty("grantamt")
    private String grantAmount;

    @JsonRawValue
    @JsonProperty("ibrdcommamt")
    private String ibrdCommitmentAmount;

    @JsonProperty("id")
    private String id;

    @JsonRawValue
    @JsonProperty("idacommamt")
    private String idaCommitmentAmount;

    @JsonProperty("impagency")
    private String implementingAgency;

    @JsonProperty("lendinginstr")
    private String lendingInstrument;

    @JsonProperty("lendinginstrtype")
    private String lendingInstrumentType;

    @JsonRawValue
    @JsonProperty("lendprojectcost")
    private String lendingProjectCost;

    @JsonProperty("majorsector_percent")
    private List<MajorSectorPercent> majorSectorPercents;

    @JsonProperty("mjsector_namecode")
    private List<MajorSectorNameCode> majorSectorNameCodes;

    @JsonProperty("mjtheme")
    private List<String> majorThemes;

    @JsonProperty("mjtheme_namecode")
    private List<MajorThemeNameCode> majorThemeNameCodes;

    @JsonProperty("mjthemecode")
    private String majorThemeCode;

    @JsonProperty("prodline")
    private String productLine;

    @JsonProperty("prodlinetext")
    private String productLineText;

    @JsonProperty("productlinetype")
    private String productLineType;

    @JsonProperty("project_abstract")
    private ProjectAbstract projectAbstractDevelopmentObjective;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("projectdocs")
    private List<ProjectDocument> projectDocuments;

    @JsonProperty("projectfinancialtype")
    private String projectFinancialType;

    @JsonProperty("projectstatusdisplay")
    private String projectStatusDisplay;

    @JsonProperty("regionname")
    private String regionName;

    @JsonProperty("sector")
    private List<Sector> sectors;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> unknownFields = new HashMap<>();

    // TODO: WE NEED ALL METHODS?
    @JsonAnyGetter
    @JsonPropertyOrder(alphabetic = true)
    public Map<String, Object> getUnknownFields() {
        return unknownFields;
    }

    @JsonAnyGetter
    @JsonPropertyOrder(alphabetic = true)
    public Object getUnknownField(String name) {
        return unknownFields.get(name);
    }

    @JsonAnySetter
    @JsonIgnore
    public void setUnknownFields(String name, Object value) {
        unknownFields.put(name, value);
    }

    @JsonProperty("sector_namecode")
    private List<SectorNameCode> sectorNameCodes;

    @JsonProperty("sectorcode")
    private String sectorCode;

    @JsonProperty("source")
    private String source;

    @JsonProperty("status")
    private String status;

    @JsonProperty("supplementprojectflg")
    private String supplementProjectFlag;

    @JsonProperty("theme_namecode")
    private List<ThemeNameCode> themeNameCodes;

    @JsonProperty("themecode")
    private String themeCode;

    @JsonRawValue
    @JsonProperty("totalamt")
    private String totalProjectCostAmount;

    @JsonRawValue
    @JsonProperty("totalcommamt")
    private String totalIdaAndIbrdCommitmentAmount;

    @JsonProperty("url")
    private String url;
}
