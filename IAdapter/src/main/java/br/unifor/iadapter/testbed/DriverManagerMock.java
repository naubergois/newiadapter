package br.unifor.iadapter.testbed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.mysql.jdbc.PreparedStatement;

@PrepareForTest({DriverManager.class})
public class DriverManagerMock {
	
	public DriverManagerMock(String a){
		
	}

	public static void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static Connection getConnection(String url) {

		Connection con = Mockito.mock(Connection.class);
		PreparedStatement stmt=Mockito.mock(PreparedStatement.class);

		try {
			Mockito.doAnswer(new Answer<Void>() {
				public Void answer(InvocationOnMock invocation) {
					Object[] args = invocation.getArguments();
					System.out.println("called with arguments: " + Arrays.toString(args));
					DriverManagerMock.sleep(1000);
					return null;
				}
			}).when(con).close();
			
			 Answer<Boolean> answer = new Answer<Boolean>() {
			        public Boolean answer(InvocationOnMock invocation) throws Throwable {
			            Thread.sleep(2000);
			            return true;
			        }
			    };
			    
			    
			    Answer<Boolean> answer1 = new Answer<Boolean>() {
			        public Boolean answer(InvocationOnMock invocation) throws Throwable {
			        	System.out.println("Answer 1");
			            Thread.sleep(2000);
			            return true;
			        }
			    };

			    
			    
			
			
			Mockito.when(stmt.execute(Mockito.anyString())).thenAnswer(answer1).thenReturn(true);
			
			Mockito.when(con.prepareStatement(Mockito.anyString())).thenAnswer(answer).thenReturn(stmt);
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;

	}

}
