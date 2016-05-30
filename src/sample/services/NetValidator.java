package sample.services;

/**
 * Validator sprawdzający poprawność danych wprowadzonych przy tworzeniu gry
 */
public class NetValidator {

    /**
     * Metoda sprawdzająca poprawność formatu adresu ip.
     * Akceptowany jest każdy adres który ma poprawny format (X.X.X.X) oraz wartości każdego pola w przedziale [0,255].
     * Odrzucane są adresy 0.0.0.0 oraz 255.255.255.255.
     * @param ip - adres, którego format chcemy sprawdzić
     * @return true, jeśli adres ma poprawny format; w przeciwnym wypadku false
     */
    public boolean checkIPAddress(String ip){
        if(ip==null)
            return false;
        String[] checkers = ip.split("\\.");
        int zeros = 0;
        int maxes = 0;
        if(checkers.length != 4){
            return false;
        }
        for(int i=0; i<checkers.length; i++){
            for (int j=0; j<checkers[i].length(); j++){
                if(!Character.isDigit(checkers[i].charAt(j)))
                    return false;
            }
            int val = Integer.valueOf(checkers[i]);
            if(val>255 || val<0)
                return false;
            if(val==0)
                zeros++;
            if(val==255)
                maxes++;
        }
        return !(zeros == 4 || maxes == 4);

    }

    /**
     * Metoda sprawdzająca, czy port ma poprawny format (czy jest liczbą naturalną).
     * Akceptowany jest port który nie jest nullem i którego wartość mieści się w przedziale (49152,65535).
     * @param port - String reprezentjący nr portu który chcemy sprawdzić
     * @return true, jeśli format portu jest poprawny; w przeciwnym wypadku false
     */
    public boolean checkPort(String port){
        if(port==null)
            return false;
        if(port.length()==0)
            return false;
        for(int i=0; i<port.length(); i++){
            if(!Character.isDigit(port.charAt(i))){
                return false;
            }
        }
        return Integer.valueOf(port) > 49152 && Integer.valueOf(port) < 65535;
    }

    /**
     * Metoda sprawdzająca poprawność wprowadzonego nicku.
     * Akceptowany jest każny nick który nie jest nullem ani pustym stringiem.
     * @param nick - String reprezentujący nick który chcemy sprawdzić
     * @return true jeśli nick jest poprawny, w przeciwnym wypadku false
     */
    public boolean checkNick(String nick){
        if(nick==null)
            return false;
        return nick.length() != 0;
    }

    public boolean ifTimeChoosen(String time){
        return time != null;
    }
}
