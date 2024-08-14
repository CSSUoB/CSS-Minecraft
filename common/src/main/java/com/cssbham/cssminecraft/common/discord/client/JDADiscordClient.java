package com.cssbham.cssminecraft.common.discord.client;

import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.events.DiscordMessageEvent;
import com.cssbham.cssminecraft.common.logger.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;

public class JDADiscordClient extends ListenerAdapter implements DiscordClient {

    private final EventBus eventBus;
    private final Logger logger;
    private final String botToken;
    private final long discordServerId;
    private final long memberRoleId;
    private final long bridgeChannelId;

    private JDA jda;

    public JDADiscordClient(EventBus eventBus, Logger logger, String botToken, long discordServerId, long memberRoleId, long bridgeChannelId) {
        this.eventBus = eventBus;
        this.logger = logger;
        this.botToken = botToken;
        this.discordServerId = discordServerId;
        this.memberRoleId = memberRoleId;
        this.bridgeChannelId = bridgeChannelId;
    }

    @Override
    public void initialise() {
        if (null != this.jda) {
            logger.debug("JDA already initialised, skipping initialisation request");
            return;
        }

        logger.debug("Initialising JDA");

        this.jda = JDABuilder.createDefault(
                        botToken
                ).setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(this)
                .build();
    }


    @Override
    public void shutdown() {
        if (null == this.jda) {
            logger.debug("JDA already stopped, skipping stop request");
            return;
        }

        logger.debug("Shutting down JDA");

        jda.shutdownNow();
        this.jda = null;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        logger.debug(String.format("Message received from %s: %s", event.getAuthor().getName(), event.getMessage().getContentRaw()));
        if (!event.isFromGuild() || event.getChannel().getIdLong() != bridgeChannelId ||
                event.isWebhookMessage() ||
                event.getMember() == null ||
                event.getAuthor().isBot() ||
                event.getMessage().isEdited()) {
            return;
        }

        eventBus.dispatch(new DiscordMessageEvent(
                event.getMember().getEffectiveName(),
                event.getMessage().getContentRaw(),
                event.getMember().getColorRaw()
        ));
    }

    @Override
    public boolean isMember(String identifier) {
        Guild g = jda.getGuildById(discordServerId);
        if (g == null) return false;
        Member m = g.getMembers().stream()
                .filter(mm ->
                        (mm.getUser().getName() + "#" + mm.getUser().getDiscriminator()).equalsIgnoreCase(identifier) ||
                                mm.getUser().getName().equalsIgnoreCase(identifier)
                ).findFirst().orElse(null);
        if (m == null) return false;
        return m.getRoles().stream().anyMatch(r -> r.getIdLong() == memberRoleId);
    }

}
