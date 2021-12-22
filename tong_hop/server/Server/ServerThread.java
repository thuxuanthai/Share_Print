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

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

import data.Data;

public class ServerThread extends Thread{
		private Socket socket ;
		private JTextArea txt = null;
	    private ObjectOutputStream out;
	    private ObjectInputStream in;
	    private DefaultListModel modFile = new DefaultListModel();
	    private DefaultListModel modClient = new DefaultListModel();
	    private JTextArea LuuTep = new JTextArea();
	    
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
			Data data = (Data) in.readObject();
	         String nameClient = inhoa(data.getNameClient()).trim();
	         modClient.addElement(nameClient);
	         txt.append("New client " + nameClient + " has been connected ...\n");
	         while (true) {
	             data = (Data) in.readObject();
	             
	             File fileReceive = new File("file/"+data.getNameFile());
	             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileReceive));
	                

	                // write file content
	                bos.write(data.getFile());
	                bos.flush();
	             
		             	FileReader fileReader = 
		                        new FileReader(fileReceive);
		            	BufferedReader  bufferedReader = 
		                        new BufferedReader(fileReader);
		            	 LuuTep.read(bufferedReader, null);
		            	 bufferedReader.close();
		            	 LuuTep.requestFocus();
		            	 try {
								LuuTep.print();
							} catch (PrinterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	             modFile.addElement(data);
	             txt.append(nameClient + " get 1 file ... \n");
	         }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String inhoa(String name) {
		String[] tam1 = name.split(" ");
		String tam2 = "";
		for(String i : tam1) {
			i = i.substring(0,1).toUpperCase() + i.substring(1).toLowerCase();
			tam2 = tam2 + i + " ";
		}
		return tam2;
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
