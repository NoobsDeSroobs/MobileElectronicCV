package com.example.imerso.camerafeed.MyOpenGL;

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

        STATUS(short s){
            value = s;
        }
    }

    public GameofLife(int width, int height){
        board = new short[width*height];
        boardWidth = width;
        boardHeight = height;
    }

    int countNeighbours(int xCoord, int yCoord){
        int numAlive = 0;
        for (int y = yCoord-1; y < yCoord+1; y++) {
            for (int x = xCoord-1; x < xCoord+1; x++) {
                if(y < 0 || y > boardHeight || x < 0 || x > boardWidth){
                    continue;
                }

                if(x == xCoord && y == yCoord){
                    continue;
                }

                if(board[y * boardWidth + x] == STATUS.ALIVE.value){
                    numAlive++;
                }
            }
        }
        return numAlive;
    }

    public void tick(){
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                int numNeighbours = countNeighbours(x, y);
                switch(numNeighbours){
                    case 0:
                    case 1:
                        //dead
                        board[y*boardWidth+x] = STATUS.DEAD.value;
                        break;
                    case 2:
                    case 3:
                        if(board[y*boardWidth+x] == STATUS.ALIVE.value){
                            //if alive, stay alive. Do nothing.
                        }else if(numNeighbours == 3){
                            //If dead, resurrect if numNeighbours == 3;
                            board[y*boardWidth+x] = STATUS.ALIVE.value;
                        }
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        //dead again
                        board[y*boardWidth+x] = STATUS.DEAD.value;
                        break;
                    default:
                        //Something went wrong.
                }
            }
        }
    }

    public short[] getBoard(){
        return board;
    }
}
