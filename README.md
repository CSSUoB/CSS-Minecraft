> [!WARNING]  
> This is a release branch for **Minecraft 1.12.2**. Do not build new features on this branch.  
> This version was backported from commit 4ea0b36b208596676fc6d907b1073f226e0c738d.

# CSS Minecraft
This is the source code for CSS' Minecraft plugin/mod.

## Building

To compile, run the `build` Gradle task.

```
./gradlew build
```

Jars will be output to `**/build/libs/cssminecraft-*.jar`.

## Configuration

The configuration file will be located in the server configuration directory, which depends on the platform.
Typically, this will be at:

* **bukkit**: `plugins/CSSMinecraft/config.yml`
* **forge**: `config/cssminecraft/config.yml`

```yaml
# The ID of the role that the plugin checks against when someone runs the /makegreen command.
member-role-id: 0

# The ID of the channel to send bridged messages to Minecraft.
bridge-channel-id: 0

# The ID of the guild to interact with.
discord-server-id: 0

# The URL of the Discord webhook to send bridged messages from Minecraft.
webhook-url: ""

# The token of the Discord bot that will be detecting messages to send to Minecraft, as well as Member roles.
bot-token: ""

# A link to an avatar service, with %s as a placeholder of the user's minecraft username.
# This is used as the profile picture URL in webhook messages.
# We'd recommend the following value: https://cravatar.eu/helmhead/%s/190.png
avatar-service: ""

# The verbosity of logging (0 = error only, 1 = +warnings, 2 = +info, 3 = +debug)
logging-level: 2
```

## Dependencies

This plugin optionally depends on [LuckPerms](https://luckperms.net/) to grant the member role.

Without it, only the Discord message bridge will be functional.

## Development

PR's welcome, feel free to do whatever.

The project is written mostly in an abstract fashion to help re-use code across
different platforms. 

Each Gradle subproject has the following purpose:
* `/common`: platform-independent interfaces and implementations which houses most logic - the 
following subprojects depend on this
* `/bukkit`: specific implementation for Bukkit / Spigot / Paper etc.
* `/forge`: specific implementation for Forge servers

Note that this is a server only mod, and will not work on clients.
