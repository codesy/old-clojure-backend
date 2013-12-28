(ns patronage.handler
  (:require [cemerick.friend           :as    friend]
            [com.postspectacular.rotor :as    rotor]
            [compojure.core            :refer :all]
            [compojure.handler         :as    handler]
            [compojure.route           :as    route]
            [liberator.dev             :refer [wrap-trace]]
            [patronage.auth            :refer :all]
            [patronage.routes.api      :refer [api-routes]]
            [ring.util.response        :as    response]
            [taoensso.timbre           :as    timbre]))

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
  (timbre/info "patronage started successfully"))

(defn destroy
  "destroy will be called when your application shuts down, put any
   clean up code here"
  []
  (timbre/info "patronage is shutting down..."))

(def app-handler (-> (routes api-routes
                             auth-routes
                             app-routes)
                     (friend/requires-scheme :https)
                     (friend/authenticate github-workflow)
                     (wrap-trace :header :ui)
                     handler/site))
