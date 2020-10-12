package club.sk1er.uhcstars.command;

import club.sk1er.mods.core.util.JsonHolder;
import club.sk1er.mods.core.util.MinecraftUtils;
import club.sk1er.mods.core.util.Multithreading;
import club.sk1er.mods.core.util.WebUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

public class StarsCommand extends CommandBase {

    private final int[] scores = {10, 50, 150, 250, 500, 750, 1000, 2500, 5000};

    /**
     * Gets the name of the command
     */
    @Override
    public String getCommandName() {
        return "stars";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/stars <player>";
    }

    /**
     * Callback when the command is invoked
     *
     * @param sender
     * @param args
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            Multithreading.runAsync(
                () -> {
                    try {
                        JsonHolder object = WebUtil.fetchJSON("https://api.sk1er.club/player/" + args[0]);
                        if (!object.optBoolean("success")) {
                            MinecraftUtils.sendMessage(EnumChatFormatting.RED + "Player not found!");
                        } else {
                            JsonHolder uhc =
                                object.optJSONObject("player").optJSONObject("stats").optJSONObject("UHC");
                            int score = uhc.optInt("score");
                            MinecraftUtils.sendMessage(EnumChatFormatting.YELLOW + "[UHC Stars] ",
                                EnumChatFormatting.BLUE
                                    + object.optJSONObject("player").optString("displayname")
                                    + " is a "
                                    + Math.min(getStars(score), 15)
                                    + " star!");
                        }
                    } catch (Exception e) {
                        MinecraftUtils.sendMessage(EnumChatFormatting.RED + "Player not found!");
                    }
                });
        } else {
            MinecraftUtils.sendMessage(EnumChatFormatting.RED + getCommandUsage(sender));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    private int getStars(int score) {
        int stars = 1;

        for (int i : scores) {
            if (score < i) {
                break;
            }

            score -= i;
            ++stars;
        }

        return stars + score / 3000;
    }
}
