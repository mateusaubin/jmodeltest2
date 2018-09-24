package es.uvigo.darwin.jmodeltest.observer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;


public class PhymlCmdlineObserver implements Observer {

	//private static PhymlCmdlineObserver instance;

	private FileOutputStream logFile;
	private PrintWriter printout;

	public PhymlCmdlineObserver() {
		try {

			logFile = new FileOutputStream("log/cmdlines.log", false);
			printout = new PrintWriter(logFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public synchronized void update(Observable o, Object arg) {

		ProgressInfo info = (ProgressInfo) arg;
		int type = info.getType();

		if (type == ProgressInfo.PHYML3CMDLINE) {

			printout.println(info.getMessage());

		} else if (type == ProgressInfo.OPTIMIZATION_COMPLETED_OK) {

			try {
				printout.flush();
				printout.close();
				logFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
