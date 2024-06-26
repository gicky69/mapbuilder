package entity;

import controller.Controller;
import core.Position;

public class Player extends GameObject {

    private Controller controller;

    public int lastKeyPressed = 0;
    public int assetNum = 0;

    public Player(Position pos, Controller controller)  {
        super();
        this.position = pos;
        this.controller = controller;
    }

    @Override
    public void update() {
        int deltaX = 0;
        int deltaY = 0;

        if (controller.isClickedUp()){
            deltaY -= 3;
            System.out.println("up");
        }
        if (controller.isClickedDown()){
            deltaY += 3;
        }
        if (controller.isClickedLeft()){
            deltaX -= 3;
        }
        if (controller.isClickedRight()){
            deltaX += 3;
        }

        // Key Inputs
        if (controller.clickedGrass()) {
            lastKeyPressed = 48;
        }
        if (controller.clickedWall()) {
            lastKeyPressed = 49;
        }
        if (controller.clickedDoor()) {
            lastKeyPressed = 50;
        }
        if (controller.clickedFloor()) {
            lastKeyPressed = 51;
        }
        for (int i = 48; i <= 105; i++) {
            if (controller.clickedTiles(i)) {
                lastKeyPressed = i;
                if (lastKeyPressed >= 96 && lastKeyPressed <= 105) {
                    assetNum = lastKeyPressed - 96;
                }
            }
        }

        System.out.println("Tile clicked: " + lastKeyPressed);
        System.out.println("Asset number: " + assetNum);

//        System.out.println("Last key pressed: " + lastKeyPressed);
        position = new Position(position.getX() + deltaX, position.getY() + deltaY);
    }
}
