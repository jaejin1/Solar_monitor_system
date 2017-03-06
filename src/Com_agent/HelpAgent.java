package Com_agent;

import java.sql.*;

import Average.Oneday_average;

public class HelpAgent{
	  private String v = "";
	  private String arr[] = new String[11];
	  private double arr2[] = new double[11];
	  private int num2 = 0;
	  private int a = 1;
	  
	  public HelpAgent(String v,int a) {
	    this.v = v;
	    this.a = a;
	  }
	
	  public void division(){
		  for(int i=0; i<5; ){
			  if(v.substring(i,i+1).equals("N")){
				  if(v.substring(i+1,i+2).equals("R")){
					  while(i+8 < 52){
						  arr[num2] = v.substring(i+4,i+8);
						  arr2[num2] = Integer.parseInt(arr[num2],16);
						  num2++;
						  i += 4;
					  }
				  }else{
					  System.out.println("error!!");   //N이 있지만 바로뒤에 R이 없을때 error
				  }	
			  }else{
				  i++;
				 
			  }
		  }
	  }

	    public void dbconnection(){
	      try
	      {
	    	
	      
	      
	      if(a % 2 == 0){//if a 2나머지 0
	    	  String driverName = "com.mysql.jdbc.Driver";
		      String DBName = "test";
		     
		      String dbURL = "jdbc:mysql://localhost/"+DBName;
		      String time ="";
		      String hour = "";
		      String hour2 = "";
		      String day = "";
		      String day2 = "";
		      int noerror = 0;
		      String average_day = "";
		     
		      
		      Class.forName(driverName);
		      Connection con  = DriverManager.getConnection(dbURL,"root","2580");
		      System.out.println("Mysql DB Connection.");


		      Statement stmt = con.createStatement();
		      ResultSet rs = null;
		      
	      division();
	      
	      for(int i=0; i<11; i++){
	    	  if(arr2[i] != 0){
	    		  noerror++;
	    	  }
	      }
	      if( noerror > 5 ){
	    	  
	      
	      arr2[2] = arr2[2]/10;
	      arr2[3] = arr2[3]/10;
	      
	      for(int i=0; i<11; i++){
	    	  System.out.println(arr2[i]);
	    	 
	    	  
	      }

	      // 전 시간 불러오기 
	      rs = stmt.executeQuery("select time from test order by num desc limit 1;"); 
	      while(rs.next()){
	    	  time = rs.getString("time");
	      }
	      //System.out.println(time);
	      average_day = time.substring(0,10);
	      //System.out.println(average_day);
	      hour = time.substring(11,13);

	      //System.out.println("전시간 = " + hour);
	      day = time.substring(8,10);
	      //System.out.println("전날짜 = " + day);
	      stmt.executeUpdate("insert into test "
	      		+ "(h_solar_rad,s_solar_rad,module_temp,ambient_temp,co2,gyro_x,gyro_y,gyro_z,r_phase_current,s_phase_current,t_phase_current,time,send) "
	      		+ " values('"+arr2[0]+"','"+arr2[1]+"','"+arr2[2]+"','"+arr2[3]+"','"+arr2[7]+"','"+arr2[4]+"','"+arr2[5]+"','"+arr2[6]+"','"+arr2[8]+"','"+arr2[9]+"','"+arr2[10]+"',NOW(),'0');");
	      
	      // 현재 시간 불러오기 
	      rs = stmt.executeQuery("select time from test order by num desc limit 1;"); 
	      while(rs.next()){
	    	  time = rs.getString("time");
	      }
	      hour2 = time.substring(11,13);
	      //System.out.println("후시간 = " + hour2);
	      day2 = time.substring(8,10);
	      //System.out.println("후날짜 = " + day2);
	      
	      if(!hour.equals(hour2)){
	    	 System.out.println("시간이 바꼇당 평균 내라 ㄹ아아ㅏ  ");   
	    	  
	    	 Oneday_average average = new Oneday_average(Integer.parseInt(hour),average_day);
	    	 average.average();
	      }
	      if(!day.equals(day2)){
	    	  System.out.println("날이 바꼇당 초기화 해라 ");
	    	  stmt.executeUpdate("truncate test;");
	    	  stmt.executeUpdate("insert into test "   //다 없애버리면 전꺼랑 비교를 못해서 한번더 입력함 
	  	      		+ "(h_solar_rad,s_solar_rad,module_temp,ambient_temp,co2,gyro_x,gyro_y,gyro_z,r_phase_current,s_phase_current,t_phase_current,time,send) "
	  	      		+ " values('"+arr2[0]+"','"+arr2[1]+"','"+arr2[2]+"','"+arr2[3]+"','"+arr2[7]+"','"+arr2[4]+"','"+arr2[5]+"','"+arr2[6]+"','"+arr2[8]+"','"+arr2[9]+"','"+arr2[10]+"',NOW(),'0');");
	    	  
	      }
	      rs.close();
	      con.close();
	      	}else{
	      		System.out.println("에러값들어갔음" );//에러값 들어가면 안돌아가게 
	      	}
	      }// 2개중에 하나 
	    
	      
	    }

	    catch(Exception e)
	    {
	      System.out.println("Mysql Server Not Connection.");
	      e.printStackTrace();
	    }
	  }

	}