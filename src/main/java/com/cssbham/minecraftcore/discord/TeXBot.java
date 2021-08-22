package com.cssbham.minecraftcore.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.cssbham.minecraftcore.MinecraftCore;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import okhttp3.OkHttpClient;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class TeXBot extends ListenerAdapter {

    private final Long MEMBER_ROLE_ID;
    private final Long BRIDGE_CHANNEL_ID;
    private final Long DISCORD_SERVER_ID;
    private final String AVATAR;

    private JDA jda = null;
    private MinecraftCore core = null;
    private WebhookClient webhook = null;
    private boolean shutdown = false;

    public TeXBot(MinecraftCore core) throws LoginException, ClassCastException {

        FileConfiguration configuration = core.getConfig();
        core.reloadConfig();
        MEMBER_ROLE_ID = Long.parseLong(configuration.getString("MEMBER_ROLE_ID"));
        BRIDGE_CHANNEL_ID = Long.parseLong(configuration.getString("BRIDGE_CHANNEL_ID"));
        DISCORD_SERVER_ID = Long.parseLong(configuration.getString("DISCORD_SERVER_ID"));
        AVATAR = configuration.getString("AVATAR_SERVICE");
        final String BOT_TOKEN = configuration.getString("BOT_TOKEN");
        final String WEBHOOK_URL = configuration.getString("WEBHOOK_URL");

        System.out.println("mem role: " + MEMBER_ROLE_ID + "\nbrd chan: " + BRIDGE_CHANNEL_ID +
                "\ndid: "+ DISCORD_SERVER_ID + "\nava: "+ AVATAR + "\ntkn: " + BOT_TOKEN + "\nwhu :" +  WEBHOOK_URL);

//        if (MEMBER_ROLE_ID == 0 || BRIDGE_CHANNEL_ID == 0 || DISCORD_SERVER_ID == 0 ||
//                AVATAR == null || WEBHOOK_URL == null || BOT_TOKEN == null) {
//            System.out.println("disabling self");
//            core.getPluginLoader().disablePlugin(core);
//            return;
//        }

        this.core = core;
        this.jda = JDABuilder.createDefault(
                BOT_TOKEN
        ).setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS).addEventListeners(this).build();
        this.webhook = new WebhookClientBuilder(WEBHOOK_URL)
                .setThreadFactory(Thread::new)
                .setDaemon(true)
                .setWait(true)
                .setHttpClient(new OkHttpClient())
                .build();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (shutdown || event.getChannel().getIdLong() != BRIDGE_CHANNEL_ID ||
                event.isWebhookMessage() ||
                event.getMember() == null ||
                event.getAuthor().isBot() ||
                event.getMessage().isEdited()) {
            return;
        }
        String hexColor = String.format("#%06X", (0xFFFFFF & event.getMember().getColorRaw()));

        String output = String.format("%s[Discord]§r%s %s§r §7>§r %s",
                net.md_5.bungee.api.ChatColor.of("#738abd"),
                net.md_5.bungee.api.ChatColor.of(hexColor.toLowerCase()),
                event.getMember().getEffectiveName(),
                ChatColor.stripColor(event.getMessage().getContentStripped())
        );
        core.getServer().broadcastMessage(output);
    }

    public void sendSanitisedMessageToDiscord(Player player, String message) {
        this.sendMessageToDiscord(player, ChatColor.stripColor(MarkdownSanitizer.sanitize(message)));
    }

    public void sendMessageToDiscord(Player player, String message) {
        if (shutdown) return;
        try {
            webhook.send(new WebhookMessageBuilder()
                    .setAvatarUrl(String.format(AVATAR, player.getName()))
                    .setUsername(ChatColor.stripColor(player.getDisplayName()))
                    .setContent(message)
                    .build());
        } catch (Exception ignored) {
        }
        // https://github.com/DV8FromTheWorld/JDA/issues/1761
    }

    public boolean isMember(String identifier) {
        Guild g = jda.getGuildById(DISCORD_SERVER_ID);
        if (g == null) return false;
        Member m = g.getMembers().stream()
                .filter(mm ->
                        (mm.getUser().getName() + "#" + mm.getUser().getDiscriminator()).equalsIgnoreCase(identifier)
                ).findFirst().orElse(null);
        if (m == null) return false;
        return m.getRoles().stream().anyMatch(r -> r.getIdLong() == MEMBER_ROLE_ID);
    }

    public void shutdown() {
        shutdown = true;
        jda.shutdownNow();
        webhook.close();
    }
}
