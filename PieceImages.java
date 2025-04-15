import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PieceImages {

    public static Image[][] chessPieceImages = new Image[2][6];
    public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int BLACK = 0, WHITE = 1;

    public static void loadImages() {
        try {
            BufferedImage bi = ImageIO.read(new File("images/chess.png"));
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = bi.getSubimage(j * 64, i * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading chess piece images: " + e.getMessage());
            System.exit(1);
        }
    }

    public static Image[][] getChessPieceImages() {
        return chessPieceImages;
    }
}