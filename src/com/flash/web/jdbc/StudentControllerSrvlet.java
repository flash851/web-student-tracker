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
		listStudents(request,response);
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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