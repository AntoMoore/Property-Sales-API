package com.app;

import java.sql.SQLException;

import com.backend.WebServer;

public class Application {
	
	private static WebServer server = new WebServer();

	public static void main(String[] args) throws SQLException {
		
		server.startWebServer();
	}
}
