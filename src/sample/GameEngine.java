package sample;

/**
 * To bedzie glowny singleton odwolujacy sie do wszystkiego
 * Bedzie wywolywal i obslugiwal wszystkie pozostale moduly programu
 */
public class GameEngine {
    private static GameEngine gameEngine = null;

    /**
     * Klasyczny singleton
     * @return instancja obiektu
     */
    public static GameEngine getInstance(){
        if(gameEngine == null)
            gameEngine = new GameEngine();

        return gameEngine;
    }
}
