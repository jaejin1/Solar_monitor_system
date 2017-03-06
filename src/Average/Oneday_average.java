package Average;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Oneday_average {
	private int time = 0;
	private String day = "";
	
	public Oneday_average(int time,String day){
		this.time = time;
		this.day = day;
	}
	
	public void average() throws ClassNotFoundException, SQLException{
		String arr_h_solar_rad[] = new String[120];
	    String arr_s_solar_rad[] = new String[120];
	    String arr_module_temp[] = new String[120];
	    String arr_ambient_temp[] = new String[120];
	    String arr_co2[] = new String[120];
	    String arr_gyro_x[] = new String[120];
	    String arr_gyro_y[] = new String[120];
	    String arr_gyro_z[] = new String[120];
	    String arr_r_phase_current[] = new String[120];
	    String arr_s_phase_current[] = new String[120];
	    String arr_t_phase_current[] = new String[120];
	    
	    int sum_h_solar_rad = 0;
	    int sum_s_solar_rad = 0;
	    double sum_module_temp = 0;
	    double sum_ambient_temp = 0;
	    int sum_co2 = 0;
	    int sum_gyro_x = 0;
	    int sum_gyro_y = 0;
	    int sum_gyro_z = 0;
	    int sum_r_phase_current = 0;
	    int sum_s_phase_current = 0;
	    int sum_t_phase_current = 0;
	    int j = 0;

	    String driverName = "com.mysql.jdbc.Driver";
	    String DBName = "test";
	    String dbURL = "jdbc:mysql://localhost/"+DBName;
	    
	    Class.forName(driverName);
	    Connection con  = DriverManager.getConnection(dbURL,"root","2580");
	    System.out.println("Mysql DB Connection.");
	    Statement stmt = con.createStatement();
	    ResultSet rs = null;
	    
		rs = stmt.executeQuery("select * from test where time > '"+day+" "+time+":00:00' && time < '"+day+" "+(time+1)+":00:00';"); 
		while(rs.next()){
			arr_h_solar_rad[j] = rs.getString("h_solar_rad");
		  	arr_s_solar_rad[j] = rs.getString("s_solar_rad"); 
		  	arr_module_temp[j] = rs.getString("module_temp") ;
		  	arr_ambient_temp[j] = rs.getString("ambient_temp");
		  	arr_co2[j] = rs.getString("co2");
		  	arr_gyro_x[j] = rs.getString("gyro_x");
		  	arr_gyro_y[j] = rs.getString("gyro_y");
		  	arr_gyro_z[j] = rs.getString("gyro_z");
		  	arr_r_phase_current[j] = rs.getString("r_phase_current");
		  	arr_s_phase_current[j] = rs.getString("s_phase_current");
		  	arr_t_phase_current[j] = rs.getString("t_phase_current");
		  	j++;
		}//end while
		
		for(int i=0; i<j; i++){
			if(arr_h_solar_rad[i] != null){
				sum_h_solar_rad += Integer.parseInt(arr_h_solar_rad[i]);
		  	}
		  	if(arr_s_solar_rad[i] != null){
		  		sum_s_solar_rad += Integer.parseInt(arr_s_solar_rad[i]);
		  	}
		  	if(arr_module_temp[i] != null){
		  		sum_module_temp += Double.parseDouble(arr_module_temp[i]);
		  	}
		  	if(arr_ambient_temp[i] != null){
		  		sum_ambient_temp += Double.parseDouble(arr_ambient_temp[i]);
		  	}
		  	if(arr_co2[i] != null){
		  		sum_co2 += Integer.parseInt(arr_co2[i]);
		  	}
		  	if(arr_gyro_x[i] != null){
		  		sum_gyro_x += Integer.parseInt(arr_gyro_x[i]);
		  	}
		  	if(arr_gyro_y[i] != null){
		  		sum_gyro_y += Integer.parseInt(arr_gyro_y[i]);
		  	}
		  	if(arr_gyro_z[i] != null){
		  		sum_gyro_z += Integer.parseInt(arr_gyro_z[i]);
		  	}
		  	if(arr_r_phase_current[i] != null){
		  		sum_r_phase_current += Integer.parseInt(arr_r_phase_current[i]);
		  	}
		  	if(arr_s_phase_current[i] != null){
		  		sum_s_phase_current += Integer.parseInt(arr_s_phase_current[i]);
		  	}
		  	if(arr_t_phase_current[i] != null){
		  		sum_t_phase_current += Integer.parseInt(arr_t_phase_current[i]);
		  	}
		}//end for
		
		int average_h_solar_rad = (sum_h_solar_rad/j);
		int average_s_solar_rad = (sum_s_solar_rad/j);
		double average_module_temp = (((sum_module_temp/j * 10) - (sum_module_temp/j *10)%1)/10);  
		double average_ambient_temp = (((sum_ambient_temp/j * 10) - (sum_ambient_temp/j *10)%1)/10);  
		int average_co2 = (sum_co2/j);
		int average_gyro_x = (sum_gyro_x/j);
		int average_gyro_y = (sum_gyro_y/j);
		int average_gyro_z = (sum_gyro_z/j);
		int average_r_phase_current = (sum_r_phase_current/j);
		int average_s_phase_current = (sum_s_phase_current/j);
		int average_t_phase_current = (sum_t_phase_current/j);
		
		stmt.executeUpdate("insert into test3 "
	      		+ "(h_solar_rad,s_solar_rad,module_temp,ambient_temp,co2,gyro_x,gyro_y,gyro_z,r_phase_current,s_phase_current,t_phase_current,time,send) "
	      		+ " values('"+average_h_solar_rad+"','"+average_s_solar_rad+"','"+average_module_temp+"','"+average_ambient_temp+"','"+average_co2+"','"+average_gyro_x+"','"+average_gyro_y+"','"+average_gyro_z+"','"+average_r_phase_current+"','"+average_s_phase_current+"','"+average_t_phase_current+"',NOW(),'0');");

	}//end everage method    
}
 
     

