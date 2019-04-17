package ro.problem.services;

import ro.problem.domain.EmployeeData;

public class ValidateInputService {

    private String error="";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String validate(EmployeeData employeeData){
        if(employeeData.getEmploymentEndDate().isBefore(employeeData.getEmploymentStartDate()))
            error+="employmentEndDate is before employmentStartDate; ";

        employeeData.getSuspensionPeriodList().stream().forEach(suspensionPeriod ->{
            if(suspensionPeriod.getStartDate().isBefore(employeeData.getEmploymentStartDate()))
                error+="The start of the suspension is before the beginning of the contract; ";
            if(suspensionPeriod.getStartDate().isAfter(employeeData.getEmploymentEndDate()))
                error+="The start of the suspension is after the ending of the contract; ";
            if(suspensionPeriod.getEndDate().isAfter(employeeData.getEmploymentEndDate()))
                error+="The end of the suspension is after the ending of the contract; ";
            if(suspensionPeriod.getEndDate().isBefore(employeeData.getEmploymentStartDate()))
                error+="The end of the suspension is before the beginning of the contract; ";
        });

        if(error.isEmpty())
            error="No errors.";

        return error;
    }
}
