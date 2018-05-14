# :shell: OneOps Secrets CLI
[![Maven Central][maven-svg]][maven-url] [![changelog][cl-svg]][cl-url] [![apidoc][apidoc-svg]][apidoc-url]  

A command line tool for managing [OneOps](http://oneos.com) application secrets.OneOps Secrets CLI interacts with the
[OneOps Secrets Proxy](http://oneops.com/user/account/secrets-proxy.html) API.

## Usage

Detailed user documentation for installation and usage is available on the 
[OneOps website](http://oneops.com/user/design/secrets-client-component) as well
as via the help function:

```bash
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

Download
--------

Download [the latest JAR][1] or grab via Maven:
```xml
<dependency>
    <groupId>com.oneops</groupId>
    <artifactId>secrets-cli</artifactId>
    <version>1.0.5</version>
</dependency>
```

### Examples

  <img src="docs/images/secrets-cli.gif" width=874 height=491>
  
  *  Add a secret for an application.
  
  ```bash
    $ secrets add -a oneops_test-assembly_dev logstash-forwarder.crt -d "Logstash cert" -n "Logstash-Cert"
    
      ✓ Secret 'Logstash-Cert' added successfully for application /oneops/test-assembly/dev.
      
      Note the followings,
        ● Secret 'Logstash-Cert' will be synced to '/oneops/test-assembly/dev' env computes in few seconds.
        ● Applications can access secret content by reading '/secrets/Logstash-Cert' file.
        ● You may need to restart the application inorder for this secret change to take effect.
        ● For security reasons, secrets are never persisted on the disk and can access from '/secrets' virtual memory file system.
  ```
  
  *  Show all secrets for an application.
  
  ```bash
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

    > Make sure to provide proper secret-proxy [truststore](src/main/resources/keystores/secrets_proxy_truststore.p12) and [application.conf](src/main/resources/application.conf) before doing the build. Use [InstallCerts](https://github.com/sureshg/InstallCerts) tool to auto-generate trust-store from your secret proxy HTTPS endpoint.
     
    ```
     $ git clone https://github.com/oneops/secrets-cli
     $ cd secrets-cli
     $ ./mvnw clean package
    ```
    
After a build the binary executables is located in the `target/` directory and name `secrets-cli-*-executable.jar`.

<!-- Badges -->

[1]: https://search.maven.org/remote_content?g=com.oneops&a=secrets-cli&v=LATEST

[maven-url]: http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.oneops%22%20AND%20a%3A%22secrets-cli%22
[maven-svg]: https://img.shields.io/maven-central/v/com.oneops/secrets-cli.svg?label=Maven%20Central&style=flat-square
 
[cl-url]: https://github.com/oneops/secrets-cli/blob/master/CHANGELOG.md
[cl-svg]: https://img.shields.io/badge/change--log-latest-green.svg?style=flat-square
 
[apidoc-url]: https://oneops.github.com/secrets-proxy/apidocs
[apidoc-svg]: https://img.shields.io/badge/api--doc-latest-cyan.svg?style=flat-square]

