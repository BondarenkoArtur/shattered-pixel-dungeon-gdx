package com.watabou.pd.desktop;

import com.badlogic.gdx.Input;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;
import java.util.HashMap;

public class IrcConnection extends PircBot {

    IrcInputProcessor inputProcessor;

    public IrcConnection(IrcInputProcessor inputProcessor) {

        this.inputProcessor = inputProcessor;

        // Enable debugging output.
        this.setVerbose(true);

        this.setName("uaBArtBot");
        // Connect to the IRC server.
        try {
            this.connect("irc.twitch.tv", 6667, "oauth:9ldmg9z1p0uesld6gpa0cqn3i2btkt");
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

        // Join the #uabart channel.
        this.joinChannel("#uabart");
    }

    @Override
    public void onMessage(String channel, String sender,
            String login, String hostname, String message) {
        GLog.i(sender + ": " + message);
        message = message.toLowerCase();
        switch (message) {
            case "help":
                send(channel, "Sorry help will be available later, now check commands from a description...");
                break;
            case "time":
                String time = new java.util.Date().toString();
                send(channel, sender + ": The time is now " + time);
                break;
            case "badges":
            case "about":
            case "exit":
                send(channel, sender + ": Sorry, command " + message + " is not allowed");
                break;
            case "esc":
            case "back":
            case "close":
                inputProcessor.pressKeyButton(Input.Keys.BACK);
                break;
            case "menu":
                inputProcessor.pressKeyButton(Input.Keys.MENU);
                break;
            case "lookaround":
                inputProcessor.pressAction(GameAction.SEARCH);
                inputProcessor.pressAction(GameAction.SEARCH);
                break;
        }
        if (COMMANDS.containsKey(message)) {
            inputProcessor.pressAction(COMMANDS.get(message));
        }
        if (message.startsWith("s")) {
            try {
                int number = Integer.parseInt(message.substring(1).trim());
                inputProcessor.pressCustomAction(number << 8, GameAction.SLOT);
            } catch (NumberFormatException nfe) {
                // do nothing
            }
        }
        if (message.startsWith("i") && message.trim().length() == 3) {
            String subMessage = message.substring(1).trim().toLowerCase();
            char c = subMessage.charAt(0);
            char c2 = subMessage.charAt(1);
            if (Character.isLetterOrDigit(c) && Character.isLetterOrDigit(c2)) {
                int a = Character.isDigit(c) ? c - 48 : c - 87;
                int b = Character.isDigit(c2) ? c2 - 48 : c2 - 87;
                inputProcessor.pressCustomAction((a * 36 + b) << 8, GameAction.ITEM);
            }
        }
    }

    private static final HashMap<String, GameAction> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("h", GameAction.HERO_INFO);
        COMMANDS.put("info", GameAction.HERO_INFO);
        COMMANDS.put("j", GameAction.JOURNAL);
        COMMANDS.put("journal", GameAction.JOURNAL);

        COMMANDS.put("s", GameAction.SEARCH);
        COMMANDS.put("search", GameAction.SEARCH);
        COMMANDS.put("t", GameAction.RESUME);
        COMMANDS.put("resume", GameAction.RESUME);

        COMMANDS.put("i", GameAction.BACKPACK);
        COMMANDS.put("backpack", GameAction.BACKPACK);
        COMMANDS.put("q1", GameAction.QUICKSLOT_1);
        COMMANDS.put("quick1", GameAction.QUICKSLOT_1);
        COMMANDS.put("q2", GameAction.QUICKSLOT_2);
        COMMANDS.put("quick2", GameAction.QUICKSLOT_2);
        COMMANDS.put("q3", GameAction.QUICKSLOT_3);
        COMMANDS.put("quick3", GameAction.QUICKSLOT_3);
        COMMANDS.put("q4", GameAction.QUICKSLOT_4);
        COMMANDS.put("quick4", GameAction.QUICKSLOT_4);

        COMMANDS.put("a", GameAction.TAG_ATTACK);
        COMMANDS.put("attack", GameAction.TAG_ATTACK);
        COMMANDS.put("tab", GameAction.TAG_DANGER);

        COMMANDS.put("zoomin", GameAction.ZOOM_IN);
        COMMANDS.put("zoomout", GameAction.ZOOM_OUT);
        COMMANDS.put("zoomreset", GameAction.ZOOM_DEFAULT);
        COMMANDS.put("zoomdefault", GameAction.ZOOM_DEFAULT);

        COMMANDS.put("1", GameAction.MOVE_BOTTOM_LEFT);
        COMMANDS.put("2", GameAction.MOVE_DOWN);
        COMMANDS.put("down", GameAction.MOVE_DOWN);
        COMMANDS.put("3", GameAction.MOVE_BOTTOM_RIGHT);
        COMMANDS.put("4", GameAction.MOVE_LEFT);
        COMMANDS.put("left", GameAction.MOVE_LEFT);
        COMMANDS.put("5", GameAction.REST);
        COMMANDS.put("space", GameAction.REST);
        COMMANDS.put("rest", GameAction.REST);
        COMMANDS.put("6", GameAction.MOVE_RIGHT);
        COMMANDS.put("right", GameAction.MOVE_RIGHT);
        COMMANDS.put("7", GameAction.MOVE_TOP_LEFT);
        COMMANDS.put("8", GameAction.MOVE_UP);
        COMMANDS.put("up", GameAction.MOVE_UP);
        COMMANDS.put("9", GameAction.MOVE_TOP_RIGHT);

        COMMANDS.put("enter", GameAction.OPERATE);
        COMMANDS.put("pick", GameAction.OPERATE);
        COMMANDS.put("pickup", GameAction.OPERATE);

        COMMANDS.put("play", GameAction.PLAY);
        COMMANDS.put("rankings", GameAction.RANKINGS);
        COMMANDS.put("main", GameAction.MAIN_MENU);
        COMMANDS.put("mainmenu", GameAction.MAIN_MENU);
        COMMANDS.put("main_menu", GameAction.MAIN_MENU);
        COMMANDS.put("options", GameAction.OPTIONS);

        COMMANDS.put("buy", GameAction.BUY);
        COMMANDS.put("sell", GameAction.SELL);
        COMMANDS.put("sell1", GameAction.SELL_1);
        COMMANDS.put("sell_1", GameAction.SELL_1);
        COMMANDS.put("sell_all", GameAction.SELL_ALL);
        COMMANDS.put("steal", GameAction.STEAL);
        COMMANDS.put("reforge", GameAction.REFORGE);
        COMMANDS.put("reward", GameAction.REWARD);
        COMMANDS.put("challenges", GameAction.CHALLENGES);
        COMMANDS.put("erase", GameAction.ERASE);

        COMMANDS.put("warrior", GameAction.WARRIOR);
        COMMANDS.put("mage", GameAction.MAGE);
        COMMANDS.put("rogue", GameAction.ROGUE);
        COMMANDS.put("huntress", GameAction.HUNTRESS);

        COMMANDS.put("yes", GameAction.YES);
        COMMANDS.put("continue", GameAction.YES);
        COMMANDS.put("start", GameAction.YES);
        COMMANDS.put("no", GameAction.NO);
        COMMANDS.put("cancel", GameAction.NO);
        COMMANDS.put("return", GameAction.NO);
        COMMANDS.put("stay", GameAction.NO);
    }

    public void send(String channel, String message) {
        GLog.p(channel + " > " + message);
        sendMessage(channel, message);
    }
}
