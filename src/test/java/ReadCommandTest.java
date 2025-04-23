import org.example.Matrix;
import org.example.commands.ReadCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReadCommandTest {
    @Mock
    Matrix accessMatrix;

    @Mock
    Matrix objectMatrix;

    @InjectMocks
    ReadCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new ReadCommand(accessMatrix, objectMatrix);

    }

    @Test
    void testReadCommand_withSufficientPermissions() {
        Mockito.when(accessMatrix.readSecrecyLevels()).thenReturn(Map.of("subject1", "HIGH"));
        Mockito.when(objectMatrix.readSecrecyLevels()).thenReturn(Map.of("object1", "LOW"));

        command.subject = "subject1";
        command.object = "object1";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);
        command.run();

        assertTrue(out.toString().contains("Разрешено чтение файла"));
    }

    @Test
    void testReadCommand_withInsufficientPermissions() {
        Mockito.when(accessMatrix.readSecrecyLevels()).thenReturn(Map.of("subject1", "LOW"));
        Mockito.when(objectMatrix.readSecrecyLevels()).thenReturn(Map.of("object1", "HIGH"));

        command.subject = "subject1";
        command.object = "object1";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);
        command.run();

        assertTrue(out.toString().contains("Недостаточно прав для чтения файла"));
    }
}