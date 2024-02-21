package io.github.danielmkraus.jackenpoy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    @ParameterizedTest
    @CsvSource({
            "-b,b",
            "--b,b",
            "-bind,b",
            "--bind,b",
            "-p,p",
            "--p,p",
            "-port,p",
            "--port,p",
            "-c,c",
            "--c,c",
            "-context-path,c",
            "--context-path,c"
    })
    void arguments_individually(String argument, String option) throws ParseException {
        String value = UUID.randomUUID().toString();
        CommandLine commandLineArguments = Server.getCommandLineArguments(new String[]{argument, value});
        String result = commandLineArguments.getOptionValue(option);
        assertThat(result).isEqualTo(value);
    }

    @Test
    void all_arguments() throws ParseException {
        CommandLine commandLineArguments = Server.getCommandLineArguments(
                new String[]{"-b", "0", "-c", "/test", "-p", "80"});

        assertThat(commandLineArguments.getOptionValue("b")).isEqualTo("0");
        assertThat(commandLineArguments.getOptionValue("p")).isEqualTo("80");
        assertThat(commandLineArguments.getOptionValue("c")).isEqualTo("/test");
    }
}