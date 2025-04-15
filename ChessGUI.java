import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ChessGUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private final JButton creditsButton = new JButton("Credits");
    private final JLabel message = new JLabel("Have Fun!");
    private boolean isCheckersMode = false;
    private ChessBoardPanel chessBoardPanel;
    private JFrame mainFrame;

    public ChessGUI() {
        initializeGui();
    }

    public final void initializeGui() {
        PieceImages.loadImages();

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        gui.add(tools, BorderLayout.PAGE_START);

        JToggleButton modeToggle = new JToggleButton("Switch to Checkers");
        modeToggle.addActionListener(_ -> {
            isCheckersMode = !isCheckersMode;
            modeToggle.setText(isCheckersMode ? "Switch to Chess" : "Switch to Checkers");
            chessBoardPanel.setupNewGame(isCheckersMode);
        });

        Action newGameAction = new AbstractAction("New Board") {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessBoardPanel.setupNewGame(isCheckersMode);
            }
        };

        tools.add(modeToggle);
        tools.add(newGameAction);
        tools.add(new JButton("Resign"));
        tools.add(creditsButton);
        tools.add(message);

        creditsButton.addActionListener(_ -> showCreditsDialog());

        chessBoardPanel = new ChessBoardPanel(message);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(Color.PINK);
        boardConstrain.add(chessBoardPanel);
        gui.add(boardConstrain);
    }

    private void showCreditsDialog() {
        JFrame creditsFrame = new JFrame("Credits");
        creditsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        creditsFrame.setSize(250, 190);

        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

        creditsPanel.add(new JLabel("MADE BY:"));
        creditsPanel.add(new JLabel("  DJABA Zaki"));
        creditsPanel.add(new JLabel("  BOUAFIA Salah"));
        creditsPanel.add(new JLabel("  TALAMALI Mohamed Amir"));
        creditsPanel.add(new JLabel("  BENZETTA Sami"));
        creditsPanel.add(new JLabel("  OMEIRI Djasser"));
        creditsPanel.add(new JLabel("  BERDJANE Abderrahmane Mohamed"));
        creditsPanel.add(new JLabel("  BEGGAR Yacine"));
        creditsPanel.add(new JLabel("  ZERAOULIA Ahmed"));
        creditsFrame.add(creditsPanel);

        creditsFrame.setVisible(true);
    }

    public final JComponent getGui() {
        return gui;
    }

    public void startGame() {
        mainFrame = new JFrame("Board Champ");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(getGui());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}