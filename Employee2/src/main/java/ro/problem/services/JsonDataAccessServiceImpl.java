package ro.problem.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import ro.problem.domain.EmployeeData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class JsonDataAccessServiceImpl implements DataAccessService {

    private ObjectMapper objectMapper;

    public JsonDataAccessServiceImpl() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public EmployeeData readData(String filePath) throws IOException, DateTimeException, NullPointerException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        JsonNode jsonNodeRoot = objectMapper.readTree(new File(filePath));
        return objectMapper.treeToValue(jsonNodeRoot.get("employeeData"),EmployeeData.class);
    }

    public void writeData(String filePath, Object output) throws IOException {
        Map<String, Object> outputMap =new HashMap<>();
        outputMap.put("output",output);
        objectMapper.writeValue(new File(filePath), outputMap);
    }
}
