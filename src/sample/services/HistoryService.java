package sample.services;

import sample.models.Board;

import java.util.*;


public class HistoryService {
    ArrayList <Board> boards = new ArrayList();

    public void addBoard(Board board){
        boards.add(board);
    }

    public Board getBoard(int i){
        return boards.get(i);
    }

    public int getSize(){
        return boards.size();
    }
}
