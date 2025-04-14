import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class ChessGUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private final JButton[][] chessBoardSquares = new JButton[8][8];
    private final Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JButton creditsButton = new JButton("Credits");
    private final JLabel message = new JLabel("Good game!");
    private boolean isCheckersMode = false;
    private Point selectedSquare = null;
    private static final char[] COLS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK };
    public static final int BLACK = 0, WHITE = 1;

    ChessGUI() {
        initializeGui();
    }

    public final void initializeGui() {
        createImages();
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        gui.add(tools, BorderLayout.PAGE_START);
        JToggleButton modeToggle = new JToggleButton("Switch to Checkers");
        modeToggle.addActionListener(_ -> {
            isCheckersMode = !isCheckersMode;
            modeToggle.setText(isCheckersMode ? "Switch to Chess" : "Switch to Checkers");
            setupNewGame();
        });
        Action newGameAction = new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
        tools.add(modeToggle);
        tools.add(newGameAction);
        tools.add(new JButton("Resign")); // add functionality!
        tools.add(creditsButton);
        tools.add(message);
        creditsButton.addActionListener(_ -> {
            // Créer une nouvelle fenêtre pour les crédits
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
        });

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), new LineBorder(Color.BLACK)));
        chessBoard.setPreferredSize(new Dimension(640, 640));
        chessBoard.setBackground(Color.PINK);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(Color.PINK);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = getJButton(buttonMargin, j, i);
                chessBoardSquares[j][i] = b;
            }
        }
        chessBoard.add(new JLabel(""));
        for (int i = 0; i < 8; i++) {
            chessBoard.add(new JLabel(String.valueOf(COLS[i]), SwingConstants.CENTER));
        }
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (j == 0) {
                    chessBoard.add(new JLabel("" + (i+1), SwingConstants.CENTER));
                }
                chessBoard.add(chessBoardSquares[j][i]);
            }
        }
    }

    private JButton getJButton(Insets buttonMargin, int j, int i) {
        JButton b = new JButton();
        b.setMargin(buttonMargin);
        ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        b.setIcon(icon);
        if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
            b.setBackground(Color.WHITE);
        } else {
            b.setBackground(Color.BLACK);
        }
        b.addActionListener(_ -> handleSquareClick(i,j));
        return b;
    }

    public final JComponent getGui() {
        return gui;
    }

    private void createImages() {
        try {
            BufferedImage bi = ImageIO.read(new URL("https://i.sstatic.net/memI0.png"));
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = bi.getSubimage(j * 64, i * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            System.exit(1);
        }
    }

    private void setupNewGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoardSquares[i][j].setIcon(null);
            }
        }

        if (isCheckersMode) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 8; col++) {
                    if ((row + col) % 2 != 0) {
                        chessBoardSquares[col][row].setText("⬤");
                        chessBoardSquares[col][row].setForeground(Color.WHITE);
                        chessBoardSquares[col][row].setHorizontalAlignment(SwingConstants.CENTER);
                        chessBoardSquares[col][row].setFont(new Font("SansSerif", Font.BOLD, 36));
                    }
                }
            }

            for (int row = 5; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ((row + col) % 2 != 0) {
                        chessBoardSquares[col][row].setText("⬤");
                        chessBoardSquares[col][row].setForeground(Color.RED);
                        chessBoardSquares[col][row].setHorizontalAlignment(SwingConstants.CENTER);
                        chessBoardSquares[col][row].setFont(new Font("SansSerif", Font.BOLD, 36));
                    }
                }
            }
        } else {
            for (int i = 0; i < STARTING_ROW.length; i++) {
                chessBoardSquares[i][0].setIcon(new ImageIcon(chessPieceImages[BLACK][STARTING_ROW[i]]));
                chessBoardSquares[i][1].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
                chessBoardSquares[i][6].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
                chessBoardSquares[i][7].setIcon(new ImageIcon(chessPieceImages[WHITE][STARTING_ROW[i]]));
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessBoardSquares[i][j].setText("");
                }
            }
        }
    }

    private void handleSquareClick(int y, int x) {
        JButton clicked = chessBoardSquares[x][y];
        Icon icon = clicked.getIcon();

        if (selectedSquare == null) {
            if (icon != null && !(icon instanceof ImageIcon && ((ImageIcon) icon).getImage() == null)) {
                selectedSquare = new Point(x, y);
                message.setText("Piece selected at " + (char) ('A' + x) + (8 - y));
            }
        } else {
            JButton fromButton = chessBoardSquares[selectedSquare.x][selectedSquare.y];
            Icon fromIcon = fromButton.getIcon();

            clicked.setIcon(fromIcon);
            fromButton.setIcon(new ImageIcon(
                    new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
            selectedSquare = null;
            message.setText("Piece moved.");
        }
    }

    public static void main(String[] args) {
        ChessGUI cg = new ChessGUI();
        JFrame f = new JFrame("Board Champ");

        f.add(cg.getGui());
        f.pack();
        f.setVisible(true);
    }
}