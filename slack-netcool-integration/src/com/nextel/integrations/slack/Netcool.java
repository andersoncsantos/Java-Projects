package com.nextel.integrations.slack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Netcool {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@10.1.222.42:1521/pntctl1";
	private static final String DB_USER = "nco";
	private static final String DB_PASSWORD = "r3p0rt3r";

	public static void main(String[] argv) {

		try {

			clearLast5Minutes();
			notClearLast5Minutes();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void clearLast5Minutes() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;
		
		String tipo = "";
		
		String selectTableSQL = "SELECT Node, Summary, LastOccurrence, Deletedat FROM REPORTER_STATUS  "
				+ "WHERE SuppressEscl != 4 "
				+ "AND Summary not like '%Intermittence%' "
				+ "AND Location not like '%Intermittence%' "
				+ "AND NODE IN ('SK552SP','SK671SP','SPCSAM014IU',"
				+ "'SPCSAM008IU','SPCSAM023IU','SPCSAM008IL', 'MSO_BLM','MSO_TLP','MSO_MAD',"
				+ "'SPIGRS022OU','RJCCTR069OU','SPIGRS009IU','RJCIGV001IU','SPIGRS012IU',"
				+ "'SPIGRS033OU','RJCIGV018OU','SPIGRS037OU','SPIGRS027OU','SPIGRS012OU',"
				+ "'RJCIGV002IU','RJCCTR031OU','SPCSAM006IU','RJCCTR017OU','SPIGRS021OU',"
				+ "'RJCIGV004OU','SPIGRS501CU','SPIGRS001IU','SPIGRS500CU','RJCCTR001IU',"
				+ "'SPIGRS025OU','SPCPIN021IU','SPCSAM001IU') "
				+ "AND DELETEDAT BETWEEN SYSDATE - INTERVAL '5' MINUTE AND SYSDATE"; //AND Deletedat IS NULL

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL statement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String node = rs.getString("NODE");
				String summary = rs.getString("SUMMARY");
				String lastoccurrence = rs.getString("LASTOCCURRENCE");
				String deletedat = rs.getString("DELETEDAT");
				
				// extract method sitesCriteria
				tipo = sitesCriteria(node);
				
				System.out.println("SUMMARY : " + summary);
				System.out.println("LASTMODIFIED : " + lastoccurrence);
				System.out.println("DELETEDAT : " + deletedat);
				
				// extract method messageConfig
				messageConfig(tipo, node, summary, lastoccurrence, deletedat);
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	
	private static void notClearLast5Minutes() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;
		
		String tipo = "";
		
		String selectTableSQL = "SELECT Node, Summary, LastOccurrence, Deletedat FROM REPORTER_STATUS  "
				+ "WHERE SuppressEscl != 4 "
				+ "AND Summary not like '%Intermittence%' "
				+ "AND Location not like '%Intermittence%' "
				+ "AND NODE IN ('SK552SP','SK671SP','SPCSAM014IU',"
				+ "'SPCSAM008IU','SPCSAM023IU','SPCSAM008IL', 'MSO_BLM','MSO_TLP','MSO_MAD',"
				+ "'SPIGRS022OU','RJCCTR069OU','SPIGRS009IU','RJCIGV001IU','SPIGRS012IU',"
				+ "'SPIGRS033OU','RJCIGV018OU','SPIGRS037OU','SPIGRS027OU','SPIGRS012OU',"
				+ "'RJCIGV002IU','RJCCTR031OU','SPCSAM006IU','RJCCTR017OU','SPIGRS021OU',"
				+ "'RJCIGV004OU','SPIGRS501CU','SPIGRS001IU','SPIGRS500CU','RJCCTR001IU',"
				+ "'SPIGRS025OU','SPCPIN021IU','SPCSAM001IU') "
				+ "AND LastOccurrence BETWEEN SYSDATE - INTERVAL '5' MINUTE AND SYSDATE AND DELETEDAT IS NULL"; 

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String node = rs.getString("NODE");
				String summary = rs.getString("SUMMARY");
				String lastoccurrence = rs.getString("LASTOCCURRENCE");
				String deletedat = rs.getString("DELETEDAT");
				
				tipo = sitesCriteria(node);
				
				System.out.println("SUMMARY : " + summary);
				System.out.println("LASTMODIFIED : " + lastoccurrence);
				System.out.println("DELETEDAT : " + deletedat);
				
				
				messageConfig(tipo, node, summary, lastoccurrence, deletedat);
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static String sitesCriteria(String node) {
		String tipo;
		if (node.equals("SK552SP")
				|| node.equals("SK671SP")
				|| node.equals("SPCSAM014IU")
				|| node.equals("SPCSAM008IU")
				|| node.equals("SPCSAM023IU")
				|| node.equals("SPCSAM008IL")){
			tipo = "_*(ROCHAVERA)*_";
		}else if (node.equals("SPCSAM006IU")){
			tipo = "_*(CONGONHAS)*_";
		} else if (node.equals("SPIGRS001IU")
				|| node.equals("SPIGRS009IU")
				|| node.equals("SPIGRS012IU")
				|| node.equals("SPIGRS012OU")
				|| node.equals("SPIGRS021OU")
				|| node.equals("SPIGRS022OU")
				|| node.equals("SPIGRS025OU")
				|| node.equals("SPIGRS027OU")
				|| node.equals("SPIGRS033OU")
				|| node.equals("SPIGRS037OU")
				|| node.equals("SPIGRS500CU")
				|| node.equals("SPIGRS501CU")) {
			tipo = "_*(CUMBICA)*_";
		}else if (node.equals("RJCIGV001IU")
				|| node.equals("RJCIGV002IU")
				|| node.equals("RJCIGV004OU")
				|| node.equals("RJCIGV018OU")) {
			tipo = "_*(GALEAO)*_";
		}else if (node.equals("SPCSAM001IU")) {
			tipo = "_*(MARKET PLACE)*_";
		} else if (node.equals("SPCPIN021IU")) {
			tipo = "_*(MORUMBI)*_";
		} else if (node.equals("RJCCTR001IU")
				|| node.equals("RJCCTR017OU")
				|| node.equals("RJCCTR031OU")
				|| node.equals("RJCCTR069OU")) {
			tipo = "_*(SANTOS DUMONT)*_";
		}else{
			tipo = "_*(ALERTA MSO)*_";
		}
		return tipo;
	}

	private static void messageConfig(String tipo, String node, String summary, String lastoccurrence,
			String deletedat) {
		if (deletedat != null) {
			deletedat = deletedat + " :heavy_check_mark:";

			SlackApi api = new SlackApi(
					"https://hooks.slack.com/services/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");
			api.call(new SlackMessage(">_*node*_ : " + "_" + node + "_" + " " + tipo + "\n" + ">_*summary*_ : "
					+ "_" + summary + "_" + "\n" + ">_*lastoccurrence*_ : " + "_" + lastoccurrence + "_" + "\n"
					+ ">_*clear*_ : " + "_" + deletedat + "_"));
		} else {
			SlackApi api = new SlackApi(
					"https://hooks.slack.com/services/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");
			api.call(new SlackMessage(">_*node*_ : " + "_" + node + "_" + " " + tipo + "\n" + ">_*summary*_ : "
					+ "_" + summary + "_" + "\n" + ">_*lastoccurrence*_ : " + "_" + lastoccurrence + "_" + " :red_circle:"));

		}
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	public void Execute() throws SQLException{
		clearLast5Minutes();
		notClearLast5Minutes();
	}
	
}
