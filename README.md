<div align="center">
    <a href="#logo"><img src="https://i.hypera.dev/assets/chameleon@750x150.png" /></a>
    <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

-----------
<div align="center">
   <img alt="GitHub" src="https://img.shields.io/github/license/HyperaOfficial/Chameleon?color=%2317aaaa&style=for-the-badge"> <img alt="Libraries.io dependency status for GitHub repo" src="https://img.shields.io/librariesio/github/HyperaOfficial/Chameleon?color=%2317aaaa&&label=Dependencies&style=for-the-badge"><br/>
   <img alt="GitHub code quality" src="https://img.shields.io/codefactor/grade/github/HyperaOfficial/Chameleon/master?style=for-the-badge&color=%2317aaaa">
   <img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/HyperaOfficial/Chameleon?color=%2317aaaa&style=for-the-badge">
   <img alt="Snyk Vulnerabilities for GitHub Repo" src="https://img.shields.io/snyk/vulnerabilities/github/HyperaOfficial/Chameleon?color=%2317aaaa&style=for-the-badge">
</div>

### What is Chameleon?
Chameleon is a library created to make creating cross-platform Minecraft plugins as simple and easy as possible.


### Supported Platforms
Chameleon plans to support every platform in this list. If you have ideas for other platforms that we could support, please suggest them on our [Official Feedback Site][Feedback].
* [x] [BungeeCord]
* [x] [Spigot]
* [x] [Velocity]
* [x] [Minestom]
* [ ] [Sponge]

### How do I use Chameleon?
As this is hard to explain, we have created an [Example Project][Example]. In short, you have a common module with non-platform-specific code (this would only be Chameleon code), then you have a module for each platform you would like to support.  
You can also check out our [Development Roadmap][Roadmap] or various examples of [BungeeCord support][BungeeCord-project], [Spigot support][Spigot-project], [Velocity support][Velocity-project] and [Minestom support][Minestom-project] for reference.

### Project Structure
 * **Core** - Most of Chameleon's non-platform-specific code belongs here.
 * **Platforms** - Any platform-specific code belongs inside here.
   * *BungeeCord* - Any code that interacts with BungeeCord belongs here.
   * *Minestom* - Any code that interacts with Minestom belongs here.
   * *Spigot* - Any code that interacts with Bukkit or Spigot belongs here.
   * *Velocity* - Any code that interacts with Velocity belongs here.


### Contributing
Please read [CONTRIBUTING].

### License
The contents of this repository is licensed under the [MIT License](LICENSE).


[BungeeCord]: https://www.spigotmc.org/wiki/bungeecord/
[Spigot]: https://www.spigotmc.org/
[Velocity]: https://velocitypowered.com/
[Minestom]: https://www.minestom.net/
[Sponge]: https://www.spongepowered.org/
[Feedback]: https://feedback.hypera.dev/
[Example]: https://github.com/HyperaOfficial/ChameleonProject
[Roadmap]: DEVELOPMENT.md
[BungeeCord-project]: Platforms/BungeeCord/README.md
[Spigot-project]: Platforms/Spigot/README.md
[Velocity-project]: Platforms/Velocity/README.md
[Minestom-project]: Platforms/Minestom/README.md
[CONTRIBUTING]: CONTRIBUTING.md