(ns patronage.auth.logging
  (:require [clojure.pprint        :refer [pprint]]
            [dire.core             :refer [with-handler!
                                           with-pre-hook!]]
            [patronage.auth.github :refer [github-oauth-callback
                                           github-workflow]]))

(with-handler! #'github-oauth-callback
  java.net.MalformedURLException
  (fn [e & args]
    (println "GITHUB_OAUTH_CALLBACK is not set.")
    (println *err* e)))

(with-pre-hook! #'github-workflow
  "Log the request map and the exchange of code, state, and access_token"
  (fn [request]
    (pprint request)
    (when-let [code (get-in [:params :code]  request)]
      (pprint (str "Code: ")))
    (when-let [state (get-in [:params :state] request)]
      (pprint (str "State: ")))
    (when-let [access-token (get-in [:params :access_token] request)]
      (pprint (str "Access Token: ")))))
