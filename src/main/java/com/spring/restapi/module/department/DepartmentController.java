package com.spring.restapi.module.department;

import com.spring.restapi.models.GetResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {
  @Autowired DepartmentService departmentService;

  @GetMapping("/")
  public GetResponseModel getAllDepartment() {
    return departmentService.getAllDepartment();
  }

  @PostMapping("/")
  public Department getGeneratedDepartment(@RequestBody Department department) {
    return departmentService.getGeneratedDepartment(department);
  }

  @PutMapping("/{id}")
  public Department getUpdatedDepartment(
      @PathVariable("id") Long id,
      @RequestBody Department department) {
    return departmentService.getUpdatedDepartment(id, department);
  }

  @DeleteMapping("/{id}")
  public void deleteDepartment(@PathVariable("id") Long id) {
    departmentService.deleteDepartment(id);
  }
}
