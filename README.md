<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the vCompat and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
***
***
***
*** To avoid retyping too much info. Do a search and replace for the following:
*** SourceWriters, vCompat, twitter_handle, email, vCompat, project_description
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![Code quality: Java][lgtm-quality-shield]][lgtm-quality-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/SourceWriters/vCompat">
    <img src="images/logo.png" alt="Logo" width="121" height="91">
  </a>

  <h3 align="center">vCompat</h3>

  <p align="center">
    <!-- TODO: project_description -->
    <br />
    <a href="https://confluence.syntaxphoenix.com/display/VCOMPAT"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/SourceWriters/vCompat">View Demo</a>
    ·
    <a href="https://github.com/SourceWriters/vCompat/issues">Report Bug</a>
    ·
    <a href="https://github.com/SourceWriters/vCompat/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

<!-- [![Product Name Screen Shot][product-screenshot]](https://example.com) -->


### Built With

* [Spigot](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/spigot/browse)
* [SyntaxApi](https://github.com/SyntaxPhoenix/syntaxapi)

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

Please notice that you need to authenticate with GitHub Packages ([Maven](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#authenticating-to-github-packages) / [Gradle](https://docs.github.com/en/packages/guides/configuring-gradle-for-use-with-github-packages#authenticating-to-github-packages)) for this to work

### Installation with Maven

```XML
<depedencies>
  <depedency>
    <groupId>net.sourcewriters.minecraft</groupId>
    <artifactId>vcompat</artifactId>
    <version>2.1.5</version>
    <scope>provided</scope>
  <dependency>
<depedencies>
```

### Installation with Gradle

```Groovy
plugins {
  id 'maven'
}

dependencies {
  compileOnly group: 'net.sourcewriters.minecraft', name: 'vcompat', version: '2.1.5'
}
```

Please use the [vCompat Updater](https://github.com/SourceWriters/vCompatUpdater) to use vCompat in your projects!
An example implementation can be found [here](https://github.com/SourceWriters/vCompatExample)

<!-- USAGE EXAMPLES -->
## Usage

_Comming soon_

_For more examples, please refer to the [Documentation](https://confluence.syntaxphoenix.com/display/VCOMPAT)_



<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/SourceWriters/vCompat/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the GPLv3 License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

[@SyntaxPhoenix](https://twitter.com/SyntaxPhoenix) - support@syntaxphoenix.com

Project Link: [https://github.com/SourceWriters/vCompat](https://github.com/SourceWriters/vCompat)





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/SourceWriters/vCompat.svg?style=flat-square
[contributors-url]: https://github.com/SourceWriters/vCompat/graphs/contributors
[stars-shield]: https://img.shields.io/github/stars/SourceWriters/vCompat.svg?style=flat-square
[stars-url]: https://github.com/SourceWriters/vCompat/stargazers
[issues-shield]: https://img.shields.io/github/issues/SourceWriters/vCompat.svg?style=flat-square
[issues-url]: https://github.com/SourceWriters/vCompat/issues
[license-shield]: https://img.shields.io/github/license/SourceWriters/vCompat.svg?style=flat-square
[license-url]: https://github.com/SourceWriters/vCompat/blob/master/LICENSE
[lgtm-quality-shield]: https://img.shields.io/lgtm/grade/java/g/SourceWriters/vCompat.svg?style=flat-square
[lgtm-quality-url]: https://lgtm.com/projects/g/SourceWriters/vCompat/context:java
