package Server;

import java.awt.print.PrinterException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

import data.Data;

public class ServerThread extends Thread {
	private Socket socket;
	private JTextArea txt = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private DefaultListModel modFile = new DefaultListModel();
	private DefaultListModel modClient = new DefaultListModel();
	private JTextArea LuuTep = new JTextArea();
	Data data;
	Runnable r = null;
//	   BlockingQueue<Runnable> blockingQueue;
	  
	   


	public ServerThread(Socket socket, DefaultListModel modF, DefaultListModel modClient, JTextArea txt) {
		this.socket = socket;
		this.modFile = modF;
		this.modClient = modClient;
		this.txt = txt;
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			data = (Data) in.readObject();
			String nameClient = data.getNameClient();
			modClient.addElement(nameClient);
			txt.append("New client " + nameClient + " has been connected ...\n");
			
			while (true) {
					data = (Data) in.readObject();
					r = new ServerFile(data, modFile,nameClient, txt);
					Server ser = new Server();
					ser.exService.submit(r);
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DefaultListModel getModFile() {
		return modFile;
	}

	public void setModFile(DefaultListModel modFile) {
		this.modFile = modFile;
	}

	public DefaultListModel getModClient() {
		return modClient;
	}

	public void setModClient(DefaultListModel modClient) {
		this.modClient = modClient;
	}

	public JTextArea getTxt() {
		return txt;
	}

	public void setTxt(JTextArea txt) {
		this.txt = txt;
	}





	
}
