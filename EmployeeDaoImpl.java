package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojos.Employee;
import static utils.DBUtils.openConnection;

public class EmployeeDaoImpl implements IEmployeeDao {
	//state
	private Connection connection;
	private PreparedStatement pst1;
	
	

	public EmployeeDaoImpl() throws SQLException{
		
		connection=openConnection();
		String sql="select empid,name,salary,join_date from my_emp where deptid=? and join_date>? order by salary";
		pst1=connection.prepareStatement(sql);
		System.out.println("emp dao created....");
	}



	@Override
	public List<Employee> getEmpDetailsByDeptAndDate(String dept, Date joinDate) throws SQLException {
		List<Employee> emps=new ArrayList<>();
		//set IN params
		pst1.setString(1, dept);
		pst1.setDate(2, joinDate);
		try(ResultSet rst=pst1.executeQuery())
		{
			//rst cursor --before 1st row
			while(rst.next())
				emps.add(new Employee(rst.getInt(1), rst.getString(2),rst.getDouble(3), rst.getDate(4)));
			
		}
		return emps;
	}
	//add clean up method for closing DB related resources
	public void cleanUp() throws SQLException	
	{
		if(pst1 != null)
			pst1.close();
		if(connection != null)
			connection.close();
		System.out.println("emp dao cleaned up.....");
		
	}
	

}
