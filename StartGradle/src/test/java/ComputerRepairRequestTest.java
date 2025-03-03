import main.java.model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerRepairRequestTest{
        @Test
        @DisplayName("First Test")
        public void testExample(){
        ComputerRepairRequest crr = new ComputerRepairRequest();
        assertEquals("",crr.getOwnerName());
        assertEquals("",crr.getOwnerAddress());
        }
        @Test
        @DisplayName("Test Exemplu")
        public void testExample2() {
            assertEquals (2, 2, "Numerele are trebui sa fie egale!");
        }
}