
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	// Create the constructor
	
	GameFrame() {
		// Create an instance of game panel and add it to to the constructor game panel with add method
		
		this.add(new GamePanel());
		this.setTitle("Snake by @robinsonjesus");
		// Close operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		this.setResizable(false);
		// this pack function will actually take our JFrame and fit it snugly ("ajustarlo suavemente") around all of
		// the componentes that we add to the frame
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}

}
