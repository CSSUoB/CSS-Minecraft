package com.cssbham.cssminecraft.common.config.option;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cssbham.cssminecraft.common.config.option.ConfigValueFactory.*;

public class ConfigOption {

    public static final ConfigValue<String> WEBHOOK_URL = buildString("webhook-url", "");

    public static final ConfigValue<String> AVATAR_SERVICE = buildString("avatar-service", "");

    public static final ConfigValue<String> BOT_TOKEN = buildString("bot-token", "");

    public static final ConfigValue<Long> MEMBER_ROLE_ID = buildLong("member-role-id", 0);

    public static final ConfigValue<Long> BRIDGE_CHANNEL_ID = buildLong("bridge-channel-id", 0);

    public static final ConfigValue<Long> DISCORD_SERVER_ID = buildLong("discord-server-id", 0);

    public static List<ConfigValue<?>> allValues() {
        return Arrays.stream(ConfigOption.class.getFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> ConfigValue.class.equals(f.getType()))
                .map(f -> {
                    try {
                        return (ConfigValue<?>) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
