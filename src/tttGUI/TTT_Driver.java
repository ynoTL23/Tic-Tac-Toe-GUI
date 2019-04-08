package tttGUI;

public class TTT_Driver {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TTT_GUI ttt = new TTT_GUI();
			}
		});
	}
	
}
