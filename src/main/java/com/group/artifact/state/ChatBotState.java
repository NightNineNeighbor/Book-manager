package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.stateStarter.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBotState {
    private static Map<String, State> savedState = new HashMap<>();

    public static State currentState(String slackId, Command command) {
        if (savedState.containsKey(slackId)) {
            return savedState.get(slackId);
        }
        return command.initState();
    }

    public static void put(String slackId, State state) {
        savedState.put(slackId, state);
    }

}
