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
	private String tam;
	private Thread run;
	private JTextArea txt = null;
    private DefaultListModel modFile = new DefaultListModel();
    private DefaultListModel modClient = new DefaultListModel();
    public static DefaultListModel modHangdoi = new DefaultListModel();
    public static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    private JList listtt ;
    private JList list_dS_cLient, list_dS_hangDoi;
    public static ExecutorService exService = new ThreadPoolExecutor(
    		 1,														//S??? l?????ng Thread m???c ?????nh trong Pool
    		 1,														//S??? l?????ng t???i ??a Thread trong Pool, khi h??ng ?????i full, server qu?? t???i
            0L,														//0 ki???u Long
            TimeUnit.MILLISECONDS,
            queue);                    								//H??ng ?????i s???p x???p c??c ph???n t??? theo d???ng FIFO (nh???p tr?????c xu???t tr?????c)
    																	//(l???y ra ph???n t??? l??u ?????i nh???t trong h??ng ?????i)
    
    
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
		 setTitle("Server-Chia s??? m??y in trong m???ng LAN");
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
	        
	        JLabel lblNameClient = new JLabel("M??y Ch???");
	        lblNameClient.setFont(new Font("Tahoma", Font.BOLD, 22));
	        lblNameClient.setForeground(new Color(0, 0, 128));
	        lblNameClient.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNameClient.setBounds(329, 11, 295, 35);
	        contentPane.add(lblNameClient);
	        
	        lblNewLabel = new JLabel("C???ng");
	        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        lblNewLabel.setBounds(204, 58, 66, 18);
	        contentPane.add(lblNewLabel);
	        
	        txtPortServer = new JTextField("9999");
	        txtPortServer.setBounds(280, 58, 90, 20);
	        contentPane.add(txtPortServer);
	        txtPortServer.setColumns(10);
	        
	         btnKhoiDongServer = new JButton("Kh???i ?????ng m??y ch???");
	         btnKhoiDongServer.setFont(new Font("Tahoma", Font.BOLD, 11));
	       
	        
	        btnKhoiDongServer.setBackground(new Color(0, 0, 128));
	        btnKhoiDongServer.setForeground(new Color(255, 255, 255));
	        btnKhoiDongServer.setBounds(390, 57, 152, 23);
	        contentPane.add(btnKhoiDongServer);
	        
	         btnDungServer = new JButton("D???ng m??y ch???");
	         btnDungServer.setFont(new Font("Tahoma", Font.BOLD, 11));
	        btnDungServer.setForeground(new Color(255, 255, 255));
	        btnDungServer.setBackground(new Color(0, 0, 128));
	        btnDungServer.setBounds(552, 57, 143, 23);
	        contentPane.add(btnDungServer);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(172, 254, 382, 150);
	        contentPane.add(scrollPane);
	        
	         listtt = new JList();
	        scrollPane.setViewportView(listtt);
	        
	        JScrollPane scrollPane_1 = new JScrollPane();
	        scrollPane_1.setBounds(172, 111, 382, 113);
	        contentPane.add(scrollPane_1);
	        
	         txt = new JTextArea();
	         scrollPane_1.setViewportView(txt);
	       
	        
	        JLabel lblNewLabel_1 = new JLabel("Tr???ng th??i k???t n???i");
	        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel_1.setBounds(172, 87, 125, 23);
	        contentPane.add(lblNewLabel_1);
	        
	        JLabel lblNewLabel_2 = new JLabel("C??c t???p ???? in th??nh c??ng");
	        lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 13));
	        lblNewLabel_2.setBounds(173, 231, 165, 19);
	        contentPane.add(lblNewLabel_2);
	        
	        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane.setBounds(576, 86, 221, 138);
	        contentPane.add(tabbedPane);
	        
	        JScrollPane scrollPane_2 = new JScrollPane();
	        tabbedPane.addTab("DS k???t n???i", null, scrollPane_2, null);
	        
	         list_dS_cLient = new JList();
	        scrollPane_2.setViewportView(list_dS_cLient);
	        
	        
	        JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane_1.setBounds(576, 230, 221, 158);
	        JScrollPane scrollPane_3 = new JScrollPane();
	         list_dS_hangDoi = new JList();
	        scrollPane_3.setViewportView(list_dS_hangDoi);
	        tabbedPane_1.addTab("DS h??ng ?????i", null, scrollPane_3, null);
	        contentPane.add(tabbedPane_1);
	        
	        JLabel lblD = new JLabel();
	        lblD.setForeground(new Color(0, 0, 153));
	        lblD.setHorizontalAlignment(SwingConstants.CENTER);
	        lblD.setFont(new Font("Tahoma", Font.BOLD, 13));
	        lblD.setText("d");
	        lblD.setBounds(729, 386, 68, 18);
	        contentPane.add(lblD);
	        
	        JLabel lblSLngTp = new JLabel();
	        lblSLngTp.setText("S??? l?????ng t???p ??ang ?????i");
	        lblSLngTp.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        lblSLngTp.setBounds(576, 386, 143, 18);
	        contentPane.add(lblSLngTp);
	        
	        
	        btnKhoiDongServer.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent e) {
	         		listtt.setModel(modFile);
	         		list_dS_cLient.setModel(modClient);
	         		list_dS_hangDoi.setModel(modHangdoi);
	         		lblD.setText(tam);
	         		
	         	        run = new Thread(new Runnable() {
	         	            @Override
	         	            public void run() {
	         	            	try {
	         	                    server = new ServerSocket(Integer.parseInt(txtPortServer.getText().trim()));
	         	                    txt.append("??ang kh???i ?????ng m??y ch???...\n");
	         	                    while (true) {
	         	                		ServerThread sv = new ServerThread(server.accept(), modFile, 
	         	                				modClient, txt);
	         	                	 	sv.start();
	         	                	 	listtt.setModel(sv.getModFile());
	         	                	 	list_dS_cLient.setModel(sv.getModClient());
	         	                	 	txt = sv.getTxt();
	         	                		list_dS_hangDoi.setModel(modHangdoi);
	         	                		tam =String.valueOf(modHangdoi.size() + 1);
	         	                		lblD.setText(tam);
	         	                	 
	         	                   }
	         	                } catch (Exception e) {
	         	                    JOptionPane.showMessageDialog(Server.this, e, "L???i", JOptionPane.ERROR_MESSAGE);
	         	                }

	         	            }
	         	        });
	         	        run.start();
	         	}
	         });
	        
	      
	        
	 
	        setLocationRelativeTo(null);
//			setVisible(true);
			setBounds(100, 100, 830, 465);
			setResizable(false);
	 }
	  
	 private Image scaleImage(Image image, int w, int h) {
	        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        return scaled;
	 }
	 }
