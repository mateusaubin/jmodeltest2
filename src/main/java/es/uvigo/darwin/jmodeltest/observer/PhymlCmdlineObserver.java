package es.uvigo.darwin.jmodeltest.observer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;


public class PhymlCmdlineObserver implements Observer {

	private static PhymlCmdlineObserver instance;
	
	private FileOutputStream logFile;
	private PrintWriter printout;

	private PhymlCmdlineObserver() {
		try {
			
			logFile = new FileOutputStream("log/cmdlines.log", false);
			printout = new PrintWriter(logFile);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public synchronized void update(Observable o, Object arg) {
		
		ProgressInfo info = (ProgressInfo) arg;
		if (info.getType() == ProgressInfo.PHYML3CMDLINE) {

			printout.println(info.getMessage());

			printout.flush();
		}
	}


	public synchronized static PhymlCmdlineObserver getInstance() {
		if (instance == null) {
			instance = new PhymlCmdlineObserver();
		}
		return instance;
	}

}
