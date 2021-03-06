package es.uvigo.darwin.jmodeltest.observer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import es.uvigo.darwin.jmodeltest.ApplicationOptions;
import es.uvigo.darwin.jmodeltest.ModelTestConfiguration;


public class PhymlCmdlineObserver implements Observer {

	//private static PhymlCmdlineObserver instance;

	private FileOutputStream logFile;
	private PrintWriter printout;

	public PhymlCmdlineObserver(ApplicationOptions options) {
		try {

			String srcfilename = options.getInputFile().getName().split("\\.(?=[^\\.]+$)")[0];
			String filename = ModelTestConfiguration.getLogDir() + File.separator + srcfilename + "_cmds.log";
			logFile = new FileOutputStream(filename, false);
			printout = new PrintWriter(logFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void update(Observable o, Object arg) {

		ProgressInfo info = (ProgressInfo) arg;
		int type = info.getType();

		synchronized (PhymlCmdlineObserver.class) {
			switch (type) {
			case ProgressInfo.PHYML3CMDLINE:
				printout.println(info.getMessage());
				break;
			case ProgressInfo.OPTIMIZATION_STAGE_COMPLETED:
				printout.flush();
				String strmsg = System.lineSeparator() + System.lineSeparator() + "$$ STAGE COMPLETE $$"
						+ System.lineSeparator() + System.lineSeparator();
				printout.println(strmsg);
				break;
			case ProgressInfo.OPTIMIZATION_COMPLETED_INTERRUPTED:
			case ProgressInfo.OPTIMIZATION_COMPLETED_OK:
			case ProgressInfo.INTERRUPTED:
			case ProgressInfo.ERROR:
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

}
