package com.group.artifact.state;

import com.group.artifact.stateStarter.Command;

import java.util.HashMap;
import java.util.Map;

public class ChatBotState {
    private static Map<String, State> savedState = new HashMap<>();

    public static State currentState(String slackId, Command command) {
        if (isInterrupt(command)) {
            return command.initState();
        }

        if (savedState.containsKey(slackId)) {
            return savedState.get(slackId);
        }
        return command.initState();
    }

    private static boolean isInterrupt(Command command) {
        return command == Command.EXIT_COMMAND_MODE || command == Command.USAGE;
    }

    public static void put(String slackId, State state) {
        savedState.put(slackId, state);
    }

    public static void remove(String slackId) {
        savedState.remove(slackId);
    }

}
