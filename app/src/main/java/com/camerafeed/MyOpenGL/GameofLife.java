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

        board[1] = STATUS.ALIVE.value;
        board[width+2] = STATUS.ALIVE.value;
        board[2*width] = STATUS.ALIVE.value;
        board[2*width+1] = STATUS.ALIVE.value;
        board[2*width+2] = STATUS.ALIVE.value;

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
                if (numNeighbours>0) {
                    Log.i("GoL", "X: " + x + ", Y: " + y + " | numNeighbours: " + numNeighbours);
                }
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
