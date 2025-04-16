import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class PieceImages {

    public static Image[][] chessPieceImages = new Image[2][6];
    public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int BLACK = 0, WHITE = 1;

    public static Image[][] shipParts = new Image[4][6];
    public static final int Huge = 0, Big = 1, small= 2, submarine = 3;
    public static final int FRONT = 0, MIDDLE1 = 1, MIDDLE2 = 2, MIDDLE3 = 3, BACK = 4;

    public static void loadImages() {
            try {
                BufferedImage bi = ImageIO.read(new File("assets/chessPieces.png"));
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 6; j++) {
                        chessPieceImages[i][j] = bi.getSubimage(j * 64, i * 64, 64, 64);
                    }
                }

                BufferedImage HugeBoat = ImageIO.read(new File("assets/HugeBoat.png"));
                BufferedImage BigBoat = ImageIO.read(new File("assets/BigBoat.png"));
                BufferedImage SmallBoat = ImageIO.read(new File("assets/smallBoat.png"));
                BufferedImage submarine = ImageIO.read(new File("assets/submarine.png"));
                for (int i = 0; i < 6; i++) {
                    shipParts[0][i] = HugeBoat.getSubimage(i * 59, 0, 64, 88);
                }
                for (int i = 0; i < 4; i++) {
                    shipParts[1][i] = BigBoat.getSubimage(i * 62, 0, 62, 68);
                }
                for (int i = 0; i < 3; i++) {
                    shipParts[2][i] = SmallBoat.getSubimage(i * 62, 0, 64, 75);
                }
                for (int i = 0; i < 2; i++) {
                    shipParts[3][i] = submarine.getSubimage(i * 62, 0, 64, 68);
                }
                
            } catch (Exception e) {
                System.err.println("Failed to load battleship images: " + e.getMessage());
                System.exit(1);
        }
    }

    public static Image[][] getChessPieceImages() {
        return chessPieceImages;
    }

    public static Image[][] getShipParts() {
        return shipParts;
    }
}
