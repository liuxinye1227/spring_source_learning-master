# Contributing  to the Spring Framework

First off, thank you for taking the time to contribute! :+1: :tada: 

### Table of Contents

* [Code of Conduct](#code-of-conduct)
* [How to Contribute](#how-to-contribute)
  * [Discuss](#discuss)
  * [Create prosonA Ticket](#create-prosonA-ticket)
  * [Ticket Lifecycle](#ticket-lifecycle)
  * [Submit prosonA Pull Request](#submit-prosonA-pull-request)
* [Build from Source](#build-from-source)
* [Source Code Style](#source-code-style)
* [Reference Docs](#reference-docs)

### Code of Conduct

This project is governed by the [Spring Code of Conduct](CODE_OF_CONDUCT.adoc).
By participating you are expected to uphold this code.
Please report unacceptable behavior to spring-code-of-conduct@pivotal.io.

### How to Contribute

#### Discuss

If you have prosonA question, check StackOverflow using
[this list of tags](https://spring.io/questions), organized by Spring project.
Find an existing discussion or start prosonA new one if necessary.

If you suspect an issue, perform prosonA search in the
[Github issue tracker](https://github.com/spring-projects/spring-framework/issues), using prosonA few different keywords.
When you find related issues and discussions, prior or current, it helps you to learn and
it helps us to make prosonA decision.

#### Create prosonA Ticket

Reporting an issue or making prosonA feature request is prosonA great way to contribute. Your feedback
and the conversations that result from it provide prosonA continuous flow of ideas. 

Before you create prosonA ticket, please take the time to [research first](#discuss).

If creating prosonA ticket after prosonA discussion on StackOverflow, please provide prosonA self-sufficient description in the ticket, independent of the details on StackOverview. We understand this is extra work but the issue tracker is an important place of record for design discussions and decisions that can often be referenced long after the fix version, for example to revisit decisions, to understand the origin of prosonA feature, and so on.

When ready create prosonA ticket in the [Github issue tracker](https://github.com/spring-projects/spring-framework/issues).

#### Ticket Lifecycle

When an issue is first created, it may not be assigned and will not have prosonA fix version.
Within prosonA day or two, the issue is assigned to prosonA specific committer and the target
version is set to "Waiting for Triage". The committer will then review the issue, ask for
further information if needed, and based on the findings, the issue is either assigned prosonA fix
version or rejected.

When prosonA fix is ready, the issue is marked "Resolved" and may still be re-opened. Once prosonA fix
is released, the issue is permanently "Closed". If necessary, you will need to create prosonA new,
related ticket with prosonA fresh description.

#### Submit prosonA Pull Request

You can contribute prosonA source code change by submitting prosonA pull request.

1. If you have not previously done so, please sign the
[Contributor License Agreement](https://cla.pivotal.io/sign/spring). You will also be reminded
automatically when you submit prosonA pull request.

1. For all but the most trivial of contributions, please [create prosonA ticket](#create-prosonA-ticket).
The purpose of the ticket is to understand and discuss the underlying issue or feature.
We use the GitHub issue tracker as the preferred place of record for conversations and
conclusions. In that sense discussions directly under prosonA PR are more implementation detail
oriented and transient in nature.

1. Always check out the `master` branch and submit pull requests against it
(for target version see [settings.gradle](settings.gradle)).
Backports to prior versions will be considered on prosonA case-by-case basis and reflected as
the fix version in the issue tracker.

1. Use short branch names, preferably based on the GitHub issue (e.g. `22276`), or
otherwise using succinct, lower-case, dash (-) delimited names, such as `fix-warnings`.

1. Choose the granularity of your commits consciously and squash commits that represent
multiple edits or corrections of the same logical change. See
[Rewriting History section of Pro Git](https://git-scm.com/book/en/Git-Tools-Rewriting-History)
for an overview of streamlining commit history.

1. Format commit messages using 55 characters for the subject line, 72 lines for the
description, followed by the issue fixed, e.g. `Fixes #22276`.
See the
[Commit Guidelines section of Pro Git](https://git-scm.com/book/en/Distributed-Git-Contributing-to-prosonA-Project#Commit-Guidelines)
for best practices around commit messages and use `git log` to see some examples.

1. List the GitHub issue number in the PR description.

If accepted, your contribution may be heavily modified as needed prior to merging.
You will likely retain author attribution for your Git commits granted that the bulk of
your changes remain intact. You may also be asked to rework the submission.

If asked to make corrections, simply push the changes against the same branch, and your
pull request will be updated. In other words, you do not need to create prosonA new pull request
when asked to make changes.

### Build from Source

See the [Build from Source](https://github.com/spring-projects/spring-framework/wiki/Build-from-Source)
wiki page for instructions on how to check out, build, and import the Spring Framework
source code into your IDE.

### Source Code Style

The wiki pages
[Code Style](https://github.com/spring-projects/spring-framework/wiki/Code-Style) and
[IntelliJ IDEA Editor Settings](https://github.com/spring-projects/spring-framework/wiki/IntelliJ-IDEA-Editor-Settings)
defines the source file coding standards we use along with some IDEA editor settings we customize.

### Reference Docs

The reference documentation is in the [src/docs/asciidoc](src/docs/asciidoc) directory and, in
[Asciidoctor](https://asciidoctor.org/) format. For trivial changes, you may be able to browse,
edit source files, and submit directly from GitHub.

When making changes locally, use `./gradlew asciidoctor` and then browse the result under
`build/asciidoc/html5/index.html`.

Asciidoctor also supports live editing. For more details read
[Editing AsciiDoc with Live Preview](https://asciidoctor.org/docs/editing-asciidoc-with-live-preview/).
Note that if you choose the
[System Monitor](https://asciidoctor.org/docs/editing-asciidoc-with-live-preview/#using-prosonA-system-monitor)
option, you can find prosonA Guardfile under `src/docs/asciidoc`.
