package Client;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import File.FileInfo;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client extends JFrame {

    private static final long serialVersionUID = 1L;
    
	private JPanel contentPane;
	private JTextField txtPortClient;
	private JTextField txtDiaChiIP;
	private JTextField txtTenClient;
	private JButton btnTimFile;
	private JSpinner spinner_soLuong;
	private JButton btnKetNoi;
	private JButton btnIn;
	private JList list_trangThaiC;
	private DefaultListModel model_trangThai;
	private  JComboBox comboBox;
	private JLabel lblNameFile;
	
	Socket socket = null;
	ObjectOutputStream output;
	String filePath = null;
	FileInfo chon;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Client() {
		initComponents();
	}

	public void initComponents() {
		model_trangThai = new DefaultListModel();
		
		setTitle("Client - Chia sẽ máy in trong mạng LAN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		
		ImageIcon originalIcon = new ImageIcon(Client.class.getResource("/Image/mayclient.jfif"));
        JLabel originalLabel = new JLabel(originalIcon);
        int width = originalIcon.getIconWidth() /2;
        int height = originalIcon.getIconHeight() / 2;
        Image scaled = scaleImage(originalIcon.getImage(), width, height);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        contentPane.setLayout(null);
        JLabel newLabel = new JLabel(scaledIcon);
        newLabel.setBounds(22, 79, 136, 132);
        newLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        contentPane.add(newLabel);
        
        JLabel lblNameClient = new JLabel("Máy khách");
        lblNameClient.setBounds(310, 5, 128, 33);
        lblNameClient.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblNameClient.setForeground(new Color(0, 0, 128));
        lblNameClient.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNameClient);
        
        JLabel lblNewLabel = new JLabel("Cổng");
        lblNewLabel.setToolTipText("Nhập cổng Máy chủ");
        lblNewLabel.setBounds(219, 125, 49, 18);
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
        contentPane.add(lblNewLabel);
        
        txtPortClient = new JTextField();
        txtPortClient.setBounds(278, 125, 145, 20);
        txtPortClient.setBackground(new Color(245, 245, 245));
        txtPortClient.setColumns(10);
        contentPane.add(txtPortClient);
        
        JLabel lblaChIp = new JLabel("Địa chỉ IP");
        lblaChIp.setToolTipText("Nhập địa chỉ IP Máy khách");
        lblaChIp.setBounds(201, 96, 63, 18);
        lblaChIp.setHorizontalAlignment(SwingConstants.RIGHT);
        lblaChIp.setFont(new Font("Dialog", Font.BOLD, 13));
        contentPane.add(lblaChIp);
        
        txtDiaChiIP = new JTextField();
        txtDiaChiIP.setText("Localhost");
        txtDiaChiIP.setBounds(278, 96, 145, 20);
        txtDiaChiIP.setBackground(new Color(245, 245, 245));
        txtDiaChiIP.setColumns(10);
        contentPane.add(txtDiaChiIP);
        
        JLabel lblNewLabel_1 = new JLabel("Chọn tệp cần in");
        lblNewLabel_1.setBounds(169, 297, 125, 20);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_1.setForeground(new Color(0, 0, 102));
        contentPane.add(lblNewLabel_1);
        
        btnTimFile = new JButton("Tìm tệp");
        btnTimFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 final JFileChooser fc = new JFileChooser();
        	        fc.showOpenDialog(null);
        	        try {
        	            if (fc.getSelectedFile() != null) {
        	            	lblNameFile.setText(fc.getSelectedFile().getName());
        	            	filePath = fc.getSelectedFile().getPath();
        	            }
        	        } catch (Exception ex) {
        	            ex.printStackTrace();
        	        }
        		
        	}
        });
        
        btnTimFile.setBounds(432, 298, 86, 25);
        btnTimFile.setForeground(new Color(255, 255, 255));
        btnTimFile.setBackground(new Color(0, 0, 128));
        btnTimFile.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnTimFile);
        
        JLabel lblNewLabel_2 = new JLabel("Số lượng file cần in");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_2.setForeground(new Color(0, 0, 128));
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2.setBounds(169, 368, 141, 30);
        contentPane.add(lblNewLabel_2);
        
         spinner_soLuong = new JSpinner();
        spinner_soLuong.setBounds(310, 368, 107, 25);
        contentPane.add(spinner_soLuong);
        
         btnIn = new JButton("In");
         btnIn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		String destinationDir = "./data/";
         		sendFile(filePath,destinationDir);
         	
         	}
         });
        btnIn.setForeground(new Color(255, 255, 255));
        btnIn.setBackground(new Color(0, 0, 128));
        btnIn.setBounds(558, 368, 115, 30);
        contentPane.add(btnIn);
        
        JLabel lblNewLabel_5 = new JLabel("Trạng thái kết nối");
        lblNewLabel_5.setForeground(new Color(0, 0, 102));
        lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_5.setBounds(172, 154, 125, 25);
        contentPane.add(lblNewLabel_5);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(169, 179, 504, 107);
        contentPane.add(scrollPane);
        
        list_trangThaiC = new JList();
        scrollPane.setViewportView(list_trangThaiC);
        
        JLabel lblTnMy = new JLabel("Tên");
        lblTnMy.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTnMy.setFont(new Font("Dialog", Font.BOLD, 13));
        lblTnMy.setBounds(233, 64, 31, 18);
        contentPane.add(lblTnMy);
        
        txtTenClient = new JTextField();
        txtTenClient.setToolTipText("Nhập tên Máy khách");
        txtTenClient.setColumns(10);
        txtTenClient.setBackground(new Color(245, 245, 245));
        txtTenClient.setBounds(278, 64, 145, 20);
        contentPane.add(txtTenClient);
        
         btnKetNoi = new JButton("Kết nối");
         btnKetNoi.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		try {
         			model_trangThai.addElement("Client connecting ...");
					list_trangThaiC.setModel(model_trangThai);
					socket = new Socket(txtDiaChiIP.getText(),Integer.parseInt(txtPortClient.getText()));
					model_trangThai.addElement("Client is connected");
					list_trangThaiC.setModel(model_trangThai);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
         	}
         });
   
        btnKetNoi.setBackground(new Color(0, 0, 128));
        btnKetNoi.setForeground(new Color(255, 255, 255));
        btnKetNoi.setBounds(488, 64, 118, 25);
        contentPane.add(btnKetNoi);
        
         lblNameFile = new JLabel("abc.doc");
        lblNameFile.setForeground(new Color(153, 51, 102));
        lblNameFile.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNameFile.setBounds(310, 334, 363, 23);
        contentPane.add(lblNameFile);
        
         comboBox = new JComboBox();
        comboBox.setBounds(310, 297, 100, 26);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Img", "File"}));
        contentPane.add(comboBox);
        
        JButton btnngKtNi = new JButton("Đóng kết nối");
        btnngKtNi.setForeground(Color.WHITE);
        btnngKtNi.setBackground(new Color(0, 0, 128));
        btnngKtNi.setBounds(488, 96, 118, 25);
        contentPane.add(btnngKtNi);
        
		setBounds(100, 100, 730, 448);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private Image scaleImage(Image image, int w, int h) {
	        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        return scaled;
	}
	
	 public void sendFile(String sourceFilePath, String destinationDir) {
	        DataOutputStream outToServer = null;
	        ObjectOutputStream oos = null;
	        ObjectInputStream ois = null;

	        try {
	        	// get file info
	            FileInfo fileInfo = getFileInfo(sourceFilePath, destinationDir);

	            // make greeting
	            outToServer = new DataOutputStream(socket.getOutputStream());
	            outToServer.writeUTF("Hello: " + socket.getLocalSocketAddress()+"\n"+socket.getLocalSocketAddress()+" file sent: "+fileInfo.getFilename());

	            
	            // send file
	            oos = new ObjectOutputStream(socket.getOutputStream());
	            oos.writeObject(fileInfo);

	            // get confirmation
	            ois = new ObjectInputStream(socket.getInputStream());
	            fileInfo = (FileInfo) ois.readObject();
	            if (fileInfo != null) {
	                model_trangThai.addElement("send file to server " + fileInfo.getStatus() + "\n");
					list_trangThaiC.setModel(model_trangThai);
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } finally {
	            // close all stream
	            closeStream(oos);
	            closeStream(ois);
	            closeStream(outToServer);
	            closeSocket(socket);
	        }
	    }

	    /**
	     * get source file info
	     * 
	     * Quynh - Xuan - Vu
	     * @param sourceFilePath
	     * @param destinationDir
	     * @return FileInfo
	     */
	    private FileInfo getFileInfo(String sourceFilePath, String destinationDir) {
	        FileInfo fileInfo = null;
	        BufferedInputStream bis = null;
	        try {
	            File sourceFile = new File(sourceFilePath);
	            bis = new BufferedInputStream(new FileInputStream(sourceFile));
	            fileInfo = new FileInfo();
	            byte[] fileBytes = new byte[(int) sourceFile.length()];
	            // get file info
	            bis.read(fileBytes, 0, fileBytes.length);
	            fileInfo.setFilename(sourceFile.getName());
	            fileInfo.setDataBytes(fileBytes);
	            fileInfo.setDestinationDirectory(destinationDir);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } finally {
	            closeStream(bis);
	        }
	        return fileInfo;
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
