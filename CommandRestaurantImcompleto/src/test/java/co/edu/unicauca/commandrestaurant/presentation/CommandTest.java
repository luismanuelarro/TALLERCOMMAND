package co.edu.unicauca.commandrestaurant.presentation;

import co.edu.unicauca.commandrestaurant.domain.Command;
import co.edu.unicauca.commandrestaurant.domain.CreateCommand;
import co.edu.unicauca.commandrestaurant.domain.Deletecommand;
import co.edu.unicauca.commandrestaurant.domain.FindByIdCommand;
import co.edu.unicauca.commandrestaurant.domain.Food;
import co.edu.unicauca.commandrestaurant.domain.FoodTypeEnum;
import co.edu.unicauca.commandrestaurant.domain.Invoker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Michel Andrea Vallejo, Mannuel Fernando Granoble 
 */
public class CommandTest {

    @Test
    public void testCreateCommand() {
        System.out.println("CreateCommand");
        //Crea el invocador
        Invoker invoker = new Invoker();
        //Crea la comida
        Food food = new Food(10, "Lentejas", FoodTypeEnum.PRINCIPIO);
        //Crea el comando crear
        Command cmd = new CreateCommand(food);
        invoker.setCommand(cmd);
        invoker.execute();

        //Consulta la comida
        cmd = new FindByIdCommand();
        invoker.setCommand(cmd);
        //Pasa parámetros al comando
        FindByIdCommand findByIdCommand = (FindByIdCommand) invoker.getCommand();
        findByIdCommand.setFoodId(10);
        //Ejecuta el comando
        invoker.execute();
        Food foodFound = findByIdCommand.getFood();

        assertEquals("Lentejas", foodFound.getName());

        //Dehace el comando crear
        invoker.undo();

        cmd = new FindByIdCommand();
        invoker.setCommand(cmd);
        //Pasa parámetros al comando
        findByIdCommand = (FindByIdCommand) invoker.getCommand();
        findByIdCommand.setFoodId(10);
        //Ejecuta el comando
        invoker.execute();
        foodFound = findByIdCommand.getFood();

        assertEquals(null, foodFound);

        
        //Creando de nuevo la comida 
        
        cmd = new CreateCommand(food);
        invoker.setCommand(cmd);
        invoker.execute();
        
        
        //Eliminar la comida
        cmd = new Deletecommand(food);
        invoker.setCommand(cmd);
        invoker.execute();
        
         //Consulta la comida eliminada 
        cmd = new FindByIdCommand();
        invoker.setCommand(cmd);
        //Pasa parámetros al comando
        findByIdCommand = (FindByIdCommand) invoker.getCommand();
        findByIdCommand.setFoodId(10);
        //Ejecuta el comando
        invoker.execute();
        foodFound = findByIdCommand.getFood();

        assertEquals(null, foodFound);
        
        
        //Dehace el comando eliminar
        invoker.undo();

        cmd = new FindByIdCommand();
        invoker.setCommand(cmd);
        //Pasa parámetros al comando
        findByIdCommand = (FindByIdCommand) invoker.getCommand();
        findByIdCommand.setFoodId(10);
        //Ejecuta el comando
        invoker.execute();
        foodFound = findByIdCommand.getFood();

        assertEquals("Lentejas", foodFound.getName());
    }

}
