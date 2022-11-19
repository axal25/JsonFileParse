package data.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.model.JsonObject;
import data.model.entity.*;
import lombok.*;
import read.JsonProvider;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RecordDto implements JsonObject {

    private static final Pattern SECTOR_REG_EXP = Pattern.compile("^sector[0-9]*$");
    private static final Pattern THEME_REG_EXP = Pattern.compile("^theme[0-9]*$");

    private UnderscoreId underscoreId;
    private String approvalFiscalYear;
    private String boardApprovalMonth;
    private String boardApprovalDate;
    private String borrower;
    private String projectClosingDate;
    private String countryNameCode;
    private String countryCode;
    private String countryName;
    private String countryShortName;
    private String documentType;
    private String environmentAssessmentCategoryCode;
    private String grantAmount;
    private String ibrdCommitmentAmount;
    private String id;
    private String idaCommitmentAmount;
    private String implementingAgency;
    private String lendingInstrument;
    private String lendingInstrumentType;
    private String lendingProjectCost;
    private List<MajorSectorPercent> majorSectorPercents;
    private List<MajorSectorNameCode> majorSectorNameCodes;
    private List<String> majorThemes;
    private List<MajorThemeNameCode> majorThemeNameCodes;
    private String majorThemeCode;
    private String productLine;
    private String productLineText;
    private String productLineType;
    private ProjectAbstract projectAbstractDevelopmentObjective;
    private String projectName;
    private List<ProjectDocument> projectDocuments;
    private String projectFinancialType;
    private String projectStatusDisplay;
    private String regionName;
    private List<Sector> sectors;
    private List<SectorPercent> sectorPercents;
    private List<SectorNameCode> sectorNameCodes;
    private String sectorCode;
    private String source;
    private String status;
    private String supplementProjectFlag;
    private List<Theme> themes;
    private List<ThemeNameCode> themeNameCodes;
    private String themeCode;
    private String totalProjectCostAmount;
    private String totalIdaAndIbrdCommitmentAmount;
    private String url;
    private Map<String, Object> unknownFields;

    public RecordDto(Record record) {
        underscoreId = record.getUnderscoreId();
        approvalFiscalYear = record.getApprovalFiscalYear();
        boardApprovalMonth = record.getBoardApprovalMonth();
        boardApprovalDate = record.getBoardApprovalDate();
        borrower = record.getBorrower();
        projectClosingDate = record.getProjectClosingDate();
        countryNameCode = record.getCountryNameCode();
        countryCode = record.getCountryCode();
        countryName = record.getCountryName();
        countryShortName = record.getCountryShortName();
        documentType = record.getDocumentType();
        environmentAssessmentCategoryCode = record.getEnvironmentAssessmentCategoryCode();
        grantAmount = record.getGrantAmount();
        ibrdCommitmentAmount = record.getIbrdCommitmentAmount();
        id = record.getId();
        idaCommitmentAmount = record.getIdaCommitmentAmount();
        implementingAgency = record.getImplementingAgency();
        lendingInstrument = record.getLendingInstrument();
        lendingInstrumentType = record.getLendingInstrumentType();
        lendingProjectCost = record.getLendingProjectCost();
        majorSectorPercents = record.getMajorSectorPercents();
        majorSectorNameCodes = record.getMajorSectorNameCodes();
        majorThemes = record.getMajorThemes();
        majorThemeNameCodes = record.getMajorThemeNameCodes();
        majorThemeCode = record.getMajorThemeCode();
        productLine = record.getProductLine();
        productLineText = record.getProductLineText();
        productLineType = record.getProductLineType();
        projectAbstractDevelopmentObjective = record.getProjectAbstractDevelopmentObjective();
        projectName = record.getProjectName();
        projectDocuments = record.getProjectDocuments();
        projectFinancialType = record.getProjectFinancialType();
        projectStatusDisplay = record.getProjectStatusDisplay();
        regionName = record.getRegionName();
        sectors = record.getSectors();
        sectorNameCodes = record.getSectorNameCodes();
        sectorCode = record.getSectorCode();
        source = record.getSource();
        status = record.getStatus();
        supplementProjectFlag = record.getSupplementProjectFlag();
        themeNameCodes = record.getThemeNameCodes();
        themeCode = record.getThemeCode();
        totalProjectCostAmount = record.getTotalProjectCostAmount();
        totalIdaAndIbrdCommitmentAmount = record.getTotalIdaAndIbrdCommitmentAmount();
        url = record.getUrl();

        sectorPercents = record.getUnknownFields().entrySet().stream()
                .filter(jsonNameToValue -> SECTOR_REG_EXP.matcher(jsonNameToValue.getKey()).matches())
                .map(Map.Entry::getValue)
                .map(jsonValue -> {
                    try {
                        ObjectMapper objectMapper = JsonProvider.getObjectMapper();
                        String jsonString = objectMapper.writeValueAsString(jsonValue);
                        return objectMapper.readValue(jsonString, SectorPercent.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        themes = record.getUnknownFields().entrySet().stream()
                .filter(jsonNameToValue -> THEME_REG_EXP.matcher(jsonNameToValue.getKey()).matches())
                .map(Map.Entry::getValue)
                .map(jsonValue -> {
                    try {
                        ObjectMapper objectMapper = JsonProvider.getObjectMapper();
                        String jsonString = objectMapper.writeValueAsString(jsonValue);
                        return objectMapper.readValue(jsonString, Theme.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        unknownFields = record.getUnknownFields().entrySet().stream()
                .filter(jsonNameToValue -> !SECTOR_REG_EXP.matcher(jsonNameToValue.getKey()).matches()
                        && !THEME_REG_EXP.matcher(jsonNameToValue.getKey()).matches())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
