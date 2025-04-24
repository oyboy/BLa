import org.example.Matrix;
import org.example.commands.WriteCommand;
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

class WriteCommandTest {
    @Mock
    Matrix subjectMatrix;

    @Mock
    Matrix objectMatrix;

    @Mock
    Matrix acccessMatrix;

    @InjectMocks
    WriteCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new WriteCommand(subjectMatrix, objectMatrix, acccessMatrix);
    }

    @Test
    void testWriteCommand_withSufficientPermissions() {
        Mockito.when(subjectMatrix.readSecrecyLevels()).thenReturn(Map.of("subject1", "LOW"));
        Mockito.when(objectMatrix.readSecrecyLevels()).thenReturn(Map.of("object1", "HIGH"));

        command.subject = "subject1";
        command.object = "object1";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);

        command.run();

        assertTrue(out.toString().contains("Файл успешно записан"));
    }

    @Test
    void testWriteCommand_withInsufficientPermissions() {
        Mockito.when(subjectMatrix.readSecrecyLevels()).thenReturn(Map.of("subject1", "HIGH"));
        Mockito.when(objectMatrix.readSecrecyLevels()).thenReturn(Map.of("object1", "LOW"));

        command.subject = "subject1";
        command.object = "object1";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);

        command.run();

        assertTrue(out.toString().contains("Недостаточно прав для записи файла"));
    }

    @Test
    void testWriteCommand_whenLevelsNotFound() {
        Mockito.when(subjectMatrix.readSecrecyLevels()).thenReturn(Map.of());
        Mockito.when(objectMatrix.readSecrecyLevels()).thenReturn(Map.of());

        command.subject = "unknownSubject";
        command.object = "unknownObject";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        System.setOut(printStream);

        command.run();

        assertTrue(out.toString().contains("Уровни секретности не найдены для субъекта или объекта"));
    }
}