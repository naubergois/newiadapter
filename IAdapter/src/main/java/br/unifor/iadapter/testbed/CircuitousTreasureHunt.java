package br.unifor.iadapter.testbed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

public class CircuitousTreasureHunt {

	public static Answer<Boolean> timer() {
		Answer<Boolean> answer = new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				Thread.sleep(200);
				return true;
			}
		};
		return answer;

	}

	public static Answer<PreparedStatement> timer1() {
		Answer<PreparedStatement> answer = new Answer<PreparedStatement>() {
			public PreparedStatement answer(InvocationOnMock invocation) throws Throwable {
				Thread.sleep(200);
				PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
				Mockito.when(stmt.execute("100")).thenAnswer(timer()).thenReturn(true);

				return stmt;
			}
		};
		return answer;

	}

	static int number;

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("increment", "1");
		return params;
	}

	@Test
	public void runTest() throws SQLException {

		SampleResult sampleResult = new SampleResult();
		sampleResult.sampleStart();
		try {

			Connection con1 = Mockito.mock(Connection.class);
			
			Mockito.when(con1.prepareStatement(Mockito.anyString())).thenAnswer(timer1());

			for (int i = 0; i <= 50; i++) {

				PreparedStatement stms = con1.prepareStatement("select * from table ");

				stms.execute();
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		sampleResult.sampleEnd();

	}

}
