package com.app;

import java.sql.SQLException;

import com.backend.WebServer;

public class Application {
	
	public static void main(String[] args) throws SQLException {
		
		new WebServer().startWebServer();
	}
}
