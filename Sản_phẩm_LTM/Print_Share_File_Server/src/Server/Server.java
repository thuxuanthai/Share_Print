package Server;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.print.*;
//import javafx.print.Printer;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class Server extends JFrame{
    // create serverSocket object
    private static final long serialVersionUID = 1L;
    
	private JPanel contentPane;
	private JTextField txtPortServer;
	private JTable table_tepCanIn;
	private JLabel lblNewLabel;
	private JButton btnKhoiDongServer;
	private JButton btnDungServer;
	private Thread run;
	private JList list_TrangthaiS; 
	private int fileId = 0;
	   
	private DefaultTableModel tableModel;
	
    private ArrayList<OIThread> list;
    private static int uniqueId;
    
    JTextArea txtA = new JTextArea();
  


	
	ServerSocket svSocket;
//	Socket socket;
//	DataOutputStream output;
//	DataInputStream input;
	DefaultListModel model;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Server() {
		initComponents();
		model = new DefaultListModel();
		list = new ArrayList<OIThread>();
		
	}
	 public void initComponents() {
		 setTitle("Server-Chia sẻ máy in trong mạng LAN");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(245, 245, 245));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			
			ImageIcon originalIcon = new ImageIcon(Server.class.getResource("/Image/maychu.png"));
	        JLabel originalLabel = new JLabel(originalIcon);
	        int width = originalIcon.getIconWidth() /6;
	        int height = originalIcon.getIconHeight() / 6;
	        Image scaled = scaleImage(originalIcon.getImage(), width, height);
	        ImageIcon scaledIcon = new ImageIcon(scaled);
	        contentPane.setLayout(null);
	        JLabel newLabel = new JLabel(scaledIcon);
	        newLabel.setBounds(10, 144, 143, 138);
	        newLabel.setVerticalAlignment(SwingConstants.BOTTOM);
	        contentPane.add(newLabel);
	        
	        JLabel lblNameClient = new JLabel("Máy Chủ");
	        lblNameClient.setFont(new Font("Tahoma", Font.BOLD, 22));
	        lblNameClient.setForeground(new Color(0, 0, 128));
	        lblNameClient.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNameClient.setBounds(327, 0, 295, 46);
	        contentPane.add(lblNameClient);
	        
	        lblNewLabel = new JLabel("Cổng");
	        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel.setBounds(169, 50, 66, 18);
	        contentPane.add(lblNewLabel);
	        
	        txtPortServer = new JTextField();
	        txtPortServer.setBounds(245, 50, 90, 20);
	        contentPane.add(txtPortServer);
	        txtPortServer.setColumns(10);
	        
	         btnKhoiDongServer = new JButton("Khởi động máy chủ");
	       
	        
	        btnKhoiDongServer.setBackground(new Color(0, 0, 128));
	        btnKhoiDongServer.setForeground(new Color(255, 255, 255));
	        btnKhoiDongServer.setBounds(355, 49, 152, 23);
	        contentPane.add(btnKhoiDongServer);
	        
	         btnDungServer = new JButton("Dừng máy chủ");
	        btnDungServer.setForeground(new Color(255, 255, 255));
	        btnDungServer.setBackground(new Color(0, 0, 128));
	        btnDungServer.setBounds(517, 49, 143, 23);
	        contentPane.add(btnDungServer);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(169, 222, 527, 168);
	        contentPane.add(scrollPane);
	        
	        table_tepCanIn = new JTable();
	        table_tepCanIn.setModel(new DefaultTableModel(
	        	new Object[][] {
	        		{null, null, null, null, null},
	        	},
	        	new String[] {
	        		"Stt", "\u0110\u1ECBa ch\u1EC9 IP", "T\u00EAn Client", "T\u00EAn file", "S\u1ED1 l\u01B0\u1EE3ng"
	        	}
	        ) {
	        	Class[] columnTypes = new Class[] {
	        		Integer.class, Object.class, Object.class, String.class, Object.class
	        	};
	        	public Class getColumnClass(int columnIndex) {
	        		return columnTypes[columnIndex];
	        	}
	        });
	        scrollPane.setViewportView(table_tepCanIn);
	        
	        JScrollPane scrollPane_1 = new JScrollPane();
	        scrollPane_1.setBounds(169, 103, 527, 92);
	        contentPane.add(scrollPane_1);
	        
	         list_TrangthaiS = new JList();
	        scrollPane_1.setViewportView(list_TrangthaiS);
	       
	        
	        JLabel lblNewLabel_1 = new JLabel("Trạng thái kết nối");
	        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel_1.setBounds(169, 79, 125, 23);
	        contentPane.add(lblNewLabel_1);
	        
	        JLabel lblNewLabel_2 = new JLabel("Các tập tin nhận được");
	        lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel_2.setBounds(170, 203, 165, 19);
	        contentPane.add(lblNewLabel_2);
	        
	        JButton btnMoTep = new JButton("Mở tệp");
	        btnMoTep.setForeground(new Color(255, 255, 255));
	        btnMoTep.setBackground(new Color(0, 0, 128));
	        btnMoTep.setBounds(511, 400, 90, 23);
	        contentPane.add(btnMoTep);
	        
	        JButton btnLuuTep = new JButton("Lưu tệp");
	        btnLuuTep.setForeground(new Color(255, 255, 255));
	        btnLuuTep.setBackground(new Color(0, 0, 128));
	        btnLuuTep.setBounds(606, 400, 90, 23);
	        contentPane.add(btnLuuTep);
	        
	        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane.setBounds(706, 48, 174, 340);
	        contentPane.add(tabbedPane);
	        
	        JScrollPane scrollPane_2 = new JScrollPane();
	        tabbedPane.addTab("DS Online", null, scrollPane_2, null);        
	        
	        JTextArea txtArea_dS_cLient = new JTextArea();
	        scrollPane_2.setViewportView(txtArea_dS_cLient);
	        
	        tableModel = (DefaultTableModel) table_tepCanIn.getModel();
	        
	        
	        btnKhoiDongServer.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent e) {
	         	        run = new Thread(new Runnable() {
	         	            @Override
	         	            public void run() {
	         	                try {
	         	                	model.addElement("Server connecting ...");
	    	                    	list_TrangthaiS.setModel(model);
	         	                	int cong = Integer.parseInt(txtPortServer.getText());
	         	                    svSocket = new ServerSocket(cong);
	         	                  
	         	                    while (true) {
	         	                    	Socket socket = null;
	         	                    	socket = svSocket.accept();
	         	                    	model.addElement("Server is connected");
		    	                    	list_TrangthaiS.setModel(model);
	         	                    	OIThread thr = new OIThread(socket);
//	         	                    	list.add(thr);
	         	                    	thr.start();
		    	                    }
	         	                } catch (Exception e) {
	         	                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
	         	                    //e.printStackTrace();
	         	                }
	         	            }
	         	        });
	         	        run.start();
	         	}
	         });
	        
	      
	        
	 
	        setLocationRelativeTo(null);
//			setVisible(true);
			setBounds(100, 100, 909, 482);
			setResizable(false);
	 }
	

	  
	 private Image scaleImage(Image image, int w, int h) {
	        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        return scaled;
	 }
	 

}
