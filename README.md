<div align="center">
  <h1>📚 Scala Library Template</h1>
  <p>A reusable template for Scala libraries, with automatic <a href="https://central.sonatype.com/">Maven Central</a> deployment.</p>
</div>

## 📋 What's included?

1. Everything from [Scala Config](https://github.com/SgtSwagrid/scala-config), including reasonable [Scalafmt](https://scalameta.org/scalafmt/) settings, CI piplines for build integrity, and some IDE config.
2. Automatic deployment to Maven Central using [sbt-ci-release](https://github.com/sbt/sbt-ci-release).
3. Example build configuration and setup instructions.

## 🔨 How to use this template

### 1. Create your repository

Click '[**Use this template**](https://github.com/new?template_name=scala-library-template&template_owner=SgtSwagrid)' on GitHub, and follow the instructions to create a new repository for your library.
All files herein will be copied as-is.

### 2. Configure [build.sbt](build.sbt) and [release.sbt](release.sbt)

Replace every placeholder with real values for your project.
The sbt settings necessary for publishing are defined by `sbt-ci-release` and are documented [here](https://github.com/sbt/sbt-ci-release?tab=readme-ov-file#sbt).

#### Settings to update in `release.sbt`:

| Name | Purpose | Example |
| ---- | ------- | ------- |
| `name` | Your library's name (for people). | `Dog Food Finder`
| `normalizedName` | Your library's name (for robots). | `dog-food-finder` |
| `organization` | Your organisation's package namespace. | `org.nohungrydogs` |
| `organizationName` | Your organisation's name. | `No Hungry Dogs` |
| `organizationHomepage` | Your organisation's website. | `nohungrydogs.org` |
| [`versionScheme`](https://www.scala-sbt.org/1.x/docs/Publishing.html#Version+scheme) | What does the version number say about binary compatibility? | `strict` |
| `licenses` | The license under which your library is released. Update [`LICENSE.md`](LICENSE.md) to match. | [`MIT`](https://opensource.org/license/mit) |
| `scmInfo` | Your library's Git repository. | `https://github.com/no-hungry-dogs/dog-food-finder` |
| `developers` | The individual developers who contribute to your library. | `SgtSwagrid` |

#### Settings to update in `build.sbt`:

| Name | Purpose | Example |
| ---- | ------- | ------- |
| `packagePrefix` | IntelliJ's implicit package prefix for all code files. | `org.nohungrydogs` |
| Name of subproject (following `lazy val`) | Your library's name, or the name of a particular module. | `dog-food-finder` |

#### Multiple modules

Each subproject listed in `build.sbt` is published as a separate artefact on Maven (albeit under the same versioning),
which is useful if you want a modular design whereby downstream users need not include all facets of your library.
Typically in this case you'll introduce one top-level subdirectory for each subproject.

#### A note on sbt settings

Settings are read from every `.sbt` file in the project root.
It doesn't matter what they are called, other than that sbt simply concatenates their contents in alphabetical order of their names.
A division between build information and publishing information is introduced for convenience.

### 3. Set up your Maven Central account

1. Create an account on [Maven Central](https://central.sonatype.com) to enable publishing, if you don't already have one.
2. [Register](https://central.sonatype.com/publishing/namespaces) your namespace (e.g. `org.nohungrydogs`).
   This should match the `organization` setting in `release.sbt`.
3. [Generate](https://central.sonatype.com/usertoken) a user token.
   This will give you a username and password, which you can add as repository secrets (see [step 5](#5-add-repository-secrets)).

### 4. Generate a PGP key for signing releases

Execute the following on your local machine to generate a [PGP](https://en.wikipedia.org/wiki/Pretty_Good_Privacy) key:

```bash
# Generate a new PGP key, making sure to remember your passphrase:
gpg --gen-key

# Expose the secret key in base64, using the public key provided by the above:
gpg --armor --export-secret-keys <PUBLIC_KEY> | base64

# Upload the public key to a keyserver:
gpg --keyserver keyserver.ubuntu.com --send-keys <PUBLIC_KEY>
```

### 5. Add repository secrets

Add the following secrets to your repository on GitHub, to allow publishing as part of an automated workflow:

| Secret                    | Value                                                                                                               |
|---------------------------|---------------------------------------------------------------------------------------------------------------------|
| `SONATYPE_USERNAME`       | Username from Maven user token in [step 3](#3-set-up-your-maven-central-account).                                   |
| `SONATYPE_PASSWORD`       | Password from Maven user token in [step 3](#3-set-up-your-maven-central-account).                                   |
| `PGP_SECRET`              | Base64-encoded PGP private key from [step 4](#4-generate-a-pgp-key-for-signing-releases).                           |
| `PGP_PASSPHRASE`          | Passphrase used when generating the PGP key in [step 4](#4-generate-a-pgp-key-for-signing-releases).                |
| `GH_TOKEN`                | Your GitHub [PAT](https://github.com/settings/personal-access-tokens) with administrator to access your repository. |
| `CLAUDE_CODE_OAUTH_TOKEN` | API key from [Claude](https://claude.com/product/claude-code) for agentic workflows (optional).                     |

Secrets can be added from the GitHub web interface by nagivating as follows from your repository's page:

> **Settings → Secrets and variables → Actions**

## 👮‍♂️ License

The included MIT license should be considered only as part of the template, and is not binding.
This repository is hereby released to the public domain, to be used freely.
In particular, and contra [`LICENSE.md`](LICENSE.md), you may remove the license text from copies.

## 🤝 Contributing

[`CONTRIBUTING.md`](CONTRIBUTING.md) is also part of the template, and does not _necessarily_ apply to contributions to the template itself.
The most important thing to know is that many of the configuration files are automatically synced from [`scala-config`](https://github.com/SgtSwagrid/scala-config), and should be updated there rather than here.

## 👁️ See also

Check out [`scala-website-template`](https://github.com/SgtSwagrid/scala-website-template) for a similar template to quickly start a new full stack website in Scala.

<br/><br/><br/><br/>
<h3 align="center">⬆️ Delete • Keep ⬇️</h3>
<br/><br/><br/><br/>

<div align="center">

  <h1>⚙️ My Library</h1>
  <p>A very cool Scala library that does something great.</p>
  
  <!-- Update the following URLS to show live build status in your README. -->
  <span>
    <a href="https://github.com/SgtSwagrid/scala-library-template/actions/workflows/build-integrity.yml"><img src="https://github.com/SgtSwagrid/scala-library-template/actions/workflows/build-integrity.yml/badge.svg" alt="Build status" /></a>
    <a href="https://search.maven.org/artifact/io.github.sgtswagrid/scala-library-template_3"><img src="https://img.shields.io/maven-central/v/io.github.sgtswagrid/scala-library-template_3.svg" alt="Maven Central" /></a>
  </span>
  
</div>

## ⬇️ Installation

Add the following dependency to your `build.sbt`:

<!-- Replace with the details for your own library. -->
```scala
libraryDependencies += "io.github.sgtswagrid" %% "scala-library-template" % "0.1.0"
```

## 📢 Publishing workflow

GitHub releases are automatically published to [Maven Central](https://central.sonatype.com/) upon creation, using [`sbt-dynver`](https://github.com/sbt/sbt-dynver).

### Example

To release version `1.2.3`, go to **Releases → Draft a new release**, create the tag `v1.2.3`, and click **Publish release**.
Note the inclusion of `v` in the GitHub release name but not the resulting Maven version.
