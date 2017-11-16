/**
 * 
 */

/**
 * @author 072971120
 *
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class GUI {

	/**
	 * @param
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args)  {

		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}


		int x=2;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		//creates main JFrame
		JFrame mainWindow = new JFrame ("Textbook Database");
		mainWindow.setBackground(Color.WHITE);
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setSize((int)width, (int)height);
		mainWindow.setAlwaysOnTop (true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    

		//creates menu on west panel
		JPanel menuScreen = new JPanel ();
		menuScreen.setLayout(new GridLayout(7,1));

		//adds button to assign student
		JButton textbookNum = new JButton("Assign Student");
		textbookNum.addActionListener (new textbookNumListener());
		menuScreen.add(textbookNum);

		//adds button to clear all student
		JButton clear = new JButton("Clear Students");
		clear.addActionListener (new clearListener());
		menuScreen.add(clear);

		//adds button to sort students
		JButton sort = new JButton("Sort");
		sort.addActionListener (new sortListener());
		menuScreen.add(sort);

		//adds button to highlight overdue students
		JButton overdue = new JButton("Overdue");
		overdue.addActionListener (new overdueListener());
		menuScreen.add(overdue);

		//adds button to add a textbook
		JButton add = new JButton("Add Textbook");
		add.addActionListener (new addListener());
		menuScreen.add(add);

		//adds button to remove a textbook
		JButton remove = new JButton("Remove Textbook");
		remove.addActionListener (new removeListener());
		menuScreen.add(remove);

		//adds button to import textbooks from a list
		JButton importBook = new JButton("Import Textbooks");
		importBook.addActionListener (new importBookListener());
		menuScreen.add(importBook);

		//adds menu to main panel
		mainWindow.add(menuScreen,BorderLayout.WEST);

		//creates spreadsheet in centre panel

		//8 by x spreadsheet
		//textbook number, student number, student lastname, student firstname, teacher, date out, coursecode + section, returned checkbox
		JPanel table = new JPanel();
		table.setLayout(new GridLayout(x,8));
		table.setPreferredSize (new Dimension(700,700));

		JLabel textNum1 = new JLabel("000000");
		JLabel stuNum1 = new JLabel("123456789");
		JLabel stuLastName1 = new JLabel("RHHS");
		JLabel stuFirstName1 = new JLabel("Math");
		JLabel teacher1 = new JLabel("Ms. Sinatra");
		JLabel dateOut1 = new JLabel("01/01/1999");
		JLabel courseCode1 = new JLabel("MDM4UE-01");
		JCheckBox returned1 = new JCheckBox();

		table.add(textNum1, 1, 0);
		table.add(stuNum1, 1, 1);
		table.add(stuLastName1, 1, 2);
		table.add(stuFirstName1,1, 3);
		table.add(teacher1, 1, 4);
		table.add(dateOut1, 1, 5);
		table.add(courseCode1, 1, 6);
		table.add(returned1, 1, 7);

		JLabel textNum = new JLabel("Textbook Number");
		JLabel stuNum = new JLabel("Student Number");
		JLabel stuLastName = new JLabel("Student Last Name");
		JLabel stuFirstName = new JLabel("Student First Name");
		JLabel teacher = new JLabel("Teacher");
		JLabel dateOut = new JLabel("Date out: MM/DD/YYYY");
		JLabel courseCode = new JLabel("Course Code-Section");
		JLabel returned = new JLabel("Returned");

		textNum.setVerticalAlignment(JLabel.TOP);
		stuNum.setVerticalAlignment(JLabel.TOP);
		stuLastName.setVerticalAlignment(JLabel.TOP);
		stuFirstName.setVerticalAlignment(JLabel.TOP);
		teacher.setVerticalAlignment(JLabel.TOP);
		dateOut.setVerticalAlignment(JLabel.TOP);
		courseCode.setVerticalAlignment(JLabel.TOP);
		returned.setVerticalAlignment(JLabel.TOP);

		textNum1.setVerticalAlignment(JLabel.TOP);
		stuNum1.setVerticalAlignment(JLabel.TOP);
		stuLastName1.setVerticalAlignment(JLabel.TOP);
		stuFirstName1.setVerticalAlignment(JLabel.TOP);
		teacher1.setVerticalAlignment(JLabel.TOP);
		dateOut1.setVerticalAlignment(JLabel.TOP);
		courseCode1.setVerticalAlignment(JLabel.TOP);
		returned1.setVerticalAlignment(JLabel.TOP);

		textNum.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		stuNum.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		stuLastName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		stuFirstName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teacher.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dateOut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		courseCode.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		returned.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		textNum1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		stuNum1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		stuLastName1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		stuFirstName1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teacher1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dateOut1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		courseCode1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		returned1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		table.add(textNum, 0, 0);
		table.add(stuNum, 0, 1);
		table.add(stuLastName, 0, 2);
		table.add(stuFirstName,0, 3);
		table.add(teacher, 0, 4);
		table.add(dateOut, 0, 5);
		table.add(courseCode, 0, 6);
		table.add(returned, 0, 7);



		mainWindow.add(table,BorderLayout.CENTER);

		//		//2d array for each spot
		//		spotLabel = new JLabel[6][7];
		//
		//		//initializes the pictures for both players and empty
		//		imageIcon1 = new ImageIcon("blank.png");
		//		imageIcon2 = new ImageIcon("player 1 piece.png");
		//		imageIcon3 = new ImageIcon("player 2 piece.png");
		//
		//		//sets each label for each connect 4 spot
		//		for(int i = 5; i > -1; i--){
		//			for (int j = 0; j < 7; j++){
		//				if(board[i][j] == 1){
		//					spotLabel[i][j] = new JLabel(imageIcon2);
		//				}else if(board[i][j] == -1){
		//					spotLabel[i][j] = new JLabel(imageIcon3);
		//				}else{
		//					spotLabel[i][j] = new JLabel(imageIcon1);
		//				}
		//				spreadsheet.add(spotLabel[i][j]);
		//			}
		//		}

		//south panel
		JPanel tabs = new JPanel();
		tabs.setLayout(new FlowLayout (FlowLayout.LEFT));
		tabs.setBorder(BorderFactory.createEmptyBorder(10,150,10,10));
		JButton tab1 = new JButton();
		tab1.setText("Textbook Name");
		tabs.add(tab1);

		mainWindow.add(tabs,BorderLayout.SOUTH);

		//north panel
		JPanel topMenu = new JPanel();
		topMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		topMenu.setBorder(BorderFactory.createEmptyBorder(10,150,10,10));

		JButton openData = new JButton("Open Database");
		openData.addActionListener (new openDataListener());
		topMenu.add(openData);

		JButton newData = new JButton("New Database");
		newData.addActionListener (new newDataListener());
		topMenu.add(newData);

		mainWindow.add(topMenu,BorderLayout.NORTH);


		mainWindow.setResizable(true);
		mainWindow.setVisible(true);
	}

	static class clearListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		}
	}

	static class sortListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		}
	}

	static class overdueListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		}
	}

	static class addListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		}
	}

	static class textbookNumListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		}
	}

	static class removeListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		}
	}

	static class importBookListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			JFileChooser jfc = new JFileChooser (); 

			jfc.setDialogTitle("Custom button");
			int returnValue = jfc.showOpenDialog(null);
			// int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());
			}
			//			int returnValue1 = jfc.showDialog(null, "A button!");
			//			if (returnValue1 == JFileChooser.APPROVE_OPTION) {
			//				System.out.println(jfc.getSelectedFile().getPath());
			//			}
			//			JFrame chooseWindow = new JFrame ();
			//			chooseWindow.add(choose);
			//			chooseWindow.setLayout(new BorderLayout());
			//			chooseWindow.setSize(500, 750);
			//			chooseWindow.setAlwaysOnTop (true);
			//			chooseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
			//			chooseWindow.setResizable(true);
			//			chooseWindow.setVisible(true);
		}
	}
	static class newDataListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
		}
	}
	static class openDataListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
		}
	}
}