# Remal JCEKS Keystore Tool

_keywords: java, jceks, keystore, cli_

_[Release Note](release.md)_

## 1) Overview

The `JCEKS-tool` is a command-line tool written in Java and can be used on all platforms where Java is available.
The tool can be used to manage and make operations on JCEKS keystore.
You can use this tool 
* to show the value of a secret key entry
* copy secret key entry from source keystore to a target one

`JCEKS-tool` is created with a main purpose to be used in Linux shell-scripts or in Windows command line.

## 2) Examples
### 2.1) Show the value of a secret key entry
~~~
java -jar \
   bin/jceks-tool-0.1.0.jar \
   show
   --keystore sample/amster/amster-transportkey-keystore.jceks \
   --keystore-password-file sample/amster/.storepass \
   --alias sms.transport.key \
   --entry-password-file sample/amster/.keypass
~~~

### 2.2) Copy a secret key between keystores
~~~
java -jar bin/jceks-tool-0.1.0.jar \
   copy \
   --source-keystore sample/amster/amster-transportkey-keystore.jceks \
   --source-keystore-password-file sample/amster/.storepass \
   --source-alias sms.transport.key \
   --source-entry-password-file sample/amster/.keypass \
   --target-keystore sample/empty-keystore/keystore.jceks \
   --target-keystore-password-file sample/empty-keystore/.storepass \
   --target-alias hello \
   --target-entry-password changeit
~~~

## 3) Usage
### 3.1) Usage
~~~
$ java -jar target/jceks-tool-0.1.0.jar 

Usage: jceks-tool [?=<main>]... (show | copy)
JCEKS keystore command line tool.

  ? , --help   display this help message

Commands:
  show  Show the value of a secret key.
  copy  Copy a secret key from the source keystore to a target keystore.

Exit codes:
  0    Successful program execution.
  1    An unexpected error appeared while executing the tool.
  2    Exit code on Invalid input.

Please report issues at arnold.somogyi@gmail.com.
Documentation, source code: https://github.com/zappee/jceks-tool.git
~~~

### 3.2) Show
~~~
$ java -jar target/jceks-tool-0.1.0.jar show

Usage: jceks-tool show [-q] -a=<alias> -k=<keystoreLocation> (-p=<keystorePassword> | -f=<keystorePasswordFile>) (-e=<entryPassword> | -n=<entryPasswordFile>)

Show the value of a secret key.

  -q, --quiet               In this mode nothing will be printed to the output.
  -k, --keystore            path to the keystore
  -p, --keystore-password   password for the keystore
  -f, --keystore-password-file
                            keystore password file
  -a, --alias               alias name of the keystore entry
  -e, --entry-password      password for the keystore entry
  -n, --entry-password-file keystore entry password file

Please report issues at arnold.somogyi@gmail.com.
Documentation, source code: https://github.com/zappee/jceks-tool.git
~~~

### 3.3) Copy
~~~
$ java -jar target/jceks-tool-0.1.0.jar copy

Usage: jceks-tool copy [-q] -a=<sourceAlias> -l=<targetAlias> -s=<sourceKeystoreLocation> -t=<targetKeystoreLocation> (-p=<sourceKeystorePassword> |
                       -f=<sourceKeystorePasswordFile>) (-e=<sourceEntryPassword> | -n=<sourceEntryPasswordFile>) (-o=<targetKeystorePassword> |
                       -u=<targetKeystorePasswordFile>) (-r=<targetEntryPassword> | -z=<targetEntryPasswordFile>)

Copy a secret key from the source keystore to a target keystore.

  -q, --quiet             In this mode nothing will be printed to the output.
  -s, --source-keystore   path to the source keystore
  -p, --source-keystore-password
                          password for the source keystore
  -f, --source-keystore-password-file
                          source keystore password file
  -a, --source-alias      alias name of the source keystore entry
  -e, --source-entry-password
                          password for the source keystore entry
  -n, --source-entry-password-file
                          source keystore entry password file
  -t, --target-keystore   path to the target keystore
  -o, --target-keystore-password
                          password for the target keystore
  -u, --target-keystore-password-file
                          target keystore password file
  -l, --target-alias      alias name of the target keystore entry
  -r, --target-entry-password
                          password for the target keystore entry
  -z, --target-entry-password-file
                          target keystore entry password file

Please report issues at arnold.somogyi@gmail.com.
Documentation, source code: https://github.com/zappee/jceks-tool.git
~~~

## 4) Contributing, improvements and bug report
* Do you like organizing?
* Do you like to code?
* Do you like helping people?
* Do you like helping others code?
* Do you like fixing bugs?

Then please
* Open an issue
* Open a pull request
* Contact with us

Contact: [arnold.somogyi@gmail.com](arnold.somogyi@gmail.com)

## 5) Licence
BSD (2-clause) licensed.

<a href="https://trackgit.com">
<img src="https://us-central1-trackgit-analytics.cloudfunctions.net/token/ping/kv448hx3umueax53m9kv" alt="trackgit-views" />
</a>
