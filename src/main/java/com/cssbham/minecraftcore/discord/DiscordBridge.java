package com.cssbham.minecraftcore.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.cssbham.minecraftcore.utility.MessageUtility;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import okhttp3.OkHttpClient;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DiscordBridge extends ListenerAdapter {

    private final @NotNull Plugin plugin;

    private final Long MEMBER_ROLE_ID;
    private final Long BRIDGE_CHANNEL_ID;
    private final Long DISCORD_SERVER_ID;
    private final String AVATAR;

    private JDA jda;
    private WebhookClient webhook;
    private boolean shutdown = false;

    /**
     * Used to create a discord bridge instance.
     *
     * @param plugin The instance of the minecraft plugin.
     */
    public DiscordBridge(@NotNull Plugin plugin) {
        this.plugin = plugin;

        // Get the configuration.
        FileConfiguration configuration = plugin.getConfig();
        plugin.reloadConfig();

        // Get configuration values.
        MEMBER_ROLE_ID = Long.parseLong(configuration.getString("MEMBER_ROLE_ID", "0"));
        BRIDGE_CHANNEL_ID = Long.parseLong(configuration.getString("BRIDGE_CHANNEL_ID", "0"));
        DISCORD_SERVER_ID = Long.parseLong(configuration.getString("DISCORD_SERVER_ID", "0"));
        AVATAR = configuration.getString("AVATAR_SERVICE");

        final String BOT_TOKEN = configuration.getString("BOT_TOKEN", "0");
        final String WEBHOOK_URL = configuration.getString("WEBHOOK_URL", "0");

        // Attempt to set up discord links.
        try {

            // Set up discord bot.
            this.jda = JDABuilder.createDefault(BOT_TOKEN)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS).addEventListeners(this).build();

            // Set up webhook client.
            this.webhook = new WebhookClientBuilder(WEBHOOK_URL)
                    .setThreadFactory(Thread::new)
                    .setDaemon(true)
                    .setWait(true)
                    .setHttpClient(new OkHttpClient())
                    .build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (shutdown || event.getChannel().getIdLong() != BRIDGE_CHANNEL_ID ||
                event.isWebhookMessage() ||
                event.getMember() == null ||
                event.getAuthor().isBot() ||
                event.getMessage().isEdited()) {

            return;
        }

        String hexColor = String.format("#%06X", (0xFFFFFF & event.getMember().getColorRaw())).toLowerCase();

        String output = String.format("%s%s %s§r §7>§r %s",
                MessageUtility.getDiscordPrefix(),
                MessageUtility.getChatColor(hexColor),
                event.getMember().getEffectiveName(),
                MessageUtility.sanitise(event.getMessage().getContentRaw())
        );

        plugin.getServer().broadcastMessage(output);
    }

    /**
     * Used to send a message to discord where the chat colors
     * have been removed.
     *
     * @param player The instance of the player.
     * @param message The message to send.
     */
    public void sendSanitisedMessageToDiscord(@NotNull Player player, @NotNull String message) {
        this.sendMessageToDiscord(player, MessageUtility.sanitise(message));
    }

    public void sendMessageToDiscord(@NotNull Player player, @NotNull String message) {
        if (shutdown) return;

        try {

            // Send the message though the webhook.
            webhook.send(new WebhookMessageBuilder()
                    .setAvatarUrl(String.format(AVATAR, player.getName()))
                    .setUsername(ChatColor.stripColor(player.getDisplayName()))
                    .setContent(message)
                    .build());

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // https://github.com/DV8FromTheWorld/JDA/issues/1761
    }

    /**
     * Used to check if a user is a member on the discord.
     *
     * @param identifier The member's discord identifier.
     * @return True if they are a member.
     */
    public boolean isMember(String identifier) {
        Guild guild = this.jda.getGuildById(DISCORD_SERVER_ID);
        if (guild == null) return false;

        // May need updating in the future.
        Member member = guild.getMembers().stream()
                .filter(m -> (m.getUser().getName() + "#" + m.getUser().getDiscriminator())
                        .equalsIgnoreCase(identifier))
                .findFirst()
                .orElse(null);

        // Check if they have the member role.
        return member != null && member.getRoles().stream().anyMatch(r -> r.getIdLong() == MEMBER_ROLE_ID);
    }

    public void shutdown() {
        shutdown = true;
        jda.shutdownNow();
        webhook.close();
    }
}
