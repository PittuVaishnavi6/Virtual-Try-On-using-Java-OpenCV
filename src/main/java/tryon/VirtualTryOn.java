package tryon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class VirtualTryOn {
    static {
        System.load("C:\\Users\\Vaishnavi\\Downloads\\VirtualTryOnJava\\lib\\opencv_java4110.dll");
    }

    static int currentGlassesIndex = 0;

    public static void main(String[] args) {
        CascadeClassifier faceCascade = new CascadeClassifier("src/main/resources/models/haarcascade_frontalface_alt.xml");

        // Load all glasses
        List<Mat> glassesList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Mat g = Imgcodecs.imread("src/main/resources/glasses/glasses_0" + i + ".png", Imgcodecs.IMREAD_UNCHANGED);
            glassesList.add(g);
        }

        VideoCapture cap = new VideoCapture(0);
        if (!cap.isOpened()) {
            System.out.println("Camera not detected");
            return;
        }

        JFrame jframe = new JFrame("Virtual Glasses Try-On");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel imageLabel = new JLabel();
        jframe.setContentPane(imageLabel);
        jframe.setSize(800, 600);
        jframe.setVisible(true);

        // Key listener to switch glasses
        jframe.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= '1' && e.getKeyChar() <= '4') {
                    currentGlassesIndex = e.getKeyChar() - '1'; // 0-based index
                    System.out.println("Switched to glasses_" + (currentGlassesIndex + 1));
                }
            }
        });

        Mat frame = new Mat();
        while (true) {
            cap.read(frame);
            if (frame.empty()) break;

            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(frame, faces);

            for (Rect face : faces.toArray()) {
                Imgproc.rectangle(frame, face, new Scalar(0, 255, 0), 2);

                // Get selected glasses
                Mat glasses = glassesList.get(currentGlassesIndex);

                // Resize glasses to fit face width
                Mat resizedGlasses = new Mat();
                Imgproc.resize(glasses, resizedGlasses, new Size(face.width, face.height / 3));

                // Overlay the glasses
                overlayImage(frame, resizedGlasses, new Point(face.x, face.y + face.height / 4));
            }

            ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
            imageLabel.setIcon(image);
            imageLabel.repaint();

            if (!jframe.isVisible()) break;
        }

        cap.release();
    }

    // Overlay transparent PNG
    public static void overlayImage(Mat background, Mat foreground, Point location) {
        for (int y = 0; y < foreground.rows(); ++y) {
            for (int x = 0; x < foreground.cols(); ++x) {
                double[] fgPixel = foreground.get(y, x);
                if (fgPixel.length == 4) {
                    double alpha = fgPixel[3] / 255.0;
                    int bx = (int) location.x + x;
                    int by = (int) location.y + y;

                    if (bx >= 0 && bx < background.cols() && by >= 0 && by < background.rows()) {
                        double[] bgPixel = background.get(by, bx);
                        for (int c = 0; c < 3; ++c) {
                            bgPixel[c] = bgPixel[c] * (1.0 - alpha) + fgPixel[c] * alpha;
                        }
                        background.put(by, bx, bgPixel);
                    }
                }
            }
        }
    }

    public static BufferedImage Mat2BufferedImage(Mat matrix) {
    Mat converted = new Mat();
    Imgproc.cvtColor(matrix, converted, Imgproc.COLOR_BGR2RGB);  // Convert BGR to RGB

    int width = converted.width();
    int height = converted.height();
    int channels = converted.channels();
    byte[] sourcePixels = new byte[width * height * channels];
    converted.get(0, 0, sourcePixels);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    image.getRaster().setDataElements(0, 0, width, height, sourcePixels);
    return image;
  }

}
