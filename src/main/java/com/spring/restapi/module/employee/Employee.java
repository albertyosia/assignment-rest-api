package com.spring.restapi.module.employee;

import com.spring.restapi.module.department.Department;
import com.spring.restapi.module.department.DepartmentModel;
import com.spring.restapi.module.manager.Manager;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.spring.restapi.module.manager.ManagerModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
  private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long employeeId;
  private @Column(name = "employee_name") String employeeName;
  private @Column(name = "employee_email") String employeeEmail;
  private @Column(name = "employee_address") String employeeAddress;
  private @OneToOne(targetEntity = Department.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY) @JoinColumn(name = "department_id") Department department;
  private @ManyToOne(targetEntity = Manager.class, cascade = CascadeType.ALL) @JoinColumn(name = "manager_id") Manager manager;

  /**
   * This is constructor.
   * @param employeeId This is employee id.
   * @param employeeName This is employee name.
   * @param employeeEmail This is employee email.
   * @param employeeAddress This is employee address.
   */
  public Employee(Long employeeId, String employeeName, String employeeEmail, String employeeAddress) {
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.employeeEmail = employeeEmail;
    this.employeeAddress = employeeAddress;
  }

  public Department getDepartment() {
    if (department != null) {
      DepartmentModel model = new DepartmentModel();
      model.setDepartmentName(department.getDepartmentName());
      model.setDepartmentId(department.getDepartmentId());
      return model;
    }
    return null;
  }

  public Manager getManager() {
    if (manager != null) {
      ManagerModel model = new ManagerModel();
      model.setManagerId(manager.getManagerId());
      model.setManagerFirstName(manager.getManagerFirstName());
      model.setManagerLastName(manager.getManagerLastName());
      model.setBranchName(manager.getBranchName());
      model.setPromotionDate(manager.getPromotionDate());
      return model;
    }
    return null;
  }
}



