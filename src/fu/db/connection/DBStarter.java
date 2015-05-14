package fu.db.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fu.db.Log;

public class DBStarter {

	private static final String START_COMMAND = "cmd /c " + "\""
			+ System.getenv().get("POSTGRESQL_HOME") + "\"\\bin\\pg_ctl.exe"
			+ " -D db_data start";

	public static void main(String[] args) {
		try {
			start();
		} catch (InterruptedException e) {
			Log.error(e);
		}
	}

	public static void start() throws InterruptedException {

		new CMDThread(START_COMMAND).start();

		waitForUserInput();

		new CMDThread(START_COMMAND.replace("start", "stop")).start();
	}

	private static void waitForUserInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Log.info("--> enter 'close' and press enter for closing PostgreSQL server instance");
		while (true) {
			try {
				String line = br.readLine();
				if (line.equals("close")) {
					Log.info("close entered, server instance is shutting down");
					break;
				} else {
					Log.info("invalid command, try it again! enter 'close' for shutting down server instance");
				}
			} catch (IOException e) {
				Log.error(e);
			}
		}
	}

	public static class CMDThread extends Thread {
		public CMDThread(final String command) {
			super(new Runnable() {

				@Override
				public void run() {
					Log.info("execute: " + command);
					Process exec;
					try {
						exec = Runtime.getRuntime().exec(command);
						InputStream is = exec.getInputStream();
						int i = 0;
						while ((i = is.read()) != -1) {
							System.out.print((char) i);
						}
					} catch (IOException e) {
						Log.error(e);
					}
				}
			});
		}
	}
}
