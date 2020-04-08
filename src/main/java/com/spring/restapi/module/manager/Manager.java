package com.spring.restapi.module.manager;

import com.spring.restapi.module.department.Department;
import com.spring.restapi.module.employee.Employee;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "manager")
public class Manager {
  private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long managerId;
  private @Column(name = "branch_name") String branchName;
  private @Column(name = "first_name") String managerFirstName;
  private @Column(name = "last_name") String managerLastName;
  private @Column(name = "promotion_date") Date promotionDate;
  private @OneToOne(targetEntity = Department.class, cascade = CascadeType.ALL) @JoinColumn(name = "department_id") Department department;
  private @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) List<Employee> employees;
}
