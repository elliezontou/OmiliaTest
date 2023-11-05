import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class PhoneNumberInterpreterTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("003069 123 45".getBytes());

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setIn(inContent);
    }

    @Test
    public void testGenerateInterpretations() {
        Set<String> interpretations = new HashSet<>();
        PhoneNumberInterpreter.generateInterpretations("003069", "", interpretations);
        Assert.assertEquals(1, interpretations.size());
        Assert.assertTrue(interpretations.contains("003069"));

        interpretations.clear();
        PhoneNumberInterpreter.generateInterpretations("234", "", interpretations);
        Assert.assertEquals(2, interpretations.size());
        Assert.assertTrue(interpretations.contains("234"));

        interpretations.clear();
        PhoneNumberInterpreter.generateInterpretations("6947085002", "", interpretations);
        Assert.assertEquals(4, interpretations.size());
        Assert.assertTrue(interpretations.contains("6947085002"));
        Assert.assertTrue(interpretations.contains("69470805002"));
        Assert.assertTrue(interpretations.contains("69407085002"));
        Assert.assertTrue(interpretations.contains("6947085002"));
    }

    @Test
    public void testValidatePhoneNumber() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PhoneNumberInterpreter.validatePhoneNumber("225");
        Assert.assertEquals("[phone number: INVALID]\n", outContent.toString());

        outContent.reset();
        PhoneNumberInterpreter.validatePhoneNumber("00302 1234567890");
        Assert.assertEquals("[phone number: INVALID]\n", outContent.toString());

        outContent.reset();
        PhoneNumberInterpreter.validatePhoneNumber("1234567890");
        Assert.assertEquals("[phone number: INVALID]\n", outContent.toString());

        outContent.reset();
        PhoneNumberInterpreter.validatePhoneNumber("6947085002");
        Assert.assertEquals("[phone number: VALID]\n", outContent.toString());

        outContent.reset();
        PhoneNumberInterpreter.validatePhoneNumber("0030210773842");
        Assert.assertEquals("[phone number: INVALID]\n", outContent.toString());

        outContent.reset();
        PhoneNumberInterpreter.validatePhoneNumber("00302107738422");
        Assert.assertEquals("[phone number: VALID]\n", outContent.toString());
    }

    @Test
    public void testMain() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("003069 123 45".getBytes());
        System.setIn(inContent);

        PhoneNumberInterpreter.main(new String[] {});
        Assert.assertEquals(
                "Enter a sequence of numbers separated by spaces: The input's format is not proper!\n",
                outContent.toString());
    }
}
