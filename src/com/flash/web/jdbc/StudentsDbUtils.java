package com.flash.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.cj.xdevapi.PreparableStatement;

public class StudentsDbUtils {
	
	private DataSource dataSource;
	public StudentsDbUtils(DataSource theDataSource) {
		dataSource=theDataSource;
	}
	public List<Student> getStudent() throws Exception{
		List<Student> students=new ArrayList<>();
		Connection myConn=null;
		Statement mystmt=null;
		ResultSet myRs=null;
		try
		{
		//get a connection
		myConn=dataSource.getConnection();
		//create a sql statement
		String sql="Select * from student order by last_name";
		mystmt=myConn.createStatement();
		//execute query
		myRs=mystmt.executeQuery(sql);
		//process resultset
		while(myRs.next())
			
		{
			//retrieve data from the result set row
			int id=myRs.getInt("id");
			String firstName=myRs.getString("first_name");
			String lastName=myRs.getString("last_name");
			String email=myRs.getString("email");
			//create new student object
			Student tempStudent=new Student(id,firstName,lastName,email);
			students.add(tempStudent);
			//add it to the list of student
		}
		//close JDBC object
			return students;
		}
		finally
		{
			close(myConn,mystmt,myRs);
		}
		
	}
	private void close(Connection myConn, Statement mystmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs!=null)
				myRs.close();
			if(mystmt!=null)
				mystmt.close();
			if(myConn!=null)
				myConn.close();
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void addStudents(Student theStudent) {
		// TODO Auto-generated method stub
		Connection myConn=null;
		PreparedStatement myStmt=null;
		try
		{
		//get db connection
		myConn=dataSource.getConnection();
		
		// create sql for insert
		String sql="insert into student "+"(first_name, last_name, email) "+"values(?,?,?)";
		myStmt=myConn.prepareStatement(sql);
		
		//set the param values for the student
		myStmt.setString(1, theStudent.getFirstName());
		myStmt.setString(2, theStudent.getLastName());
		myStmt.setString(3, theStudent.getEmail());
		
		//execute sql insert
		myStmt.execute();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(myConn,myStmt,null);
		}
	}
}
