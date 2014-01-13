# patronage

[codesy](http://codesy.io) is a pay-what-you-want market for the open source community to encourage coders to fix important bugs.

## Prerequisites

### Java

You will need Java 6 or above installed.

On Debian or Ubuntu:

    $ apt-get install openjdk-6-jdk

or

    $ apt-get install openjdk-7-jdk

### Leiningen

You will need [Leiningen][1] 2.0 or above installed.

Clojure, unlike other programming languages, is not installed but
pulled in as a project dependency. [Leiningen][1] is the de-facto
Clojure build tool.

[1]: https://github.com/technomancy/leiningen

## GitHub authentication

`patronage` currently authenticates via GitHub OAuth2, so you will
need to modify your profile and environment to redirect back to your
local development instance.

### [Register](https://github.com/settings/applications/new) your local development instance with GitHub

    Application name: patronage-dev
    Homepage URL: https://localhost:3443
    Authorization callback URL: https://localhost:3443/auth/github

### Set GitHub OAuth2 environment variables

After registering your local development instance with GitHub, the
application profile will have tokens for `Client ID`, `Client Secret`,
and 'Authorization callback URL`. The environment variables
`GITHUB_OAUTH_CLIENT_ID`, `GITHUB_OAUTH_CLIENT_SECRET`,
`GITHUB_OAUTH_CALLBACK` can be set to these tokens:

    $ export GITHUB_OAUTH_CLIENT_ID="<client_id>"
    $ export GITHUB_OAUTH_CLIENT_SECRET="<client_secret>"
    $ export GITHUB_OAUTH_CALLBACK="<callback>"

Alternatively, they can also be saved in your Leiningen profile configuration at `$HOME/.lein/profiles.clj`:

    {:user {:env {:github-oauth-client-id     "<client-id>"
                  :github-oauth-client-secret "<client-secret>"
                  :github-oauth-callback      "<callback>"}}}

## Generate a local SSL certificate

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

## Running

To start a web server for the application, run:

    $ lein with-profile dev ring server-headless

Open a web browser and point it to https://localhost:3443.

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
