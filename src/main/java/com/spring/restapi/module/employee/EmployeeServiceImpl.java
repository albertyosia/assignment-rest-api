package com.spring.restapi.module.employee;

import com.spring.restapi.exceptions.DepartmentNotFoundException;
import com.spring.restapi.exceptions.ManagerNotFoundException;
import com.spring.restapi.exceptions.RestStatus;
import com.spring.restapi.exceptions.UsernameNotFoundException;
import com.spring.restapi.module.department.Department;
import com.spring.restapi.module.department.DepartmentRepository;
import com.spring.restapi.module.manager.Manager;
import com.spring.restapi.module.manager.ManagerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
  private EmployeeRepository employeeRepository;
  private DepartmentRepository departmentRepository;
  private ManagerRepository managerRepository;
  private EmployeeUtil employeeUtil;

  /**
   * Constructor.
   * @param employeeRepository for access employee data.
   * @param departmentRepository for access department data.
   * @param managerRepository for access manager data.
   * @param employeeUtil utilities.
   */
  public EmployeeServiceImpl(
      EmployeeRepository employeeRepository,
      DepartmentRepository departmentRepository,
      ManagerRepository managerRepository,
      EmployeeUtil employeeUtil) {
    this.employeeRepository = employeeRepository;
    this.departmentRepository = departmentRepository;
    this.managerRepository = managerRepository;
    this.employeeUtil = employeeUtil;
  }

  @Override
  public EmployeeResponseModel getAllEmployees() {
    List<Employee> employees = employeeRepository.findAll();
    EmployeeResponseModel model = new EmployeeResponseModel();
    model.setCode(RestStatus.SUCCESS.getCode());
    model.setStatus(RestStatus.SUCCESS.getMessage());
    model.setEmployees(employees);
    return model;
  }

  @Override
  public Page<Employee> getPageableEmployee(int page, int size) {
    Pageable showData = PageRequest.of(page, size, Sort.by("employeeName").descending());
    return employeeRepository.findAll(showData);
  }

  @Override
  public List<Employee> getFiveEmployee() {
    return employeeRepository.findTop5By();
  }

  @Override
  public List<Employee> getFiveEmployeeOrderByName() {
    return employeeRepository.findTop5ByOrderByEmployeeName();
  }

  @Override
  public List<Employee> getFiveEmployeeSortByDescending() {
    return employeeRepository.findTop5ByOrderByEmployeeNameDesc();
  }

  @Override
  @Transactional
  public Employee getGeneratedEmployee(Employee newEmployee) {

    Long departmentId = newEmployee.getDepartmentModel().getDepartmentId();
    Optional<Department> foundDepartment = departmentRepository.findByDepartmentId(departmentId);
    if (foundDepartment.isEmpty()) {
      throw new DepartmentNotFoundException("Department not found");
    }

    Long managerId = newEmployee.getManagerModel().getManagerId();
    Optional<Manager> foundManager = managerRepository.findOneByManagerId(managerId);
    if (foundManager.isEmpty()) {
      throw new ManagerNotFoundException("Manager not found");
    }

    boolean resultValidateAttribute = employeeUtil.validateAttribute(newEmployee);
    if (resultValidateAttribute) {
      newEmployee.setDepartment(foundDepartment.get());
      newEmployee.setManager(foundManager.get());
    }

    return employeeRepository.saveAndFlush(newEmployee);
  }


  @Override
  @Transactional
  public Employee getUpdatedEmployee(Long id, Employee employee) {

    Optional<Employee> foundEmployee = employeeRepository.findOneByEmployeeId(id);
    if (foundEmployee.isEmpty()) {
      throw new UsernameNotFoundException("Employee with id " + id + " not found");
    }

    boolean result = employeeUtil.validateAttribute(employee);

    if (result) {
      if (!StringUtils.isEmpty(employee.getEmployeeName())) {
        foundEmployee.get().setEmployeeName((employee.getEmployeeName()));
      }
      if (!StringUtils.isEmpty(employee.getEmployeeEmail())) {
        foundEmployee.get().setEmployeeEmail(employee.getEmployeeEmail());
      }
      if (!StringUtils.isEmpty(employee.getEmployeeAddress())) {
        foundEmployee.get().setEmployeeAddress(employee.getEmployeeAddress());
      }
    }

    return employeeRepository.saveAndFlush(foundEmployee.get());
  }

  @Override
  @Transactional
  public void deleteEmployee(Long id) {
    Optional<Employee> foundEmployee = employeeRepository.findOneByEmployeeId(id);
    if (foundEmployee.isEmpty()) {
      throw  new UsernameNotFoundException("Employee with id " + id + " not found");
    }
    employeeRepository.delete(foundEmployee.get());
  }
}
