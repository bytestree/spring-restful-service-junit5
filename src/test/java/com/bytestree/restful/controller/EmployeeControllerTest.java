package com.bytestree.restful.controller;

import com.bytestree.restful.TestUtils;
import com.bytestree.restful.model.Employee;
import com.bytestree.restful.service.EmployeeService;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService empService;

    private final String URL = "/employee/";

    @Test
    public void testAddEmployee() throws Exception {

        // prepare data and mock's behaviour
        Employee empStub = new Employee(1l, "bytes", "tree", "developer", 12000);
        when(empService.save(any(Employee.class))).thenReturn(empStub);

        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(empStub))).andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status, "Incorrect Response Status");

        // verify that service method was called once
        verify(empService).save(any(Employee.class));

        Employee resultEmployee = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Employee.class);
        assertNotNull(resultEmployee);
        assertEquals(1l, resultEmployee.getId().longValue());

    }

    @Test
    public void testGetAllEmployee() throws Exception {

        // prepare data and mock's behaviour
        List<Employee> empList = buildEmployees();
        when(empService.getAll()).thenReturn(empList);

        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");

        // verify that service method was called once
        verify(empService).getAll();

        // get the List<Employee> from the Json response
        TypeToken<List<Employee>> token = new TypeToken<>() {
        };
        @SuppressWarnings("unchecked")
        List<Employee> empListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

        assertNotNull(empListResult, "Employees not found");
        assertEquals(empList.size(), empListResult.size(), "Incorrect Employee List");

    }

    private List<Employee> buildEmployees() {
        Employee e1 = new Employee(1l, "bytes", "tree", "developer", 12000);
        Employee e2 = new Employee(2l, "bytes2", "tree2", "Senior developer", 16000);
        List<Employee> empList = Arrays.asList(e1, e2);
        return empList;
    }

}
