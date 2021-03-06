(ns patronage.repl
  (:require [liberator.dev            :refer [wrap-trace]]
            [patronage.handler        :refer :all]
            [patronage.models.logging :as    logging]
            [ring.server.standalone   :refer :all]
            [ring.middleware
             [file                    :refer [wrap-file]]
             [file-info               :refer [wrap-file-info]]]))

(defonce server (atom nil))

(defn get-handler
  []
  ;; #'app expands to (var app) so that when we reload our code, the
  ;; server is forced to re-resolve the symbol in the var rather than
  ;; having its own copy. When the root binding changes, the server
  ;; picks it up without having to restart.
  (-> #'auth-app-handler
    ;; Makes static assets in $PROJECT_DIR/resources/public/ available.
    (wrap-file "resources")
    ;; Content-Type, Content-Length, and Last Modified headers for files in body
    (wrap-file-info)
    ;; Liberator decision tree headers for debugging
    (wrap-trace :header :ui)))

(defn start-server
  "used for starting the server in development mode from REPL"
  [& [port ssl-port keystore key-password]]
  (let [port         (if port     (Integer/parseInt port)     3000)
        ssl-port     (if ssl-port (Integer/parseInt ssl-port) 3443)
        keystore     (if keystore keystore "codesykeystore")
        key-password (if key-password key-password "codesy")]
    (reset! server
            (serve (get-handler)
                   {:port         port
                    :init         init
                    :auto-reload? true
                    :destroy      destroy
                    :join?        false
                    :ssl?         true
                    :ssl-port     ssl-port
                    :keystore     keystore
                    :key-password key-password}))
    (println (str "You can view the site at http://localhost:" port))))

(defn stop-server
  []
  (.stop @server)
  (reset! server nil))
