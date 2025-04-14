import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class ZakTest {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel("Good game!");
    private final JButton creditsButton = new JButton("Credits");
    private static final String COLS = "ABCDEFGH";

    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;

    private Point selectedSquare = null;

    ZakTest() {
        initializeGui();
    }

    public final void initializeGui() {
        createImages();

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        Action newGameAction = new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
        tools.add(newGameAction);
        tools.add(new JButton("Save")); // TODO
        tools.add(new JButton("Restore")); // TODO
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO
        tools.addSeparator();
        tools.add(message);
        tools.add(creditsButton);

        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer une nouvelle fenêtre pour les crédits
                JFrame creditsFrame = new JFrame("Credits");
                creditsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                creditsFrame.setSize(300, 220);

                JPanel creditsPanel = new JPanel();
                creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

                creditsPanel.add(new JLabel("Made by:"));
                creditsPanel.add(new JLabel("DJABA Zaki"));
                creditsPanel.add(new JLabel("BOUAFIA Salah"));
                creditsPanel.add(new JLabel("TALAMALI Mohamed Amir"));
                creditsPanel.add(new JLabel("BENZETTA Sami"));
                creditsPanel.add(new JLabel("OMEIRI Djasser"));
                creditsPanel.add(new JLabel("BERDJANE Abderrahmane Mohamed"));
                creditsPanel.add(new JLabel("BEGGAR Yacine"));
                creditsPanel.add(new JLabel("ZERAOULIA Ahmed"));
                creditsFrame.add(creditsPanel);

                creditsFrame.setLocationRelativeTo(null);
                creditsFrame.setVisible(true);
            }
        });

        chessBoard = new JPanel(new GridLayout(0, 9)) {
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int) d.getWidth(), (int) d.getHeight());
                } else if (c != null &&
                        c.getWidth() > d.getWidth() &&
                        c.getHeight() > d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w > h ? h : w);
                return new Dimension(s, s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)));

        Color BGcolor = new Color(0, 119, 34);
        chessBoard.setBackground(BGcolor);
        
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(BGcolor);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                final int x = ii;
                final int y = jj;
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setIcon(new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));

                b.setBackground((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)
                        ? Color.WHITE
                        : Color.BLACK);

                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleSquareClick(x, y);
                    }
                });

                chessBoardSquares[jj][ii] = b;
            }
        }

        chessBoard.add(new JLabel(""));
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }

        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                if (jj == 0) {
                    chessBoard.add(new JLabel("" + (9 - (ii + 1)),
                            SwingConstants.CENTER));
                }
                chessBoard.add(chessBoardSquares[jj][ii]);
            }
        }

        setupNewGame();
    }

    private void createImages() {
        try {
            BufferedImage bi = ImageIO.read(getClass().getResource("./chess.png"));
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setupNewGame() {
        message.setText("Make your move!");
        selectedSquare = null;

        // Reset all squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoardSquares[i][j].setIcon(new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
            }
        }

        for (int i = 0; i < 8; i++) {
            chessBoardSquares[i][0].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][STARTING_ROW[i]]));
            chessBoardSquares[i][1].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][PAWN]));
            chessBoardSquares[i][6].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][PAWN]));
            chessBoardSquares[i][7].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][STARTING_ROW[i]]));
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

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ZakTest cg = new ZakTest();
            JFrame f = new JFrame("Chess Game");
            f.add(cg.getGui());
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.pack();
            f.setMinimumSize(f.getSize());
            f.setVisible(true);
        });
    }
}
