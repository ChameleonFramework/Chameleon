<div align="center">
    <img alt="Chameleon Logo" src="https://assets.hypera.dev/chameleon@750x150.png" />
    <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

## Welcome!

Welcome to the [Chameleon Framework](https://github.com/ChameleonFramework/Chameleon), first off,
thank you for taking the time to consider contributing!

All contributions to Chameleon are extremely helpful and greatly appreciated! We are trying our best
to make this project as good as possible, but we're still improving things.

This document contains a set of guides for contributing to this project.

<details>
<summary>Table of Contents</summary>

<!-- TOC -->
  * [Welcome!](#welcome)
  * [Code of Conduct](#code-of-conduct)
  * [Questions](#questions)
  * [Contributing](#contributing)
    * [Bug reports](#bug-reports)
      * [Security vulnerabilities](#security-vulnerabilities)
    * [Suggesting features](#suggesting-features)
    * [Code contributions](#code-contributions)
      * [Code style](#code-style)
      * [Testing](#testing)
      * [Commit messages](#commit-messages)
        * [Allowed types](#allowed-types)
        * [Allowed scopes](#allowed-scopes)
      * [Code review](#code-review)
  * [Supporting the authors](#supporting-the-authors)
  * [Maintainer's guide](#maintainers-guide)
    * [Release process](#release-process)
      * [Snapshots](#snapshots)
      * [Releases](#releases)
        * [Creating a new release](#creating-a-new-release)
<!-- TOC -->
</details>

## Code of Conduct

Please help keep this project open and inclusive for all.<br/>
Read and follow
the [Code of Conduct](https://github.com/ChameleonFramework/.github/blob/main/CODE_OF_CONDUCT.md)
before contributing to this repository.

If you have encountered someone who is not following the Code of Conduct, please report them
to [oss@hypera.dev](mailto:oss@hypera.dev).

## Questions

> **Please do not use GitHub issues to ask questions.** You will get a faster response if you ask on
> Discord!

If you would like to ask a question, please contact us using Discord by joining
the [Hypera Development Discord server](https://discord.hypera.dev), and you will get a response as
soon as someone is next available.

## Contributing

### Bug reports

If you have discovered a bug in Chameleon, you can help us
by [creating an issue](https://github.com/ChameleonFramework/Chameleon/issues/new?template=bug_report.yml),
or if you have the time and required knowledge, and really want to help this project, you
can [create a Pull Request](https://github.com/ChameleonFramework/Chameleon/compare) with a fix.

#### Security vulnerabilities

We take the security of Chameleon and our users very seriously. As such, we encourage responsible
disclosure of security vulnerabilities in Chameleon.

If you have discovered a security vulnerability in Chameleon, please report it in accordance with
our [Security Policy](SECURITY.md#reporting-a-vulnerability).<br/>
**Never use GitHub issues to report a security vulnerability.**

### Suggesting features

If you have an idea for something that could be added to Chameleon, you can suggest it
by [creating an issue](https://github.com/ChameleonFramework/Chameleon/issues/new?template=feature_request.yml)!<br/>
Before submitting a feature request, please be sure to check that it hasn't already been suggested.

We ask that people create feature requests even if you are going to make a pull request. This helps
us keep track of everything.

### Code contributions

Code contributions are often the most helpful way to contribute to this project, and all code
contributions will be greatly appreciated!

You can contribute code changes that you have written for Chameleon
by [creating a Pull Request](https://github.com/ChameleonFramework/Chameleon/compare).

#### Code style

We primarily follow
the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).<br/>
During the pull request review process, we will let you know if we think any style changes should be
made.

#### Testing

Adding test coverage is extremely helpful and highly recommended for any major changes you
make.<br/>
Testing helps us catch problems early, and before they have the chance to cause issues.

#### Commit messages

Whilst not required for commits in pull requests, all commits made in the `main` branch **must**
follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification.
Additionally, commit message lines should not exceed 100 characters.

##### Allowed types

- `fix`, when fixing a bug or other issue.
- `feat`, when adding a new feature.
- `refactor`, when refactoring existing code.
- `build`, when changing a build file.
- `ci`, when changing a GitHub Actions workflow.
- `docs`, when changing documentation.
- `style`, when correcting code-style issues.
- `perf`, when improving the performance of a feature.
- `test`, when adding or improving tests.
- `chore`, when doing something that does not fit into a type listed above.

##### Allowed scopes

- `adventure`, when modifying something that wraps Adventure.
- `annotations`, when modifying a file inside the `annotations` module.
- `api`, when modifying a file inside the `api` module.
- `bom`, when modifying a file inside the `bom` module.
- `build-logic`, when modifying a file inside the `build-logic` module.
- `bukkit`, when modifying a file inside the `platform-bukkit` module.
- `bungeecord`, when modifying a file inside the `platform-bungeecord` module.
- `commands`, when modifying a file that is related to commands.
- `deps`, when adding, updating, or removing dependencies.
- `events`, when modifying a file that is related to events.
- `examples`, when modifying an example.
- `extensions`, when modifying a file that is related to extensions.
- `nukkit`, when modifying a file inside the `platform-nukkit` module.
- `platform`, when modifying a file inside the `platform-api` module.
- `scheduling`, when modifying a file that is related to scheduling.
- `scripts`, when modifying a file inside the `scripts` directory.
- `sponge`, when modifying a file inside the `platform-sponge` module.
- `users`, when modifying a file that is related to users.
- `velocity`, when modifying a file inside the `platform-velocity` module.

When using the type `ci`, workflow names (`.github/workflows/` files, excluding extensions) may be
used as the scope.

#### Code review

We have strict guidelines for merging pull requests to maintain code quality, and to prevent future
problems:

1. Pull requests must successfully build, pass all tests, and adhere to style guidelines. Any pull
   requests that fail to do so, will not be merged.
2. Code contributions must be licensed under the [MIT License](LICENSE).
3. Modifications must be reviewed by the [code owners](.github/CODEOWNERS) for the respective files.

We value high code quality, which is why our pull request reviews are rigorous. This approach
ensures that problems or mistakes are caught before being merged into the `main` branch.

If you receive numerous comments pointing out mistakes in your pull request, please don't take
offense. Mistakes are normal, and you should not be ashamed of them.

If you spot issues in someone else's pull request, kindly leave a polite comment to make others
aware of the problem before it gets merged. Your help in maintaining code quality is greatly
appreciated.

## Supporting the authors

If you wish to support this project in another way, the authors accept donations!
These donations go towards enabling the authors to spend more time working on this project, paying
for infrastructure/domains, etc. All donations are extremely appreciated! :D

- [Joshua (joshuasing)](https://github.com/sponsors/joshuasing)
- [Luis (LooFifteen)](https://ko-fi.com/loofifteen)

## Maintainer's guide

This section contains guides for the maintainers of Chameleon.

### Release process

#### Snapshots

Snapshots are automatically published from the `main` branch.

#### Releases

Releases are automatically published from `releases/<major>` branches.

##### Creating a new release

Release scripts can be found in the [`scripts/`](scripts) directory.

1. Checkout `main` branch: `git checkout main`
2. Make sure local `main` branch is up-to-date: `git pull origin main`
3. Update `build.gradle.kts` and remove `-SNAPSHOT` from the version
4. If applicable, update [`SECURITY.md` Supported Versions](SECURITY.md#supported-versions)
5. Commit the change: `git commit -am 'release: <version>'`
6. Push release branch: `git push origin main:releases/<major>`
7. Update `build.gradle.kts` and bump the version to the next version with `-SNAPSHOT` appended
8. Commit the change: `git commit -am 'chore: bump version to <next-version>'`
9. Push changes to `main` branch: `git push origin main`
