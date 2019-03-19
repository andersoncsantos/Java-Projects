package com.nextel.integrations.slack;

import java.sql.SQLException;
import java.util.Date;

public class SlackTimeTask {

	public static void main(String args[]) throws SQLException {
		
		Netcool netcool = new Netcool();

		try {
			while (true) {
				System.out.println(new Date());
				netcool.Execute();
				Thread.sleep(300 * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
