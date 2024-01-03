import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.nio.file.*;

public class HappyBirthday {

    private static final ArrayList<String> wishes = new ArrayList<>();
    private static final int pokemonSpeed = 500;
    private static int currentPokemon = 0;
    public static void main(String[] args) {
        wishes.add("Удачи, крепкого здоровья, карьерного роста!");
        wishes.add("Высоких достижений в карьере, хорошего настроения на каждый день!");
        wishes.add("Душевного равновесия, адекватных людей тиммейтов, верных друзей, чтобы желание развиваться никогда не угасало и естественно, чтобы каждый день приносил побольше новых и теплых эмоций!");
        wishes.add("Огромных успехов во всех начинаниях");
        wishes.add("Побольше счастливых моментов. Чтобы все трудности решались, а близкие люди окружали добротой и любовью");
        wishes.add("Спокойствия и отличного настроения! Чтобы посторонние заглядывали в дверь только с подарками)");
        wishes.add("Здоровья всем членам и питомцам твоей семьи!");
        wishes.add("Желаю бесконечность счастливых и интересных дней!");
        wishes.add("Желаю веселых каток, побольше пряток, нежности, любви и игры проводить");
        wishes.add("Душевного равновесия, гармонии! Смотри на мир с позитивом!");
        wishes.add("И чтобы всегда были...");

        showPokemon(currentPokemon);
    }

    private static void showPokemon(int index) {
        JFrame frame = generateTransparentJFrame(getPathToPokemon(index), true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setRandomFrameLocation(frame);
        frame.setVisible(true);

        Timer timer = new Timer(pokemonSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRandomFrameLocation(frame);
            }
        });
        timer.start();

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                currentPokemon++;
                if (currentPokemon == 11) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    showBabki();
                }
                else {
                    JOptionPane.showMessageDialog(frame, wishes.get(index));
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    showPokemon(currentPokemon);
                }
            }
        });

    }
    private static void showBabki() {
        JFrame frame = generateTransparentJFrame(getImgPath() + "babki.jpg", false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation( (getScreenResolution().width - frame.getSize().width) / 2, (getScreenResolution().height - frame.getSize().height) / 2);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame,  wishes.get(wishes.size() - 1));
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private static JFrame generateTransparentJFrame(String imgPath, boolean needScaleImg) {
        ImageIcon imageIcon = new ImageIcon(imgPath);
        if (needScaleImg) {
            imageIcon = scaleImageIcon(imageIcon, 500, 500);
        }
        JLabel label = new JLabel(imageIcon);
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.getContentPane().add(label);
        frame.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        return frame;
    }

    public static ImageIcon scaleImageIcon(ImageIcon originalIcon, int width, int height) {
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private static void setRandomFrameLocation(JFrame frame) {
        Random random = new Random();
        int x = random.nextInt(getScreenResolution().width - frame.getSize().width);
        int y = random.nextInt(getScreenResolution().height - frame.getSize().height);
        frame.setLocation(x, y);
    }

    private static Dimension getScreenResolution() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }

    private static String getPathToPokemon(int index) {
        return getImgPath() + index + ".png";
    }

    private static String getImgPath(){
        Path currentPath = Paths.get("").toAbsolutePath();
        return currentPath + "\\src\\img\\";
    }
}