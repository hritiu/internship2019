package ro.problem.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeData {

    private LocalDate employmentStartDate;
    private LocalDate employmentEndDate;
    private List<SuspensionPeriod> suspensionPeriodList;

    public LocalDate getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(LocalDate employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public LocalDate getEmploymentEndDate() {
        return employmentEndDate;
    }

    public void setEmploymentEndDate(LocalDate employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public List<SuspensionPeriod> getSuspensionPeriodList() {
        return suspensionPeriodList;
    }

    public void setSuspensionPeriodList(List<SuspensionPeriod> suspensionPeriodList) {
        this.suspensionPeriodList = suspensionPeriodList;
    }
}
