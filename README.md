# patronage

[codesy](http://codesy.io) is a pay-what-you-want market for the open source community to encourage coders to fix important bugs.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To generate a key pair and certificate for local development, run:

    $ keytool -keystore codesykeystore -alias codesy -genkey -keyalg RSA
    Enter keystore password:  password
    What is your first and last name?
      [Unknown]:  localhost
    What is the name of your organizational unit?
      [Unknown]:
    What is the name of your City or Locality?
      [Unknown]:
    What is the name of your State or Province?
      [Unknown]:
    What is the two-letter country code for this unit?
      [Unknown]:
    Is CN=localhost, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown correct?
      [no]:  yes

    Enter key password for <codesy>
            (RETURN if same as keystore password):  password

The only mandatory response is to provide the fully qualified host name of the server at the "first and last name" prompt.

To start a web server for the application, run:

    lein ring server

## License

    Copyright © 2013 Edward Cho

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
