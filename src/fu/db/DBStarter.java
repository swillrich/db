package fu.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBStarter {

	private static final String START_COMMAND = "cmd /c "
			+ DBProperties.INSTANCE.getProperty("postgresql")
			+ "\\bin\\pg_ctl.exe" + " -D db_data start";

	public static void main(String[] args) {
		try {
			start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void start() throws InterruptedException {

		new CMDThread(START_COMMAND).start();

		waitForUserInput();

		new CMDThread(START_COMMAND.replace("start", "stop")).start();
	}

	private static void waitForUserInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out
				.println("--> enter 'close' and press enter for closing PostgreSQL server instance");
		while (true) {
			try {
				String line = br.readLine();
				if (line.equals("close")) {
					System.out
							.println("close enteres, server instance is shutting down");
					break;
				} else {
					System.out
							.println("invalid command, try it again! enter 'close' for shutting down server instance");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class CMDThread extends Thread {
		public CMDThread(final String command) {
			super(new Runnable() {

				@Override
				public void run() {
					Process exec;
					try {
						exec = Runtime.getRuntime().exec(command);
						InputStream is = exec.getInputStream();
						int i = 0;
						while ((i = is.read()) != -1) {
							System.out.print((char) i);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
