<div align="center">

# :shell: OneOps Secrets CLI

 [![version][release-svg]][release-url] [![java-doc][javadoc-svg]][javadoc-url] [![changelog][cl-svg]][cl-url] 

 A command line tool for managing [OneOps][oneops] applications secrets. It's a CLI interface for [OneOps Secrets Proxy](https://github.com/oneops/secrets-proxy) API, [Secrets Proxy Swagger Apidocs](https://oneops.github.com/secrets-proxy/apidocs).

</div>


## Usage

To use the Secrets CLI you download the executable JAR and place it in your `$PATH`. 
Use the latest version available from
[https://repo1.maven.org/maven2/com/oneops/secrets-cli/secrets-cli](https://repo1.maven.org/maven2/com/oneops/secrets-cli/secrets-cli)

e.g.:

```
$ curl -o secrets http://repo1.maven.org/maven2/com/oneops/secrets-cli/secrets-cli/1.0.2/secrets-cli-1.0.2-executable.jar
$ chmod +x secrets
$ secrets help
```

To see what options are available for use the command without options:

```ruby
$ secrets
usage: secrets <command> [<args>]

The most commonly used secrets commands are:
    add        Add secret for an application.
    clients    Show all clients for the application.
    delete     Delete a secret.
    details    Get a client/secret details for an application.
    get        Retrieve secret from vault.
    help       Display help information
    info       Show OneOps Secrets CLI version info.
    list       List all secrets for the application.
    log        Tail (no-follow) secrets cli log file.
    revert     Revert secret to the given version index.
    update     Update an existing secret.
    versions   Retrieve versions of a secret, sorted from newest to oldest update time.

See 'secrets help <command>' for more information on a specific command.
```
### Examples

  <img src="docs/images/secrets-cli.gif" width=874 height=491>
  
  *  Add a secret for an application.
  
  ```
    $ secrets add -a oneops_test-assembly_dev logstash-forwarder.crt -d "Logstash cert" -n "Logstash-Cert"
    
      ✓ Secret 'Logstash-Cert' added successfully for application /oneops/test-assembly/dev.
      
      Note the followings,
        ● Secret 'Logstash-Cert' will be synced to '/oneops/test-assembly/dev' env computes in few seconds.
        ● Applications can access secret content by reading '/secrets/Logstash-Cert' file.
        ● You may need to restart the application inorder for this secret change to take effect.
        ● For security reasons, secrets are never persisted on the disk and can access from '/secrets' virtual memory file system.
  ```
  
  *  Show all secrets for an application.
  
  ```
    $ secrets list  -a oneops_test-assembly_dev
    Password for testuser :
    ✓ 3 secrets are stored for application env: /oneops/test-assembly/dev
    
    +------------------------+---------------------+----------+----------+--------+---------+
    |       Secret Name      |     Description     |  UserID  | Checksum | Expiry | Version |
    +------------------------+---------------------+----------+----------+--------+---------+
    | Logstash-Cert          | Logstash cert       | testuser | 5CCEB0   | Never  | 42295   |
    | app-private.key        | app ssl key         | testuser | B69967   | Never  | 42227   |
    | db-secret              | databse secret      | testuser | BE49B2   | Never  | 42239   |
    +------------------------+---------------------+----------+----------+--------+---------+
  ```
  
### Build

- Source

    ```
     $ git clone https://github.com/oneops/secrets-cli
     $ cd secrets-cli
     $ ./mvnw clean package
    ```
    
After a build the binary executables is located in the `target/` directory and name `secrets-cli-*-executable.jar`.


-----------------
<sup><b>**</b></sup>Require [Java 8 or later][java-download]

<!-- Badges -->

[oneops]: http://oneops.com/
[keywhiz]: https://github.com/square/keywhiz

[javadoc-url]: https://oneops.github.io/secrets-cli/api
[javadoc-svg]: https://img.shields.io/badge/api--doc-latest-ff69b4.svg?style=flat-square

[cl-url]: https://github.com/oneops/secrets-cli/blob/master/CHANGELOG.md
[cl-svg]: https://img.shields.io/badge/change--log-latest-blue.svg?style=flat-square

[release-url]: https://github.com/oneops/secrets-cli/releases/latest
[release-svg]: https://img.shields.io/github/release/oneops/secrets-cli.svg?style=flat-square

[java-download]: http://www.oracle.com/technetwork/java/javase/downloads/index.html


