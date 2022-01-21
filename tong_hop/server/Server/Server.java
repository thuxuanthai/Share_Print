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

import data.Data;


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
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class Server extends JFrame{
    private static final long serialVersionUID = 1L;
    
	private JPanel contentPane;
	private JTextField txtPortServer;
	private JLabel lblNewLabel;
	private JButton btnKhoiDongServer;
	private JButton btnDungServer;
	private Thread run;
	private JTextArea txt = null;
    private DefaultListModel modFile = new DefaultListModel();
    private DefaultListModel modClient = new DefaultListModel();
    private JList listtt ;
    private JList list_dS_cLient;
    public static ExecutorService exService = new ThreadPoolExecutor(
    		 1,
    		 1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    
    
    private ServerSocket server;
	
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
	        lblNameClient.setBounds(329, 11, 295, 35);
	        contentPane.add(lblNameClient);
	        
	        lblNewLabel = new JLabel("Cổng");
	        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel.setBounds(204, 58, 66, 18);
	        contentPane.add(lblNewLabel);
	        
	        txtPortServer = new JTextField("9999");
	        txtPortServer.setBounds(280, 58, 90, 20);
	        contentPane.add(txtPortServer);
	        txtPortServer.setColumns(10);
	        
	         btnKhoiDongServer = new JButton("Khởi động máy chủ");
	         btnKhoiDongServer.setFont(new Font("Tahoma", Font.BOLD, 11));
	       
	        
	        btnKhoiDongServer.setBackground(new Color(0, 0, 128));
	        btnKhoiDongServer.setForeground(new Color(255, 255, 255));
	        btnKhoiDongServer.setBounds(390, 57, 152, 23);
	        contentPane.add(btnKhoiDongServer);
	        
	         btnDungServer = new JButton("Dừng máy chủ");
	         btnDungServer.setFont(new Font("Tahoma", Font.BOLD, 11));
	        btnDungServer.setForeground(new Color(255, 255, 255));
	        btnDungServer.setBackground(new Color(0, 0, 128));
	        btnDungServer.setBounds(552, 57, 143, 23);
	        contentPane.add(btnDungServer);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(172, 230, 382, 168);
	        contentPane.add(scrollPane);
	        
	         listtt = new JList();
	        scrollPane.setViewportView(listtt);
	        
	        JScrollPane scrollPane_1 = new JScrollPane();
	        scrollPane_1.setBounds(172, 111, 382, 92);
	        contentPane.add(scrollPane_1);
	        
	         txt = new JTextArea();
	         scrollPane_1.setViewportView(txt);
	       
	        
	        JLabel lblNewLabel_1 = new JLabel("Trạng thái kết nối");
	        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel_1.setBounds(172, 87, 125, 23);
	        contentPane.add(lblNewLabel_1);
	        
	        JLabel lblNewLabel_2 = new JLabel("Các tập tin nhận được");
	        lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel_2.setBounds(173, 211, 165, 19);
	        contentPane.add(lblNewLabel_2);
	        
	        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane.setBounds(576, 86, 174, 312);
	        contentPane.add(tabbedPane);
	        
	        JScrollPane scrollPane_2 = new JScrollPane();
	        tabbedPane.addTab("DS kết nối", null, scrollPane_2, null);
	        
	         list_dS_cLient = new JList();
	        scrollPane_2.setViewportView(list_dS_cLient);
	        
	        
	        btnKhoiDongServer.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent e) {
	         		listtt.setModel(modFile);
	         		list_dS_cLient.setModel(modClient);
	         		
	         	        run = new Thread(new Runnable() {
	         	            @Override
	         	            public void run() {
	         	            	try {
	         	                    server = new ServerSocket(Integer.parseInt(txtPortServer.getText().trim()));
	         	                    txt.append("Server stating ...\n");
	         	                    while (true) {
	         	                		ServerThread sv = new ServerThread(server.accept(), modFile, 
	         	                				modClient, txt);
	         	                	 	sv.start();
	         	                	 	listtt.setModel(sv.getModFile());
	         	                	 	list_dS_cLient.setModel(sv.getModClient());
	         	                	 	txt = sv.getTxt();
	         	                	 
	         	                   }
	         	                } catch (Exception e) {
	         	                    JOptionPane.showMessageDialog(Server.this, e, "Error", JOptionPane.ERROR_MESSAGE);
	         	                }

	         	            }
	         	        });
	         	        run.start();
	         	}
	         });
	        
	      
	        
	 
	        setLocationRelativeTo(null);
//			setVisible(true);
			setBounds(100, 100, 806, 465);
			setResizable(false);
	 }
	  
	 private Image scaleImage(Image image, int w, int h) {
	        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        return scaled;
	 }
	 }
