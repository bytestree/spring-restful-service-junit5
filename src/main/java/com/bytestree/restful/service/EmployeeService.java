package com.bytestree.restful.service;

import com.bytestree.restful.model.Employee;

import java.util.List;

/**
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 */
public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getAll();
}
