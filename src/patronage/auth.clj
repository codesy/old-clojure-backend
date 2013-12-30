(ns patronage.auth
  (:require [cemerick.friend        :as    friend]
            [compojure.core         :refer :all]
            [environ.core           :refer [env]]
            [friend-oauth2.workflow :as    oauth2]
            [friend-oauth2.util     :refer [format-config-uri
                                            get-access-token-from-params]]
            [ring.util.response     :as    response]))

(def config-auth {:roles #{::user}})

(def client-config
  {:client-id     (env :github-oauth-client-id)
   :client-secret (env :github-oauth-client-secret)
   :callback {:domain "https://localhost:3443" :path "/auth/github"}})

(def uri-config
  {:authentication-uri
   {:url   "https://github.com/login/oauth/authorize"
    :query {:client_id     (:client-id client-config)
            :response_type "code"
            :redirect_uri  (format-config-uri client-config)
            :scope         "user:email"}}

   :access-token-uri
   {:url   "https://github.com/login/oauth/access_token"
    :query {:client_id     (:client-id client-config)
            :client_secret (:client-secret client-config)
            :grant_type    "authorization_code"
            :redirect_uri  (format-config-uri client-config)}}})

(def github-workflow
  (oauth2/workflow
   {:client-config        client-config
    :uri-config           uri-config
    :access-token-parsefn get-access-token-from-params
    :config-auth          config-auth}))

(defroutes auth-routes
  (friend/logout
   (ANY "/logout" [request] (response/redirect "/"))))
