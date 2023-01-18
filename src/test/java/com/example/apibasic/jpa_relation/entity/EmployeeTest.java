package com.example.apibasic.jpa_relation.entity;

import com.example.apibasic.jpa_relation.repository.DepartmentRepository;
import com.example.apibasic.jpa_relation.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EmployeeTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @Test
    void empTest1() {

        Department dept1 = Department.builder()
                .deptName("개발부")
                .build();
        Department dept2 = Department.builder()
                .deptName("영업부")
                .build();

        Employee emp1 = Employee.builder()
                .empName("푸파파파")
                .department(dept1)
                .build();

        Employee emp2 = Employee.builder()
                .empName("헬로키티")
                .department(dept1)
                .build();


        departmentRepository.save(dept1);
        departmentRepository.save(dept2);


        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
    }



    @Test
    @Transactional //spring transaction]
    void empTest2(){
        Employee foundEmp = employeeRepository.findById(2L).orElseThrow();

        //LAZY 전략으로 설정해서 JOIN 안함
        System.out.println("foundEmp = " + foundEmp);


        System.out.println("foundEmp = " + foundEmp.getEmpName());

        System.out.println("foundEmp.getDepartment().getDeptId() = " + foundEmp.getDepartment().getDeptId());

    }

    @Test
    @Transactional
    void empTest3(){

        Department dept = departmentRepository.findById(1L).orElseThrow();

        List<Employee> employees=dept.getEmployees();
        employees.forEach(System.out::println);
    }


}