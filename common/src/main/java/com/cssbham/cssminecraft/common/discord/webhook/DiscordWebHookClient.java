package com.cssbham.cssminecraft.common.discord.webhook;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.cssbham.cssminecraft.common.event.EventHandler;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import okhttp3.OkHttpClient;

import java.util.Objects;

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
        if (null != this.webhook) {
            return;
        }

        this.webhook = new WebhookClientBuilder(webHookUrl)
                .setThreadFactory(Thread::new)
                .setDaemon(true)
                .setWait(true)
                .setHttpClient(new OkHttpClient())
                .build();
    }

    @Override
    public void shutdown() {
        if (null == this.webhook) {
            return;
        }

        if (this.webhook.isShutdown()) {
            this.webhook = null;
            return;
        }

        this.webhook.close();
        this.webhook = null;
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
            ignored.printStackTrace();
        }
        // https://github.com/DV8FromTheWorld/JDA/issues/1761
    }
}
