package ro.problem.test;

import org.junit.Assert;
import org.junit.Test;
import ro.problem.domain.EmployeeData;
import ro.problem.services.DataAccessService;
import ro.problem.services.JsonDataAccessServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccessServiceTest  {

    @Test
    public void testReadData() throws IOException {
        DataAccessService dataAccessService = new JsonDataAccessServiceImpl();
        EmployeeData employeeData = dataAccessService.readData("D:\\Faculta\\Info\\Other Stuff\\COERA\\internship2019\\Employee2\\src\\test\\resources\\input.json");

        Assert.assertNotNull(employeeData);
        Assert.assertNotNull(employeeData.getEmploymentStartDate());
        Assert.assertNotNull(employeeData.getEmploymentEndDate());
        Assert.assertNotNull(employeeData.getSuspensionPeriodList());

        Assert.assertEquals("2019-01-01", employeeData.getEmploymentStartDate().toString());
        Assert.assertEquals("2020-12-31", employeeData.getEmploymentEndDate().toString());

        Assert.assertEquals("2019-03-01",employeeData.getSuspensionPeriodList().get(0).getStartDate().toString());
        Assert.assertEquals("2019-08-31",employeeData.getSuspensionPeriodList().get(0).getEndDate().toString());

        Assert.assertEquals("2019-12-30",employeeData.getSuspensionPeriodList().get(1).getStartDate().toString());
        Assert.assertEquals("2020-01-20",employeeData.getSuspensionPeriodList().get(1).getEndDate().toString());
    }

}
