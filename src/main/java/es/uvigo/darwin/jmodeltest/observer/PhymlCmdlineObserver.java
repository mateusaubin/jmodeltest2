package es.uvigo.darwin.jmodeltest.observer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import es.uvigo.darwin.jmodeltest.ModelTestConfiguration;


public class PhymlCmdlineObserver implements Observer {

	//private static PhymlCmdlineObserver instance;

	private FileOutputStream logFile;
	private PrintWriter printout;

	public PhymlCmdlineObserver() {
		try {

			var filename = ModelTestConfiguration.getLogDir() + File.separator + "cmdlines.log";
			logFile = new FileOutputStream(filename, false);
			printout = new PrintWriter(logFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public synchronized void update(Observable o, Object arg) {

		ProgressInfo info = (ProgressInfo) arg;
		int type = info.getType();

		switch (type) {
		case ProgressInfo.PHYML3CMDLINE:
			printout.println(info.getMessage());
			break;
		case ProgressInfo.OPTIMIZATION_STAGE_COMPLETED:
			var strmsg = System.lineSeparator() + System.lineSeparator() + "-- STAGE COMPLETE -- " + System.lineSeparator() + System.lineSeparator();
			printout.println(strmsg);
			break;
		case ProgressInfo.OPTIMIZATION_COMPLETED_INTERRUPTED:
		case ProgressInfo.OPTIMIZATION_COMPLETED_OK:
			try {
				printout.flush();
				printout.close();
				logFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

}
