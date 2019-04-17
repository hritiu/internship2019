package ro.problem.services;

import ro.problem.domain.EmployeeData;
import java.io.IOException;

public interface DataAccessService {

    EmployeeData readData(String filePath) throws IOException;
    void writeData(String filePath, Object output) throws IOException;
}
