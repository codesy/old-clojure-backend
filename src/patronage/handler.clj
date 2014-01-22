(ns patronage.handler
  (:require [cemerick.friend             :as    friend]
            [com.postspectacular.rotor   :as    rotor]
            [compojure.core              :refer :all]
            [compojure.handler           :as    handler]
            [compojure.route             :as    route]
            [patronage.auth.github       :refer :all]
            [patronage.models.logging    :as    logging]
            [patronage.models.migrations :as    migrations]
            [patronage.routes.api        :refer [api-v1-routes]]
            [ragtime.core                :refer [migrate-all]]
            [ring.util.response          :as    response]
            [taoensso.timbre             :as    timbre]))

(defroutes app-routes
  (GET "/" [request] "codesy.io")
  ;; (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when app is deployed as a servlet on an
   app server such as Tomcat put any initialization code here"
  []
  (timbre/set-config! [:appenders :rotor]
                      {:min-level             :info
                       :enabled?              true
                       :async?                false
                       :max-message-per-msecs nil
                       :fn                    rotor/append})
  (timbre/set-config! [:shared-appender-config :rotor]
                      {:path     "patronage.log"
                       :max-size (* 512 1024)
                       :backlog  10})
  (migrate-all migrations/db-spec migrations/all)
  (timbre/info "patronage started successfully"))

(defn destroy
  "destroy will be called when your application shuts down, put any
   clean up code here"
  []
  (timbre/info "patronage is shutting down..."))

(def app-handler (-> (routes api-v1-routes
                             app-routes)
                     handler/site))

(def auth-app-handler (-> app-handler
                          (friend/requires-scheme-with-proxy :https)
                          (friend/authenticate
                           {:allow-anon? true
                            :workflows   [github-workflow]})))
