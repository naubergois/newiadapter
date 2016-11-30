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

import com.mysql.jdbc.PreparedStatement;


@RunWith(PowerMockRunner.class)
public class DatabaseEmulator {

	public void connection() {
		PowerMockito.mockStatic(DriverManager.class, DriverManagerMock.class);

	}

}
