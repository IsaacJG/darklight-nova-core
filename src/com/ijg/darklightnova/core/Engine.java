package com.ijg.darklightnova.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.ijg.darklightnova.gui.GUI;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public String progressFile = "C:\\Users\\Lucas\\AppData\\Roaming\\darklight-progress.dat"; //Should vary with OS installation.
	
	public AssessmentModule assessModule;
	
	GUI gui;
	
	public static void main(String[] args) {
		new Engine();
	}

	public Engine() {
		bNotFinished = true;
		assessModule = new AssessmentModule(this);
		start();
	}
	
	public void start() {
		/*
		 * Init the gui and the thread, start
		 * the gears turning, do initial 
		 * scoring and display
		 */
		running = true;
		gui = new GUI(this);
		Thread engine = new Thread(this, "engine");
		engine.start();
		assessModule.assess();
		gui.update();
	}

	public void run() {
		while (running) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	public void finishSession() {
		running = false;
	}
	
	public void writeFoundList() {
		/*
		 * Write all found vulnerabilities
		 * to the progress file in the format
		 * of "name: description"
		*/
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(progressFile)));
			for (Issue issue : assessModule.issues) {
				out.write(issue.name + ": " + issue.description + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean finished() {
		return !bNotFinished;
	}
}
