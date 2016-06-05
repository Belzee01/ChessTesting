package sample.services;

public class BoardValidator {
    public static boolean isBoardStringValid(String boardString){
        if(boardString==null){
            return false;
        }

        if(boardString.length()!=64){
            return false;
        }

        String acceptedCharts="rbqknp PRBQKN";
        for(int i=0;i<boardString.length();i++){
            if(acceptedCharts.indexOf(boardString.charAt(i))==-1){
                return false;
            }
        }
        return true;
    }
}
