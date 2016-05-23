/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import test2.controller.GameController;
import test2.model.FigureType;
import test2.model.GameState;

/**
 *
 * @author Andi
 */
public class NewEmptyJUnitTest {
    private GameState gameState;
    
    @Test
    public void NewEmptyJUnitTest() {
        assertEquals(2, gameState.countFigures(FigureType.WHITE));
        assertEquals(2, gameState.countFigures(FigureType.BLACK));
        
        GameController.getInstance().handleButtonClick(3, 2);
        assertEquals(1, gameState.countFigures(FigureType.WHITE));
        assertEquals(4, gameState.countFigures(FigureType.BLACK));
        
        GameController.getInstance().handleButtonClick(2,2);
        assertEquals(3, gameState.countFigures(FigureType.WHITE));
        assertEquals(3, gameState.countFigures(FigureType.BLACK));

        GameController.getInstance().handleButtonClick(1,2);
        assertEquals(2, gameState.countFigures(FigureType.WHITE));
        assertEquals(5, gameState.countFigures(FigureType.BLACK));
        
        GameController.getInstance().handleButtonClick(1,1);
        assertEquals(4, gameState.countFigures(FigureType.WHITE));
        assertEquals(4, gameState.countFigures(FigureType.BLACK));
    }

    @Test
    public void testTurnSwitch() {
        assertEquals(0, GameController.getInstance().getGameState().getNextPlayerId());

        GameController.getInstance().handleButtonClick(2,2);
        assertEquals(0, GameController.getInstance().getGameState().getNextPlayerId());
        
        GameController.getInstance().handleButtonClick(3,2);
        assertEquals(1, GameController.getInstance().getGameState().getNextPlayerId());
        
        GameController.getInstance().handleButtonClick(2,2);
        assertEquals(0, GameController.getInstance().getGameState().getNextPlayerId());

        GameController.getInstance().handleButtonClick(1,2);
        assertEquals(1, GameController.getInstance().getGameState().getNextPlayerId());

        GameController.getInstance().handleButtonClick(1,1);
        assertEquals(0, GameController.getInstance().getGameState().getNextPlayerId());
    }

    @BeforeClass
    public static void setUpClass(){
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        GameController.getInstance().newGame();
        gameState = GameController.getInstance().getGameState();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
