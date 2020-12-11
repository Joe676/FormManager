package GUI;

import java.awt.EventQueue;

public class ManagerGUI{

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
 			@Override
 			public void run() {
 				new ManagerWindow();
 			}
 		});
 	}

}
