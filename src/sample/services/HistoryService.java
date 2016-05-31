package sample.services;

import sample.models.Board;

import java.util.*;

public class HistoryService {
    ArrayList <Board> boards = new ArrayList();
    int index = -1;

    public void addBoard(Board board){
        index++;
        boards.add(board);
    }

    public Board current(){
        return boards.get(index);
    }

    public Board next(){
        if (index == boards.size()-1)
            return null;
        return boards.get(++index);
    }

    public Board prev(){
        if (index == 0)
            return null;
        return boards.get(--index);
    }
    public int getSize(){
        return boards.size();
    }
}
