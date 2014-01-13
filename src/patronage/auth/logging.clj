(ns patronage.auth.logging
  (:require [dire.core :refer [with-handler!]]))

(with-handler! #'github-oauth-callback
  java.net.MalformedURLException
  (fn [e & args] (println "GITHUB_OAUTH_CALLBACK is not set.")))
