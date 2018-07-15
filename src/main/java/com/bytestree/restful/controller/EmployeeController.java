package com.bytestree.restful.controller;

import com.bytestree.restful.model.Employee;
import com.bytestree.restful.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService empService;

    /**
     * Add new employee
     *
     * @param employee
     * @return Added Employee
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        empService.save(employee);
        logger.debug("Added:: " + employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
    }


    /**
     * Get All Employees in DB
     *
     * @return List of all Employees
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = empService.getAll();
        if (employees.isEmpty()) {
            logger.debug("Employees does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + employees.size() + " Employees");
        logger.debug(Arrays.toString(employees.toArray()));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
