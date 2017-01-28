package org.apache.jmeter.threads.dynamic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.threads.ListenerNotifier;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.ListedHashTree;

public class AddThreadsFileWatcherImpl {

	public static void watch(ThreadGroup threadGroup, int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, StandardJMeterEngine engine, File file) {
		// TODO Auto-generated method stub
		// monitor a single file
	    TimerTask task = new FileWatcher( file ) {
	      protected void onChange( File file ) throws NumberFormatException, IOException {
	        // here we code the action on a change
	       
	        int nAddThreads = Integer.parseInt(new String(Files.readAllBytes(Paths.get(file.toURI()))));
	        System.out.println( "File "+ file.getName() +" have changed ! Adding " + nAddThreads + " Threads." );
	        threadGroup.addThreads(groupCount, notifier, threadGroupTree, engine, nAddThreads);
	      }
	    };

	    Timer timer = new Timer();
	    // repeat the check every second
	    timer.schedule( task , new Date(), 1000 );
		
	}

	

}
