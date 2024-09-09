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
* **fabric**: `config/cssminecraft/config.yml`
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
* `/fabric`: specific implementation for Fabric servers
* `/forge`: specific implementation for Forge servers

Note that this is a server only mod, and will not work on clients.

## Version matrix

The following table documents mod compatibility for older versions of Minecraft and their platforms. 
Any versions prior to 1.12.1 are backports.

If a Minecraft version is not listed here, then no version of the mod exists for it.

All version branches will follow the name `minecraft/<version>`.

| Minecraft | Java | Bukkit | Forge | Fabric | Links                                                                   |
|-----------|------|--------|-------|--------|-------------------------------------------------------------------------|
| 1.21.1    | 21   | ✅      | ✅     | ✅      | (master)                                                                |
| 1.18.2    | 17   | ❌      | ❌     | ✅      | [Branch](https://github.com/CSSUoB/CSS-Minecraft/tree/minecraft/1.18.2) |
| 1.12.2    | 8    | ❌      | ✅     | ❌      | [Branch](https://github.com/CSSUoB/CSS-Minecraft/tree/minecraft/1.12.2) |

**Never merge `minecraft/*` branches into master.** Build features/fixes in master and cherry-pick backwards.

### Upgrading to future versions

The `master` branch should always target the latest version. 
Before upgrading, create a new release branch for the current version using the naming
scheme `minecraft/<version>`.

Then, make the necessary changes to upgrade Minecraft version. Bukkit / Spigot / Paper 
has a stable enough API where not many changes will be needed (if any at all), but
other platforms will likely break.

Once changes are done, update the version matrix and open a new PR to `master`.

### Backporting to older versions

This mod was originally made for Minecraft 1.21, thus
it will require backporting to work on older modpacks.

Create a branch from the nearest Minecraft version and name it `minecraft/<version>`. 
You may be required to change the Java version, or upgrade/downgrade Gradle. 
It should be noted that Fabric does not exist prior to Minecraft 1.14.

Once finished, push the branch to GitHub and update this version matrix with the platform
and version you have backported.

