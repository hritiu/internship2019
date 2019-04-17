package ro.problem.services;

import javafx.util.Pair;
import ro.problem.domain.EmployeeData;
import ro.problem.domain.SuspensionPeriod;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRightsService {
    private final int MIN_HOLIDAY_RIGHT = 20;
    private final int MAX_HOLIDAY_RIGHT = 24;

    public Map<String, Object> processHolidayRights(EmployeeData employeeData) {
        Map<String, Long>  suspensionDays = this.getSuspensionDaysPerYear(employeeData.getSuspensionPeriodList());
        Map<String, Object> holidayRights =new HashMap<>();
        long workingDays = -1;
        int holiday=-1;

        if(employeeData.getEmploymentStartDate().getYear()==employeeData.getEmploymentEndDate().getYear()){
            workingDays = ChronoUnit.DAYS.between(employeeData.getEmploymentStartDate(),
                    employeeData.getEmploymentEndDate());//+ 1;
            if(suspensionDays.containsKey(Integer.toString(employeeData.getEmploymentStartDate().getYear())))
                workingDays-=suspensionDays.get(Integer.toString(employeeData.getEmploymentStartDate().getYear()));

            holiday = Math.round((float)MIN_HOLIDAY_RIGHT*workingDays/LocalDate.of(employeeData.
                    getEmploymentStartDate().getYear(),12,31).getDayOfYear());

            holidayRights.put(Integer.toString(employeeData.getEmploymentStartDate().getYear()),holiday);
        }
        else {
            int startYear = employeeData.getEmploymentStartDate().getYear();
            int endYear = employeeData.getEmploymentEndDate().getYear();
            int maximumFreeDays = MIN_HOLIDAY_RIGHT;

            while(startYear<endYear){
                if(maximumFreeDays == 20)
                    workingDays = ChronoUnit.DAYS.between(employeeData.getEmploymentStartDate(),LocalDate.of(
                            startYear,12,31));
                else
                    //workingDays = ChronoUnit.DAYS.between(LocalDate.of(startYear,01,01),
                    //      LocalDate.of(startYear,12,31));
                    workingDays = LocalDate.of(startYear, 12, 31).getDayOfYear();


                if(suspensionDays.containsKey(Integer.toString(startYear))) {
                    //System.out.println("year= "+startYear+" workindDaysBefore= "+workingDays);
                    workingDays -= suspensionDays.get(Integer.toString(startYear));
                   // System.out.println("year= "+startYear+" workindDaysAfter= "+workingDays);
                }

                holiday = Math.round((float)maximumFreeDays*workingDays/LocalDate.of(startYear,12,31)
                .getDayOfYear());

                holidayRights.put(Integer.toString(startYear),holiday);

                startYear+=1;
                if(maximumFreeDays < MAX_HOLIDAY_RIGHT)
                    maximumFreeDays+= 1;
            }

            //workingDays = ChronoUnit.DAYS.between(LocalDate.of(startYear,01,01),
              //      employeeData.getEmploymentEndDate());
            workingDays = employeeData.getEmploymentEndDate().getDayOfYear();

            if(suspensionDays.containsKey(Integer.toString(startYear)))
                workingDays -= suspensionDays.get(Integer.toString(startYear));
            holiday = Math.round((float)maximumFreeDays*workingDays/LocalDate.of(startYear,12,31)
            .getDayOfYear());

            holidayRights.put(Integer.toString(startYear),holiday);
        }

        return holidayRights;
    }

    private List<SuspensionPeriod> transformSuspension(List<SuspensionPeriod> suspensionPeriod){
        ///this function concatenates the suspension periods that overlap

        List<SuspensionPeriod> transformedSuspensions = new ArrayList<>();
        List<Integer> positionsToBeDeleted= new ArrayList<>();
        int position=0;

        while(position < suspensionPeriod.size()){
            if(!positionsToBeDeleted.contains(position)){
                transformedSuspensions.add(suspensionPeriod.get(position));

                for(int i=0; i<suspensionPeriod.size();i++){
                    if((transformedSuspensions.get(transformedSuspensions.size()-1).getStartDate()
                            .isAfter(suspensionPeriod.get(i).getStartDate()) &&
                            transformedSuspensions.get(transformedSuspensions.size()-1).getStartDate()
                                    .isBefore(suspensionPeriod.get(i).getEndDate())) ||
                            transformedSuspensions.get(transformedSuspensions.size()-1).getStartDate().isEqual(
                                    suspensionPeriod.get(i).getStartDate()) ||
                            transformedSuspensions.get(transformedSuspensions.size()-1).getStartDate().isEqual(
                                    suspensionPeriod.get(i).getEndDate())){

                        if(transformedSuspensions.get(transformedSuspensions.size()-1).getStartDate().isAfter(
                                suspensionPeriod.get(i).getStartDate()))
                            transformedSuspensions.get(transformedSuspensions.size()-1).setStartDate(
                                    suspensionPeriod.get(i).getStartDate());

                        if(transformedSuspensions.get(transformedSuspensions.size()-1).getEndDate().isBefore(
                                suspensionPeriod.get(i).getEndDate()))
                            transformedSuspensions.get(transformedSuspensions.size()-1).setEndDate(
                                    suspensionPeriod.get(i).getEndDate());

                        positionsToBeDeleted.add(i);
                    }
                    else if((transformedSuspensions.get(transformedSuspensions.size()-1).getEndDate().isBefore(
                            suspensionPeriod.get(i).getEndDate()) &&
                            transformedSuspensions.get(transformedSuspensions.size()-1).getEndDate().isAfter(
                                    suspensionPeriod.get(i).getStartDate())) ||
                            transformedSuspensions.get(transformedSuspensions.size()-1).getEndDate().isEqual(
                                    suspensionPeriod.get(i).getEndDate()) ||
                            transformedSuspensions.get(transformedSuspensions.size()-1).getEndDate().isEqual(
                                    suspensionPeriod.get(i).getStartDate())){

                        if(transformedSuspensions.get(transformedSuspensions.size()-1).getStartDate().isAfter(
                                suspensionPeriod.get(i).getStartDate()))
                            transformedSuspensions.get(transformedSuspensions.size()-1).setStartDate(
                                    suspensionPeriod.get(i).getStartDate());

                        if(transformedSuspensions.get(transformedSuspensions.size()-1).getEndDate().isBefore(
                                suspensionPeriod.get(i).getEndDate()))
                            transformedSuspensions.get(transformedSuspensions.size()-1).setEndDate(
                                    suspensionPeriod.get(i).getEndDate());

                        positionsToBeDeleted.add(i);
                    }
                }

            }
            positionsToBeDeleted.add(position);

            position+=1;
        }
        return transformedSuspensions;
    }

    private Map<String, Long> getSuspensionDaysPerYear(List<SuspensionPeriod> suspensionPeriod) {
        Map<String,Long> suspensionDays = new HashMap<>();

        List<SuspensionPeriod> suspensionPeriodList = transformSuspension(suspensionPeriod);

        suspensionPeriodList.stream()
                .forEach(e->{
                        if(e.getStartDate().getYear()==e.getEndDate().getYear()){
                            if(suspensionDays.containsKey(Integer.toString(e.getStartDate().getYear()))){
                                suspensionDays.put(Integer.toString(e.getStartDate().getYear()),
                                        ChronoUnit.DAYS.between(e.getStartDate(),e.getEndDate()) +
                                suspensionDays.get(Integer.toString(e.getStartDate().getYear())));
                            }
                            else
                                suspensionDays.put(Integer.toString(e.getStartDate().getYear()),ChronoUnit.DAYS
                                    .between(e.getStartDate(), e.getEndDate()));
                        }
                        else{
                            int startYear = e.getStartDate().getYear();
                            int endYear = e.getEndDate().getYear();

                            if(e.getStartDate().getYear() <= e.getEndDate().getYear()){

                                while(startYear<endYear){
                                    suspensionDays.put(Integer.toString(startYear),ChronoUnit.DAYS
                                            .between(e.getStartDate(), LocalDate.of(startYear,12,31)) +
                                            suspensionDays.get(Integer.toString(e.getStartDate().getYear())));
                                    startYear+=1;
                                }
                                suspensionDays.put(Integer.toString(startYear),ChronoUnit.DAYS
                                        .between(LocalDate.of(startYear,01,01),e.getEndDate()) +
                                                suspensionDays.get(Integer.toString(e.getStartDate().getYear())));
                            }

                            while(startYear<endYear){
                                suspensionDays.put(Integer.toString(startYear),ChronoUnit.DAYS
                                .between(e.getStartDate(), LocalDate.of(startYear,12,31)));
                                startYear+=1;
                            }
                            suspensionDays.put(Integer.toString(startYear),ChronoUnit.DAYS
                            .between(LocalDate.of(startYear,01,01),e.getEndDate()));
                        }
                });

        return suspensionDays;
    }

    public List<Object> convertToOutputList(EmployeeData employeeData){
        Map<String, Object> holidayRights = processHolidayRights(employeeData);
        List<Object> outputList = new ArrayList<>();

        for (String key: holidayRights.keySet()) {
            //Pair<String, String> year = new Pair<>("year",key);
            //Pair<String, String> holidayDays = new Pair<>("holidayDays",holidayRights.get(key).toString());
            Map<String, String> element = new HashMap<>();
            element.put("year",key);
            element.put("holidayDays",holidayRights.get(key).toString());
            outputList.add(element);
        }

        return outputList;
    }
}
