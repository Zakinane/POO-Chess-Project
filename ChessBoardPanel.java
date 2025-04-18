import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

public class ChessBoardPanel extends JPanel {

    private final JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private Point selectedSquare = null;
    private final JLabel messageLabel;
    private static final char[] COLS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK };
    public static final int BLACK = 0, WHITE = 1;

    public ChessBoardPanel(JLabel messageLabel) {
        this.messageLabel = messageLabel;
        initializeChessBoard();
        setupNewGame(false,false,false,false);
    }

    private void initializeChessBoard() {
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
        chessBoard.setPreferredSize(new Dimension(640, 640));
        chessBoard.setBackground(Color.PINK);
        setLayout(new GridBagLayout());
        setBackground(Color.PINK);
        add(chessBoard);

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
                    chessBoard.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
                }
                chessBoard.add(chessBoardSquares[j][i]);
            }
        }
    }

    private JButton getJButton(Insets buttonMargin, int x, int y) {
        JButton b = new JButton();
        b.setMargin(buttonMargin);
        ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        b.setIcon(icon);
        if ((x % 2 == 1 && y % 2 == 1) || (x % 2 == 0 && y % 2 == 0)) {
            b.setBackground(Color.WHITE);
        } else {
            b.setBackground(Color.BLACK);
        }
        b.addActionListener(_ -> handleSquareClick(y, x));
        return b;
    }

    public void setupNewGame(boolean isChessMode,Boolean isCheckersMode, Boolean isBattleshipMode, Boolean isTicMode) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoardSquares[i][j].setIcon(null);
                chessBoardSquares[i][j].setText("");
                chessBoardSquares[i][j].setForeground(Color.RED);
                chessBoardSquares[i][j].setFont(new Font("SansSerif", Font.PLAIN, 24));
                if ((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0)) {
                    chessBoardSquares[i][j].setBackground(Color.WHITE);
                } else {
                    chessBoardSquares[i][j].setBackground(Color.BLACK);
                }
            }
        }

        if (isCheckersMode) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 != 0) {
                        chessBoardSquares[j][i].setText("⬤");
                        chessBoardSquares[j][i].setForeground(Color.WHITE);
                        chessBoardSquares[j][i].setHorizontalAlignment(SwingConstants.CENTER);
                        chessBoardSquares[j][i].setFont(new Font("SansSerif", Font.BOLD, 36));
                    }
                }
            }

            for (int i = 5; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 != 0) {
                        chessBoardSquares[j][i].setText("⬤");
                        chessBoardSquares[j][i].setForeground(Color.RED);
                        chessBoardSquares[j][i].setHorizontalAlignment(SwingConstants.CENTER);
                        chessBoardSquares[j][i].setFont(new Font("SansSerif", Font.BOLD, 36));
                    }
                }
            }
            isCheckersMode = false;
        } else if (isChessMode) {
            for (int i = 0; i < STARTING_ROW.length; i++) {
                PieceImages.loadImages();
                Image[][] chessPieceImages = PieceImages.getChessPieceImages();
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
            isChessMode = false;
        } else if (isBattleshipMode) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessBoardSquares[j][i].setBackground(Color.CYAN);
                }
            }
            PieceImages.loadImages();
            Image[][] shipParts = PieceImages.getShipParts();
            chessBoardSquares[0][0].setIcon(new ImageIcon(shipParts[0][0]));
            chessBoardSquares[1][0].setIcon(new ImageIcon(shipParts[0][1]));
            chessBoardSquares[2][0].setIcon(new ImageIcon(shipParts[0][2]));
            chessBoardSquares[3][0].setIcon(new ImageIcon(shipParts[0][3]));
            chessBoardSquares[4][0].setIcon(new ImageIcon(shipParts[0][4]));
            chessBoardSquares[5][0].setIcon(new ImageIcon(shipParts[0][5]));
            chessBoardSquares[0][1].setIcon(new ImageIcon(shipParts[1][0]));
            chessBoardSquares[1][1].setIcon(new ImageIcon(shipParts[1][1]));
            chessBoardSquares[2][1].setIcon(new ImageIcon(shipParts[1][2]));
            chessBoardSquares[3][1].setIcon(new ImageIcon(shipParts[1][3]));
            chessBoardSquares[0][2].setIcon(new ImageIcon(shipParts[2][0]));
            chessBoardSquares[1][2].setIcon(new ImageIcon(shipParts[2][1]));
            chessBoardSquares[2][2].setIcon(new ImageIcon(shipParts[2][2]));
            chessBoardSquares[0][3].setIcon(new ImageIcon(shipParts[3][0]));
            chessBoardSquares[1][3].setIcon(new ImageIcon(shipParts[3][1]));
            isBattleshipMode = false;
        } else if (isTicMode) {
            chessBoardSquares[0][0].setText("O");
            chessBoardSquares[0][1].setText("X");
            chessBoardSquares[0][2].setText("O");
            chessBoardSquares[1][0].setText("X");
            chessBoardSquares[1][1].setText("O");
            chessBoardSquares[1][2].setText("X");
            chessBoardSquares[2][0].setText("X");
            chessBoardSquares[2][2].setText("O");
            isTicMode = false;
        }
        selectedSquare = null;
        messageLabel.setText("New game started!");
    }

    private void handleSquareClick(int y, int x) {
        JButton clicked = chessBoardSquares[x][y];
        Icon icon = clicked.getIcon();

        if (selectedSquare == null) {
            if (icon != null || (clicked.getText() != null && !clicked.getText().isEmpty())) {
                selectedSquare = new Point(x, y);
                messageLabel.setText("Piece selected at " + (char) ('A' + x) + (8 - y));
            }
        } else {
            JButton fromButton = chessBoardSquares[selectedSquare.x][selectedSquare.y];
            Icon fromIcon = fromButton.getIcon();
            String fromText = fromButton.getText();
            Color fromColor = fromButton.getForeground();
            Font fromFont = fromButton.getFont();

            clicked.setIcon(fromIcon);
            clicked.setText(fromText);
            clicked.setForeground(fromColor);
            clicked.setFont(fromFont);

            fromButton.setIcon(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
            fromButton.setText("");

            selectedSquare = null;
            messageLabel.setText("Piece moved.");
        }
    }
}