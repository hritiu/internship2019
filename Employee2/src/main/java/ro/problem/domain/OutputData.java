package ro.problem.domain;

import java.util.List;

public class OutputData {
    String errorMessage;
    List<Object> holidayRightsPerYearList;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Object> getHolidayRightsPerYearList() {
        return holidayRightsPerYearList;
    }

    public void setHolidayRightsPerYearList(List<Object> holidayRightsPerYearList) {
        this.holidayRightsPerYearList = holidayRightsPerYearList;
    }
}
