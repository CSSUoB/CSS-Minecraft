# CSS-Minecraft
This is the source code for CSS' Minecraft plugin.

# Building

Ensure that you have maven installed.
Then:
```
maven clean package
```

This will automatically execute the shadow-jar goal in the package cycle.

There will now be a .jar file located in /target

You can now put this .jar file in the /plugins folder in a Spigot/CraftBukkit 1.17.1 server and run the server.

# Configuration


MEMBER_ROLE_ID: The ID of the role that the plugin checks against when someone runs the /makegreen command.

BRIDGE_CHANNEL_ID: The ID of the channel to send bridged messages to Minecraft.

DISCORD_SERVER_ID: The ID of the guild to interact with.

WEBHOOK_URL: The URL of the Discord webhook to send bridged messages from Minecraft.

AVATAR_SERVICE: A link to an avatar service, with %s as a placeholder of the user's minecraft username.

BOT_TOKEN: The token of the Discord bot that will be detecting messages to send to Minecraft, as well as Member roles.


# Development

PR's welcome, feel free to do whatever.
