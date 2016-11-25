package br.unifor.iadapter.testbed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

class DriverManagerMock {

	public static void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection(String url) {

		Connection con = Mockito.mock(Connection.class);

		try {
			Mockito.doAnswer(new Answer<Void>() {
				public Void answer(InvocationOnMock invocation) {
					Object[] args = invocation.getArguments();
					System.out.println("called with arguments: " + Arrays.toString(args));
					DriverManagerMock.sleep(1000);
					return null;
				}
			}).when(con).close();
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

@RunWith(PowerMockRunner.class)
public class DatabaseEmulator {

	public void connection() {
		PowerMockito.mockStatic(DriverManager.class, DriverManagerMock.class);

	}

}
