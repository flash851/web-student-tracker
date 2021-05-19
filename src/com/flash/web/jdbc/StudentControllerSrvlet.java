package com.flash.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.org.apache.xpath.internal.axes.RTFIterator;

/**
 * Servlet implementation class StudentControllerSrvlet
 */
@WebServlet("/StudentControllerSrvlet")
public class StudentControllerSrvlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private StudentsDbUtils studentsDbUtils;
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			studentsDbUtils=new StudentsDbUtils(dataSource);
		}
		catch(Exception e){
			throw new ServletException(e);
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//list the students .....in MVC fashion
		try
		{	
			// read the command parameter
			String theCommand=request.getParameter("command");
			
			//if the command is missing then default to listing students
			if(theCommand==null)
			{
				theCommand="LIST";
			}
			
			//route to appropriate method
			switch(theCommand)
			{
			case "LIST":
				listStudents(request,response);
				break;
			case "ADD":
				addStudent(request,response);
				break;
			case "LOAD":
				loadStudent(request,response);
			case "UPDATE":
				updateStudent(request,response);
			default:
				listStudents(request,response);
			}
		
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	private void updateStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		
		//read student info from form data
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		System.out.println(id+" "+firstName+" "+lastName+" "+email);
		// create a new student object
		Student theStudent=new Student(id,firstName,lastName,email);
		
		//perform update on database
		studentsDbUtils.updateStudent(theStudent);
		
		//send them back to the "list student page"
		
		listStudents(request, response);
		
		//
		
	}
	private void loadStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		
		//read student id from form data
		String theStudentId=request.getParameter("studentId");
		// getting student from database
		Student theStudent=studentsDbUtils.getStudent(theStudentId);
		//place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		//send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher=request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		// read student info from form data
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		//create a new student object
		Student theStudent=new Student(firstName,lastName,email);
		//add the student to database
		studentsDbUtils.addStudents(theStudent);
		//send back to main page
		listStudents(request, response);
	}
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		
		//get students from db util
		List<Student> students=studentsDbUtils.getStudent();
		//add students to the request
		request.setAttribute("STUDENTS_LIST", students);
		//send to JSP page(view)
		RequestDispatcher dispatcher=request.getRequestDispatcher("/list-student.jsp");
		dispatcher.forward(request, response);
	}

}
