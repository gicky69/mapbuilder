package map;

import display.Display;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

public class Map {

    private Display display;
    public Tile[] tile;
    public int[][] tileMap;
    public List<Point> clickTiles;

    private String selectedMap;

    Color[] colors = {
            Color.BLACK, // 0
            Color.GREEN, // 1
            Color.BLACK, // 2
            Color.ORANGE, // 3
            Color.BLUE, // 4
            Color.RED, // 5
            Color.YELLOW, // 6
            Color.CYAN, // 7
            Color.MAGENTA, // 8
            Color.PINK, // 9
            new Color(0x8B4513), // Saddle Brown // 10
            new Color(0x6CF5AC), // Turquoise // 11
            new Color(255, 0, 0),     // Red // 12
            new Color(255, 165, 0),   // Orange // 13
            new Color(255, 255, 0),   // Yellow // 14
            new Color(0, 128, 0),     // Green // 15
            new Color(0, 0, 255),     // Blue // 16
            new Color(75, 0, 130),    // Indigo // 17
            new Color(238, 130, 238), // Violet // 18
            new Color(128, 0, 0),     // Maroon // 19
            new Color(128, 128, 0),   // Olive //20
            new Color(0, 128, 128),   // Teal // 21
            new Color(128, 0, 128),   // Purple // 22
            new Color(192, 192, 192), // Silver // 23
            new Color(255, 192, 203), // Pink // 24
            new Color(255, 20, 147),  // Deep Pink // 25
            new Color(255, 105, 180), // Hot Pink // 26
            new Color(255, 140, 0),   // Dark Orange // 27
            new Color(255, 215, 0),   // Gold //28
            new Color(173, 255, 47),  // Green Yellow // 29
            new Color(0, 255, 127),   // Spring Green // 30
            new Color(0, 206, 209),   // Dark Turquoise // 31
            new Color(0, 191, 255),   // Deep Sky Blue // 32
            new Color(70, 130, 180),  // Steel Blue // 33
            new Color(75, 0, 130),    // Indigo // 34
            new Color(106, 90, 205),  // Slate Blue // 35
    };

    public Map(Display display) {
        this.display = display;
        tile = new Tile[2];
        tileMap = new int[40][80];
        clickTiles = new ArrayList<>();

        Graphics g = display.getBufferStrategy().getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, 40, 40);
    }

    public void draw(Graphics g2) {
        int playerPosX = (int)display.player.getPosition().getX();
        int playerPosY = (int)display.player.getPosition().getY();
        Point point = new Point();

        Color currentColor = null;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 80; j++) {
                int worldX = j * 40 - playerPosX;
                int worldY = i * 40 - playerPosY;

                point.setLocation(worldX, worldY);
    //            clickTiles.add(point);

                if (tileMap[i][j] < colors.length) {
//                    System.out.println("Tile: " + tileMap[0][0]);
                    Color tileColor = colors[tileMap[i][j]];
                    if (!tileColor.equals(currentColor)) {
                        g2.setColor(tileColor);
                        currentColor = tileColor;
                    }
                    if (tileMap[i][j] == 1) {
                        g2.drawRect(worldX, worldY, 40, 40);
                    } else {
                        g2.fillRect(worldX, worldY, 40, 40);
                    }
                }
            }
        }
    }

    public File createMap() {
        int count = 0;
        File file;
        do {
            String filename = "mapbuilder/resources/maps/map" + count + ".txt";
            file = new File(filename);
            count++;
        } while (file.exists());

        selectedMap = file.getPath();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 80; j++) {
                    writer.write("1 ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadDirectly(selectedMap);

        Graphics g = display.getBufferStrategy().getDrawGraphics();
        draw(g);

        return file;
    }

    public void saveMap() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedMap, false))) {
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 80; j++) {
                    writer.write(tileMap[i][j] + " "); // Ensure there's a space after each number
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDirectly(String mapPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mapPath))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split(" "); // Trim leading and trailing spaces before splitting
                for (int j = 0; j < values.length; j++) {
                    tileMap[i][j] = Integer.parseInt(values[j]);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("mapbuilder"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedMap = selectedFile.getPath();
            try {
                List<String> lines = Files.readAllLines(Paths.get(selectedMap));
                for (int i = 0; i < lines.size(); i++) {
                    String[] values = lines.get(i).split(" ");
                    for (int j = 0; j < values.length; j++) {
                        tileMap[i][j] = Integer.parseInt(values[j]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}