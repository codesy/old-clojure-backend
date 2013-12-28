# patronage-clj

[codesy](http://codesy.io) is a pay-what-you-want market for the open source community to encourage coders to fix important bugs.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To generate a key pair and certificate for local development, run:

    keytool -keystore codesykeystore -alias codesy -genkey -keyalg RSA

The only mandatory response is to provide the fully qualified host name of the server at the "first and last name" prompt.

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2013 FIXME
