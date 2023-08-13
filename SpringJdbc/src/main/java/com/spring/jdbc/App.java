 package com.spring.jdbc;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.spring.jdbc.dao.StudentDao;
import com.spring.jdbc.entities.Student;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "my program Started.........." );
        
        // spring jdbcTemplate
        
        ApplicationContext context=new AnnotationConfigApplicationContext(JdbcConfig.class);
        
        StudentDao studentDao= context.getBean("studentDao",StudentDao.class);
    	
        
        Student student=new Student();
        //insert
//        student.setId(666);
//        student.setName("Jogn Snow");
//        student.setCity("Lucknow");
//        
//        int result = studentDao.insert(student);
//        System.out.println("student added "+ result);
        
        //update
//        student.setId(666);
//        student.setName("John");
//        student.setCity("LA ");
//        
//        int result=studentDao.update(student);
//        System.out.println("data changed "+result);
        
        //delete
//        int result=studentDao.delete(666);
//        System.out.println("deleted "+result);
//        
//    	((AbstractApplicationContext) context).close();
        
//        Student student2 = studentDao.getStudent(222);
//        System.out.println("Single studnet "+student2);
        
       List<Student> student3=studentDao.getAllStudents();
       for (Student student2 : student3) {
		System.out.println(student2);
	}
        
    }
}
