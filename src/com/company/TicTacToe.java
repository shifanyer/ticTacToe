package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TicTacToe extends JComponent {

    private static final byte emptyField = -1;
    private static final byte field_X = 10;
    private static final byte field_0 = 0;
    private static int fieldSize;
    private static boolean isXturn;
    private final int[][] field;

    public TicTacToe(int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[size][size];
        fieldSize = size;
        initGame();
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {

            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            int i = (int) ((float) x / getWidth() * fieldSize);
            int j = (int) ((float) y / getHeight() * fieldSize);

            if (field[i][j] == emptyField) {
                field[i][j] = isXturn ? field_X : field_0;
                isXturn = !isXturn;
                repaint();
                byte stateNum = checkState();
                if (stateNum != -1) {
                    if (stateNum == 0) {
                        JOptionPane.showMessageDialog(this, "Победа ноликов", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (stateNum == 10) {
                        JOptionPane.showMessageDialog(this, "Победа крестиков", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (stateNum == -10) {
                        JOptionPane.showMessageDialog(this, "Ничья", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                    }
                    initGame();
                    repaint();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        drawGrid(graphics);
        super.paintComponent(graphics);

    }

    void drawGrid(Graphics graphics) {
        int w = getWidth();
        int h = getHeight();

        int dw = w / fieldSize;
        int dh = h / fieldSize;

        graphics.setColor(Color.blue);

        for (int i = 0; i < fieldSize; i++) {
            graphics.drawLine(0, dh * i, w, dh * i);
            graphics.drawLine(dw * i, 0, dw * i, h);
        }
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] == field_0) {
                    draw0(i, j, graphics);
                }
                if (field[i][j] == field_X) {
                    drawX(i, j, graphics);
                }
            }
        }
    }

    void initGame() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = emptyField;
            }
        }
        isXturn = true;
    }

    void draw0(int i, int j, Graphics graphics) {
        graphics.setColor(Color.black);
        int dw = getWidth() / fieldSize;
        int dh = getHeight() / fieldSize;
        int x = i * dw;
        int y = j * dh;

        graphics.drawOval(x, y, dw, dh);
    }

    void drawX(int i, int j, Graphics graphics) {
        graphics.setColor(Color.magenta);

        int dw = getWidth() / fieldSize;
        int dh = getHeight() / fieldSize;

        int x = dw * i;
        int y = dh * j;

        graphics.drawLine(x, y, x + dw, y + dh);
        graphics.drawLine(x, y + dh, x + dw, y);
    }

    byte checkState() {
        boolean isEmptyFields = false;
        for (int i = 0; i < fieldSize; i++) {
            int s = 0;
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] == -1) {
                    isEmptyFields = true;
                }
                s += field[i][j];
            }
            if (s == 0) {
                return 0;
            }
            if (s == field_X * fieldSize) {
                return 10;
            }
        }

        for (int j = 0; j < fieldSize; j++) {
            int s = 0;
            for (int i = 0; i < fieldSize; i++) {
                s += field[i][j];
            }
            if (s == 0) {
                return 0;
            }
            if (s == field_X * fieldSize) {
                return 10;
            }
        }

        int s = 0;
        for (int i = 0; i < fieldSize; i++) {
            s += field[i][i];
        }
        if (s == 0) {
            return 0;
        }
        if (s == field_X * fieldSize) {
            return 10;
        }


        s = 0;
        for (int i = 0; i < fieldSize; i++) {
            s += field[i][fieldSize - i - 1];
        }
        if (s == 0) {
            return 0;
        }
        if (s == field_X * fieldSize) {
            return 10;
        }

        if (!isEmptyFields) {
            return -10;
        }
        return -1;
    }

}
