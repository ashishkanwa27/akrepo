package com.spring.orm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.orm.dao.StudentDao;
import com.spring.orm.entities.Student;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
       
       StudentDao studentDao=context.getBean("studentDao",StudentDao.class);
       
//       Student student=new Student(2,"Rahul","Theog");
//       int result = studentDao.insert(student);
//       System.out.println("done "+ result);
    
       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       boolean go=true;
   while(go) {
       System.out.println("Press 1 for adding new student");
       System.out.println("Press 2 for display all student");
       System.out.println("Press 3 for seeing single student destails");
       System.out.println("Press 4 for deleting student");
       System.out.println("Press 5 for for updating");
       System.out.println("Press 6 exit");
       try {
		int input=Integer.parseInt(br.readLine());
		
		switch (input) {
		case 1:
			System.out.println("ENter id : ");
			int uId=Integer.parseInt(br.readLine());
			
			System.out.println("Enter user Name: ");
			String uName=br.readLine();
			
			System.out.println("Enter user city: ");
			String uCity=br.readLine();
			
			Student s=new Student();
			s.setStudentId(uId);
			s.setStudentName(uName);
			s.setStudentCity(uCity);
			
			int insert = studentDao.insert(s);
			System.out.println(insert+" user added");
			System.out.println();
			
			break;
		case 2:
			//all
			
			List<Student> allStudents = studentDao.getAllStudents();
			for (Student student : allStudents) {
				System.out.println(student);
				
			}
			System.out.println("************************");
			System.out.println();
			break;
			
		case 3:
			System.out.println("Enter user id : ");
			int userId=Integer.parseInt(br.readLine());
			Student student = studentDao.getStudent(userId);
			System.out.println(student);
			System.out.println();
	
			break;
	
		case 4:
		//delete
			System.out.println("Enter user id : ");
			int id=Integer.parseInt(br.readLine());
			studentDao.delete(id);
			System.out.println("user deleted");
			System.out.println();
			break;
		
		case 5:
			//update
			System.out.print("type in the id of student you want to update:");
			int oldId = Integer.parseInt(br.readLine());
			System.out.print("Enter new user name:");
			String namee= br.readLine();
			System.out.print("Enter new user city:");
			String cityy= br.readLine();
			studentDao.delete(oldId);
			Student student3 = new Student(oldId, namee, cityy);
			studentDao.insert(student3);
			System.out.println("one row updated");
			break;
			
			
			
			case 6:
			go= false;
				break;

		
		}
		
	} catch (Exception e) {
		System.out.println("invalid input Try with another one");
		System.out.println(e.getMessage());
	}
   }
       System.out.println("Thank you ");
    }
}
