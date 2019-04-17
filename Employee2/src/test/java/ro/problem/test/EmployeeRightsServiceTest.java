package ro.problem.test;

import com.sun.xml.internal.ws.policy.AssertionSet;
import org.junit.Assert;
import org.junit.Test;
import ro.problem.domain.EmployeeData;
import ro.problem.domain.SuspensionPeriod;
import ro.problem.services.EmployeeRightsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRightsServiceTest {

    @Test
    public void testEmployeeRights(){
        EmployeeData employeeData = new EmployeeData();
        EmployeeRightsService employeeRightsService = new EmployeeRightsService();
        SuspensionPeriod suspensionPeriod1 = new SuspensionPeriod();
        SuspensionPeriod suspensionPeriod2 = new SuspensionPeriod();


        Assert.assertNull(suspensionPeriod1.getStartDate());
        Assert.assertNull(suspensionPeriod1.getEndDate());
        Assert.assertNull(suspensionPeriod2.getStartDate());
        Assert.assertNull(suspensionPeriod2.getEndDate());

        employeeData.setEmploymentStartDate(LocalDate.of(2019,8,31));
        employeeData.setEmploymentEndDate(LocalDate.of(2015,8,31));
        Assert.assertEquals(employeeData.getEmploymentStartDate().isBefore(employeeData.getEmploymentEndDate()),false);
        Assert.assertNull(employeeData.getSuspensionPeriodList());

        employeeData.setEmploymentEndDate(LocalDate.of(2019,8,31));
        employeeData.setEmploymentStartDate(LocalDate.of(2015,8,31));
        Assert.assertEquals(employeeData.getEmploymentStartDate().isBefore(employeeData.getEmploymentEndDate()),true);
        Assert.assertNull(employeeData.getSuspensionPeriodList());

        employeeData.setEmploymentStartDate(LocalDate.of(2019,1,1));
        employeeData.setEmploymentEndDate(LocalDate.of(2019,12,31));
        suspensionPeriod1.setStartDate(LocalDate.of(2019,03,01));
        suspensionPeriod1.setStartDate(LocalDate.of(2019,8,31));
        suspensionPeriod2.setStartDate(LocalDate.of(2019,12,30));
        suspensionPeriod2.setEndDate(LocalDate.of(2020,01,20));

        List<SuspensionPeriod> suspentionPeriods=new ArrayList<>();
        suspentionPeriods.add(suspensionPeriod1);
        suspentionPeriods.add(suspensionPeriod2);

        Map<String, Object> freeDays = new HashMap<>();
        freeDays.put("2019",10);
        freeDays.put("2020",20);
        employeeData.setSuspensionPeriodList(suspentionPeriods);

        Assert.assertNotNull(employeeData.getSuspensionPeriodList());
        Assert.assertEquals(employeeData.getSuspensionPeriodList(),suspentionPeriods);



    }
}
