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
import javax.swing.JTextArea;

import data.Data;

public class ServerFile implements Runnable{
	private Data data ;
	private JTextArea txt = null;
//    private ObjectOutputStream out;
//    private ObjectInputStream in;
    private DefaultListModel modFile = new DefaultListModel();
    String name;
    private JTextArea LuuTep = new JTextArea();
  
    
public ServerFile(Data data, DefaultListModel modF, String name , JTextArea txt) {
	this.data = data;
	this.modFile = modF;
	this.name = name;
	this.txt = txt;

}


  public void run() {
	  
	try {
     
           //  data = (Data) in.readObject();
		 System.out.println(" đang thực thi...");
             
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
	            
					LuuTep.print();
					processCommand();
						
             modFile.addElement(data.getNameFile());
             System.out.println(data.getNameFile() + " xong");
             txt.append(name + " get 1 file ... \n");
      
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	} catch (PrinterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

//public String inhoa(String name) {
//	String[] tam1 = name.split(" ");
//	String tam2 = "";
//	for(String i : tam1) {
//		i = i.substring(0,1).toUpperCase() + i.substring(1).toLowerCase();
//		tam2 = tam2 + i + " ";
//	}
//	return tam2;
//}
  

private void processCommand() {
	try {
		Thread.sleep(2000);
	}catch(InterruptedException e) {
		e.printStackTrace();
	}
}
public DefaultListModel getModFile() {
	return modFile;
}

public void setModFile(DefaultListModel modFile) {
	this.modFile = modFile;
}


public JTextArea getTxt() {
	return txt;
}

public void setTxt(JTextArea txt) {
	this.txt = txt;
}
}

