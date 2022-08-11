package server.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Commands {
    STOP("/stop"),
    AUTHORIZATION("/reg");

    private final String name;
}
