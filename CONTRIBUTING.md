<div align="center">
    <img src="https://i.hypera.dev/assets/chameleon@750x150.png" />
    <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

## Welcome
Welcome to [Chameleon](https://github.com/ChameleonFramework/Chameleon), first off, thank you for taking the time to consider contributing.  
We would love for you to contribute to Chameleon and help us improve the project as much as possible.  
This document contains a set of guidelines for contributing to Chameleon.

**Table of Contents**
- [Code of Conduct](#code-of-conduct)
- [How can I ask a question?](#how-can-i-ask-a-question)
- [How can I contribute?](#how-can-i-contribute)
    - [Reporting bugs](#reporting-bugs)
        - [Security vulnerabilities](#security-vulnerabilities)
    - [Suggesting features](#suggesting-features)
    - [Code contributions](#code-contributions)
        - [Code style](#code-style)
        - [Testing](#testing)
        - [Commit messages](#commit-messages)
        - [Code review](#code-review)
- [Supporting the authors](#supporting-the-authors)

## Code of Conduct
Please help keep this project open and inclusive for all.  
Read and follow the [Code of Conduct](https://github.com/ChameleonFramework/.github/blob/main/CODE_OF_CONDUCT.md) before contributing to this repository.  
If you have encountered someone who is not following the Code of Conduct, please report them to [contact@hypera.dev](mailto:contact@hypera.dev).

## How can I ask a question?
> **Please do not submit an issue to ask a question.** You will get a response faster if you ask on
> Discord.

If you wish to ask a question, please reach out to us on Discord by joining
the [Hypera Development Discord server](https://discord.hypera.dev/), and you will get a response as soon as someone is available.

## How can I contribute?

### Reporting Bugs
If you have found a bug in Chameleon, you can help us by [submitting an issue](https://github.com/ChameleonFramework/Chameleon/issues/new?template=bug_report.yml), or if you have the time and required knowledge, and want to really help this project, you can [submit a Pull Request](https://github.com/ChameleonFramework/Chameleon/compare) with a fix.

#### Security vulnerabilities
While we hope it never comes to this, if you have discovered a security vulnerability in Chameleon, 
**please responsibility report it to [security@hypera.dev](mailto:security@hypera.dev)**
so that we can fix it and release notifications through the correct channels. **Never** use GitHub issues to report a security vulnerability.

### Suggesting features
If you have an idea for something we could add to Chameleon, you can submit it by [creating an issue](https://github.com/ChameleonFramework/Chameleon/issues/new?template=feature_request.yml), or if you have the time and required knowledge, and want to really help this project, you can [submit a Pull Request](https://github.com/ChameleonFramework/Chameleon/compare) to add the feature.  
Before submitting a feature request, please be sure to check that it isn't already a feature and no one else has requested it.

### Code contributions
Code contributions are usually the most helpful way to contribute to this project.  
You can contribute code that you have written for Chameleon by [submitting a Pull Request](https://github.com/ChameleonFramework/Chameleon/compare).

Working on your first Pull Request?  
You can learn how from this free series: [How to Contribute to an Open Source Project on GitHub](https://kcd.im/pull-request)

#### Code style
We mostly follow the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html), however we will let you know of any styling improvements that can be made in pull request reviews.
We have automated style checks at build time, as long as these all pass then your code is good to go.

#### Testing
It is recommended to add tests for any major changes you make so that problems can be caught early.  
Currently we don't have a lot of test coverage due to it being hard to do because of the complexity of Chameleon, however we hope to fix this in the near future. If you've found something that you can add tests for, and have the time, you can contribute by [submitting a Pull Request](https://github.com/ChameleonFramework/Chameleon/compare) to add the test coverage.

#### Commit messages
Whilst not required for commits in pull requests, all commits merged into the repository must follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).
This allows the Git history to be more readable and also allows us to generate changelogs automatically.

##### Allowed types
 - `fix`, when fixing a bug.
 - `feat`, when adding a new feature.
 - `refactor`, when refactoring existing code.
 - `build`, when changing a build file.
 - `ci`, when changing a GitHub Actions workflow.
 - `docs`, when changing documentation.
 - `style`, when correcting code style.
 - `perf`, when improving the performance of something.
 - `test`, when adding or improving tests.
 - `chore`, when doing something that does not fit the above categories.

##### Allowed scopes
 - `adventure`, when modifying something that wraps Adventure.
 - `annotations`, when modifying a file inside the `annotations` module.
 - `api`, when modifying a file inside the `api` module.
 - `bom`, when modifying a file inside the `bom` module.
 - `build-logic`, when modifying a file inside the `build-logic` module.
 - `bukkit`, when modifying a file inside the `platform-bukkit` module.
 - `bungeecord`, when modifying a file inside the `platform-bungeecord` module.
 - `commands`, when modifying a file that is related to commands.
 - `events`, when modifying a file that is related to events.
 - `examples`, when modifying an example.
 - `extensions`, when modifying a file that is related to extensions.
 - `folia`, when modifying a file inside the `platform-folia` module.
 - `minestom`, when modifying a file inside the `platform-minestom` module.
 - `nukkit`, when modifying a file inside the `platform-nukkit` module.
 - `scheduling`, when modifying a file that is related to scheduling.
 - `sponge`, when modifying a file inside the `platform-sponge` module.
 - `users`, when modifying a file that is related to users.
 - `velocity`, when modifying a file inside the `platform-velocity` module.

#### Code review
We will **not** merge any pull requests that do not build, pass all tests or have style violations.
**All** code contributions **must** be licensed under the MIT License, and **must** be reviewed by the [code owners](https://github.com/ChameleonFramework/Chameleon/blob/main/.github/CODEOWNERS) that match the file(s) you are editing.

Reviews are likely to be strict to prevent problems or mistakes from being merged into the repository.  
If you have spotted a problem or mistake in someone else's pull request, feel free to leave a polite comment to make everyone else aware of the problem before it is merged.

## Supporting the authors
If you wish to support this project in a way other than reporting bugs, suggesting ideas, or contributing code, the authors accept donations via GitHub Sponsors and Ko-fi, these donations go towards allowing the authors to spend more time working on this project, or paying for infrastructure for the author's personal projects.
 - [Joshua (joshuasing)](https://github.com/sponsors/joshuasing)
 - [Luis (SLLCoding)](https://ko-fi.com/SLLCoding)
