package com.cssbham.cssminecraft.common.discord.webhook;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.cssbham.cssminecraft.common.event.EventHandler;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import okhttp3.OkHttpClient;

public class DiscordWebHookClient implements WebHookClient {

    private final String webHookUrl;
    private final String avatarServiceUrl;

    private WebhookClient webhook = null;

    public DiscordWebHookClient(String webHookUrl, String avatarServiceUrl) {
        this.webHookUrl = webHookUrl;
        this.avatarServiceUrl = avatarServiceUrl;
    }

    @Override
    public void initialise() {
        this.webhook = new WebhookClientBuilder(webHookUrl)
                .setThreadFactory(Thread::new)
                .setDaemon(true)
                .setWait(true)
                .setHttpClient(new OkHttpClient())
                .build();
    }

    @Override
    public void sendMessageAsMinecraftUser(String avatarName, String displayName, String message) {
        try {
            webhook.send(new WebhookMessageBuilder()
                    .setAvatarUrl(String.format(avatarServiceUrl, avatarName))
                    .setUsername(displayName)
                    .setContent(message)
                    .build());
        } catch (Exception ignored) {
        }
        // https://github.com/DV8FromTheWorld/JDA/issues/1761
    }
}
