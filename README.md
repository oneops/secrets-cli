<div align="center">

# :shell: OneOps Secrets CLI (WIP...)

 [![version][release-svg]][release-url] [![changelog][cl-svg]][cl-url] [![api-doc][apidoc-svg]][apidoc-url]

[Keywhiz][keywhiz] shell is a CLI program for managing application secrets in [OneOps][oneops]. Secrets associated with an assembly can be queried, added, removed using this CLI. Users can authenticate and use the CLI.

</div>


## Usage

To use the Secrets CLI you download the executable JAR and place it in your `$PATH`. Use the 
latest version available from
[http://repo1.maven.org/maven2/com/oneops/secrets-cli/secrets-cli](http://repo1.maven.org/maven2/com/oneops/secrets-cli/secrets-cli)
e.g.:

```
curl -o secrets-cli http://repo1.maven.org/maven2/com/oneops/secrets-cli/secrets-cli/1.0.2/secrets-cli-1.0.2-executable.jar

chmod +x secrets-cli

secrets-cli <options>
```

To see what options are available for use the command without options:

```
secrets-cli
```

### Build

- Source

    ```
     $ git clone https://github.com/oneops/secrets-cli
     $ cd secrets-cli
     $ ./mvnw clean package
    ```
    
After a build the binary executables is located in the `target/` directory
and name `secrets-cli-*-executable.jar`.

- API Doc

The API docs is generated under the [docs](docs/api), and can be published as
[GitHub Pages][github-pages].

    
    ```
     $ cd keywhiz-cli
     $ ./mvnw clean javadoc:javadoc
    ```


-----------------
<sup><b>**</b></sup>Require [Java 8 or later][java-download]

<!-- Badges -->

[oneops]: http://oneops.com/
[keywhiz]: https://github.com/square/keywhiz

[apidoc-url]: https://oneops.github.io/keywhiz-cli/api
[apidoc-svg]: https://img.shields.io/badge/api--doc-latest-ff69b4.svg?style=flat-square

[cl-url]: https://github.com/oneops/keywhiz-cli/blob/master/CHANGELOG.md#011---2017-05-31
[cl-svg]: https://img.shields.io/badge/change--log-0.1.1-blue.svg?style=flat-square

[release-url]: https://github.com/oneops/keywhiz-cli/releases/latest
[download-url]: https://github.com/oneops/keywhiz-cli/releases/download/0.1.1/keywhiz-cli
[release-svg]: https://img.shields.io/github/release/oneops/keywhiz-cli.svg?style=flat-square

[execjar-url]: https://github.com/oneops/keywhiz-cli/releases/download/0.1.1/keywhiz-cli.jar
[execjar-svg]: https://img.shields.io/badge/exec--jar-0.1.1-00BCD4.svg?style=flat-square

[license-url]: https://github.com/oneops/keywhiz-cli/blob/master/LICENSE
[license-svg]: https://img.shields.io/github/license/oneops/keywhiz-cli.svg?style=flat-square

[total-dl-url]: https://github.com/oneops/keywhiz-cli/releases
[total-dl-svg]: https://img.shields.io/github/downloads/oneops/keywhiz-cli/total.svg?style=flat-square

[java-download]: http://www.oracle.com/technetwork/java/javase/downloads/index.html

[github-token]: https://github.com/settings/tokens
[github-pages]: https://pages.github.com/
[github-pages-pub]: https://help.github.com/articles/configuring-a-publishing-source-for-github-pages/

