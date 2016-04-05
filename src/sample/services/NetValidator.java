package sample.services;

/**
 * Validator sprawdzający poprawność danych wprowadzonych przy tworzeniu gry
 */
public class NetValidator {

    /**
     * Metoda sprawdzająca poprawność formatu adresu ip.
     * @param ip - adres, którego format chcemy sprawdzić
     * @return true, jeśli adres ma poprawny format; w przeciwnym wypadku false
     */
    public boolean checkIPAddress(String ip){
        String[] checkers = ip.split("\\.");
        if(checkers.length != 4){
            return false;
        }
        for(int i=0; i<checkers.length; i++){
            for (int j=0; j<checkers[i].length(); j++){
                if(!Character.isDigit(checkers[i].charAt(j)))
                    return false;
            }
        }
        return true;
    }

    /**
     * Metoda sprawdzająca, czy port ma poprawny format (czy jest liczbą naturalną)
     * @param port - String reprezentjący nr portu który chcemy sprawdzić
     * @return true, jeśli format portu jest poprawny; w przeciwnym wypadku false
     */
    public boolean checkPort(String port){
        if(port.length()==0)
            return false;
        for(int i=0; i<port.length(); i++){
            if(!Character.isDigit(port.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
