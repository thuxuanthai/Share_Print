package Server;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.Sides;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import File.FileInfo;

public class OIThread extends Thread{
	Socket socket = null;
	ObjectInputStream sInput;
	ObjectOutputStream sOutput;
	int id;
	String username;
	JTextArea LuuTep = new JTextArea();
	

	
	public OIThread(Socket socket) {
		this.socket = socket;
	
		
//		model = new DefaultListModel();
//		list_TTS = new JList();
	}
	
	@Override
	  public void run() {
//	            Socket server = null;
	            DataInputStream inFromClient = null;
	            ObjectInputStream ois = null;
	            ObjectOutputStream oos = null;

	            try {
//	                // accept connect from client and create Socket object
//	                server = serverSocket.accept();
//	                System.out.println("\nConnected to " + server.getRemoteSocketAddress());

	                // get greeting from client
	                inFromClient = new DataInputStream(socket.getInputStream());
	                System.out.println(inFromClient.readUTF());

	                // receive file info
	                ois = new ObjectInputStream(socket.getInputStream());
	                FileInfo fileInfo = (FileInfo) ois.readObject();
	                fileInfo.getFilename();
	                System.out.println(fileInfo.getFilename());
	                
	                if (fileInfo != null) {
	                    createFile(fileInfo);
	                }
	                
	                // confirm that file is received
	                oos = new ObjectOutputStream(socket.getOutputStream());
	                fileInfo.setStatus("success");
	                fileInfo.setDataBytes(null);
	                oos.writeObject(fileInfo);
//	                new btm5_trang184("E:\\"+fileInfo.getFilename());
	                
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            } finally {
	                // close all stream
	                closeStream(ois);
	                closeStream(oos);
	                closeStream(inFromClient);
	           //      close session
	                closeSocket(socket);
	            }
	        }

	    /**
	     * create file with fileInfo
	     * 
	     * Quynh - Xuan - Vu
	     * @param fileInfo
	     * @return file is created or not
	     */
	    
	    private boolean createFile(FileInfo fileInfo) {
	        BufferedOutputStream bos = null;

	        try {
	            if (fileInfo != null) {
	            	File fileReceive = new File(fileInfo.getDestinationDirectory() + fileInfo.getFilename());
	                bos = new BufferedOutputStream(new FileOutputStream(fileReceive));
	                

	                // write file content
	                bos.write(fileInfo.getDataBytes());
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
					
	                
	               // Desktop.getDesktop().open(fileReceive);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }finally {
	            closeStream(bos);
	        }
	        return true;
	    }

	    /**
	     * close socket
	     * 
	     * Quynh - Xuan - Vu
	     */
	    public void closeSocket(Socket socket) {
	        try {
	            if (socket != null) {
	                socket.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * close input stream
	     * 
	     * Quynh - Xuan - Vu
	     */
	    public void closeStream(InputStream inputStream) {
	        try {
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }

	    /**
	     * close output stream
	     * 
	     * Quynh - Xuan - Vu
	     */
	    public void closeStream(OutputStream outputStream) {
	        try {
	            if (outputStream != null) {
	                outputStream.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	
}