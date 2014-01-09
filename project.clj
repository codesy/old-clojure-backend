(defproject patronage "0.1.0-SNAPSHOT"

  :description "codesy.io"

  :url "http://github.com/codesy/patronage"

  :license {:name "GNU Affero General Public License, version 3 (AGPLv3)"
            :url  "http://www.gnu.org/licenses/agpl-3.0.html"}

  :repl-options {:init-ns patronage.repl}

  :dependencies [[org.clojure/clojure       "1.5.1"]
                 [dire                      "0.5.1"]

                 ;; HTTP
                 [ring/ring-core            "1.2.1"]
                 [ring-server               "0.3.1"]

                 ;; Routing
                 [compojure                 "1.1.6"]

                 ;; Database
                 [korma                     "0.3.0-RC6"]

                 ;; JSON
                 [cheshire                  "5.2.0"]

                 ;; REST architecture
                 [liberator                 "0.10.0"]

                 ;; Authentication/Authorization
                 [com.cemerick/friend       "0.2.0"]
                 [friend-oauth2             "0.1.1"]

                 ;; Logging
                 [log4j                     "1.2.17"
                  :exclusions [javax.mail/mail
                               javax.jms/jms
                               com.sun.jdmk/jmxtools
                               com.sun.jmx/jmxri]]
                 [com.taoensso/timbre       "3.0.0-RC2"]
                 [com.postspectacular/rotor "0.1.0"]

                 ;; Environment Settings
                 [environ                   "0.4.0"]

                 ;; Internationalization
                 [com.taoensso/tower        "2.0.1"]]

  :ring {:handler patronage.handler/app-handler
         :init    patronage.handler/init
         :destroy patronage.handler/destroy}

  :profiles {:production
             {:dependencies [[org.postgresql/postgresql        "9.3-1100-jdbc41"]]
              :ring {:open-browser? false
                     :stacktraces?  false
                     :auto-reload?  false}}

             :dev
             {:dependencies [[org.clojure/tools.trace "0.7.6"]
                             [ring-mock               "0.1.5"]
                             [ring/ring-devel         "1.2.1"]
                             [com.h2database/h2       "1.3.174"]]
              :ring {:adapter {:ssl-port     3443
                               :keystore     "codesykeystore"
                               :key-password "codesy"}
                     :nrepl   {:start?       true}}}}

  :plugins [[lein-ring      "0.8.8"]
            [lein-environ   "0.4.0"]]

  :min-lein-version "2.0.0")
