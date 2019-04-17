package ro.problem;

import ro.problem.domain.EmployeeData;
import ro.problem.domain.OutputData;
import ro.problem.services.DataAccessService;
import ro.problem.services.EmployeeRightsService;
import ro.problem.services.JsonDataAccessServiceImpl;
import ro.problem.services.ValidateInputService;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String fileInputPath="D:\\Faculta\\Info\\Other Stuff\\COERA\\internship2019\\Employee2\\src\\main\\resources\\input.json";
        String fileOutputPath="D:\\Faculta\\Info\\Other Stuff\\COERA\\internship2019\\Employee2\\src\\main\\resources\\output.json";

        DataAccessService dataAccessService = new JsonDataAccessServiceImpl();
        ValidateInputService validateInputService = new ValidateInputService();
        OutputData outputData = new OutputData();
        EmployeeData employeeData = null;
        try {
            employeeData = dataAccessService.readData(fileInputPath);
            outputData.setErrorMessage(validateInputService.validate(employeeData));
            //outputData.setErrorMessage("no errors");
        }catch (IOException ioe){
            outputData.setErrorMessage("There is an invalid date in the input file.");
        }catch (NullPointerException npe){
            outputData.setErrorMessage("Invalid input.");
        }

        if(employeeData != null){
            EmployeeRightsService employeeRightsService = null;
            if(Objects.equals(outputData.getErrorMessage(),"No errors.")){
                employeeRightsService = new EmployeeRightsService();
                employeeRightsService.processHolidayRights(employeeData);
            }

            //System.out.println(employeeRightsService.processHolidayRights(employeeData));
    //        List<Object> holidayRightsPerYearList = new ArrayList<>();
    //        holidayRightsPerYearList.add(employeeRightsService.processHolidayRights(employeeData));
    //        Map<String, Object> outputContent = new HashMap<>();
    //        String errorMessage = "string";
    //        outputContent.put("errorMessage", errorMessage);
    //        outputContent.put("holidayRightsPerYearList", holidayRightsPerYearList);
    //        dataAccessService.writeData(fileOutputPath, outputContent);


          //  ValidateInputService validateInputService = new ValidateInputService();

           // OutputData outputData = new OutputData();
            //outputData.setErrorMessage(validateInputService.validate(employeeData));

            //if(Objects.equals(outputData.getErrorMessage(),"no errors"))
    //        outputData.setErrorMessage("string");
            if(Objects.equals(outputData.getErrorMessage(),"No errors.") && employeeRightsService != null)
                outputData.setHolidayRightsPerYearList(employeeRightsService.convertToOutputList(employeeData));
            else
                outputData.setHolidayRightsPerYearList(new ArrayList<>());
            dataAccessService.writeData(fileOutputPath, outputData);
            }
        else {
            outputData.setHolidayRightsPerYearList(new ArrayList<>());
            dataAccessService.writeData(fileOutputPath,outputData);
        }
    }
}
