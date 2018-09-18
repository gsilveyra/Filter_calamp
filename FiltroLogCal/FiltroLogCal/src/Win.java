import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JSeparator;

import java.awt.Label;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Toolkit;
public class Win {
private	JLabel lblNewLabel;
private JFrame frmFilterLogCalamp;
public static File selectedFile;
Label label;
File selectedPath ;
JTextField textField;
JRadioButton rdn;
JCheckBox check;
String idEquipo = "_";


	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Win window = new Win();
					window.frmFilterLogCalamp.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Win() {
		initialize();
	}

	private void initialize() {
		frmFilterLogCalamp = new JFrame();
		frmFilterLogCalamp.setIconImage(Toolkit.getDefaultToolkit().getImage(Win.class.getResource("/resources/light-bulb.png")));
		//frmFilterLogCalamp.setIconImage(Toolkit.getDefaultToolkit().getImage(Win.class.getResource("/javax/swing/plaf/metal/icons/ocean/newFolder.gif")));
		frmFilterLogCalamp.setTitle("Filtro Log Calamp v4.0");
		frmFilterLogCalamp.setResizable(false);
		frmFilterLogCalamp.setBounds(100, 100, 502, 194);
		frmFilterLogCalamp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFilterLogCalamp.getContentPane().setLayout(null);
		
		JButton btnOpenFile = new JButton("1-Buscar Archivo");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser fileChooser = new JFileChooser();
			        int returnValue = fileChooser.showOpenDialog(null);
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			          selectedFile = fileChooser.getSelectedFile();
			          selectedPath = fileChooser.getCurrentDirectory();
			          lblNewLabel.setText(selectedFile.getPath());
			}
			}
		});
		btnOpenFile.setBounds(11, 11, 118, 37);
		frmFilterLogCalamp.getContentPane().add(btnOpenFile);
		JButton btnArranca = new JButton("2-Filtrar");
		btnArranca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					idEquipo=textField.getText();
					label.setText("Procesando......");
					// Open the file INPUT
					File file = new File(selectedFile.toString());
					FileInputStream fstream = new FileInputStream(file);
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					// Write to file OUTPUT
					File fout = new File(selectedPath+"\\"+idEquipo +"_"+selectedFile.getName());
					FileOutputStream fos = new FileOutputStream(fout);
				 	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				 	String strLine;
					String strLine2 = null;
					//Read File Line By Line
					while ((strLine = br.readLine()) != null)   {
						//System.out.println("1");
						int pos=strLine.indexOf("'");  			//POSICION DE TILDE DONDE ARRANCA PAQUETE POSTA
						int posb=strLine.lastIndexOf("'");
							if((posb-pos)>40) {
								//System.out.println("2");
								strLine2=strLine.substring(pos+1, posb);
								if(check.isSelected()) {
								//	System.out.println("3");
									int a=strLine2.indexOf(textField.getText().trim());
									//System.out.println(a);
									if(a != -1) {
									//	System.out.println("4");
										bw.write(strLine2);
										bw.newLine();
									}
								}else {
									bw.write(strLine2);
									bw.newLine();
								}
								
								//System.out.println (strLine2);
								
							}
					}
					bw.close();
					br.close();
					label.setText("LISTO!!!!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnArranca.setBounds(10, 59, 118, 37);
		frmFilterLogCalamp.getContentPane().add(btnArranca);
		lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lblNewLabel.setForeground(Color.GRAY);
	    lblNewLabel.setBounds(139, 24, 371, 14);
		frmFilterLogCalamp.getContentPane().add(lblNewLabel);
		JButton btnNewButton = new JButton("3-Abrir Carpeta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    try {
					Desktop.getDesktop().open(selectedPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(11, 107, 118, 37);
		frmFilterLogCalamp.getContentPane().add(btnNewButton);
		
		label = new Label("");
		label.setFont(new Font("Calibri", Font.BOLD, 19));
		label.setBounds(172, 59, 113, 59);
		frmFilterLogCalamp.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBounds(311, 89, 157, 23);
		frmFilterLogCalamp.getContentPane().add(textField);
		textField.setColumns(10);
		
		check = new JCheckBox("Filtro IdEquipo");
		check.setBounds(311, 59, 126, 23);
		frmFilterLogCalamp.getContentPane().add(check);
	
	}
}
