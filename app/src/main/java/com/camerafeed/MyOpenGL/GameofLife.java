package com.camerafeed.MyOpenGL;

import android.util.Log;

/**
 * Created by NoobsDeSroobs on 12-May-16.
 */
public class GameofLife {
    short[] board;
    int boardWidth;
    int boardHeight;

    enum STATUS{
        DEAD((short)0),
        ALIVE((short)1);
        private final short value;
        private short dead;

        STATUS(short s){
            value = s;
        }

        public short getDead() {
            return DEAD.value;
        }

        public short getAlive() {
            return ALIVE.value;
        }
    }

    public GameofLife(int width, int height){
        board = new short[width*height];
        boardWidth = width;
        boardHeight = height;

        for (int i = 0; i < board.length; i++) {
            board[i] = STATUS.DEAD.value;
        }

        short[] GosperGliderGun = {
                1, 7,
                1, 8,
                2, 7,
                2, 8,
                11, 4,
                11, 5,
                11, 7,
                11, 9,
                11, 10,
                13, 4,
                13, 10,
                15, 5,
                15, 6,
                15, 8,
                15, 9,
                16, 7,
                23, 4,
                23, 5,
                23, 6,
                24, 3,
                24, 7,
                26, 2,
                26, 8,
                27, 2,
                27, 3,
                27, 7,
                27, 8,
                30, 5,
                31, 4,
                31, 6,
                32, 4,
                32, 6,
                33, 4,
                34, 4,
                35, 4,
                35, 7,
                36, 5,
                36, 6
        };

        for (int i = 0; i < GosperGliderGun.length; i+=2) {
            int x = GosperGliderGun[i];
            int y = GosperGliderGun[i+1];

            board[y*width+x] = STATUS.ALIVE.value;
        }

        for (int i = 0; i < GosperGliderGun.length; i+=2) {
            int x = GosperGliderGun[i]+5;
            int y = boardHeight-GosperGliderGun[i+1];

            board[y*width+x] = STATUS.ALIVE.value;
        }


    }

    //010
    //001
    //111
    int countNeighbours(int xCoord, int yCoord){
        int numAlive = 0;
        for (int y = yCoord-1; y <= yCoord+1; y++) {
            for (int x = xCoord-1; x <= xCoord+1; x++) {
                if(y < 0 || y >= boardHeight || x < 0 || x >= boardWidth){
                    continue;
                }

                if(x == xCoord && y == yCoord){
                    continue;
                }

                int coordinate = y * boardWidth + x;

                if(board[coordinate] == STATUS.ALIVE.value){
                    numAlive++;
                }
            }
        }
        return numAlive;
    }

    public void tick(){
        short[] newBoard = board.clone();

        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                int coordinate = y*boardWidth+x;
                int numNeighbours = countNeighbours(x, y);
                switch(numNeighbours){
                    case 0:
                    case 1:
                        //dead
                        newBoard[coordinate] = STATUS.DEAD.value;
                        break;
                    case 2:
                    case 3:
                        if(board[coordinate] == STATUS.ALIVE.value){
                            //if alive, stay alive. Do nothing.
                            newBoard[coordinate] = board[coordinate];
                        }else if(numNeighbours == 3){
                            //If dead, resurrect if numNeighbours == 3;
                            newBoard[coordinate] = STATUS.ALIVE.value;
                        }
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        //dead again
                        newBoard[coordinate] = STATUS.DEAD.value;
                        break;
                    default:
                        //Something went wrong.
                }
            }
        }
        board = newBoard;
    }

    public short[] getBoard(){
        return board;
    }
}
