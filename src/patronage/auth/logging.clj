(ns patronage.auth.logging
  (:require [dire.core             :refer [with-handler!]]
            [patronage.auth.github :refer :all]))

(with-handler! #'github-oauth-callback
  java.net.MalformedURLException
  (fn [e & args] (println "GITHUB_OAUTH_CALLBACK is not set.")))
