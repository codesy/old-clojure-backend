(ns patronage.routes.api
  (:require [cemerick.friend     :as    friend]
            [cheshire.core       :as    json]
            [compojure.core      :refer :all]
            [liberator.core      :refer [defresource]]
            [patronage.models.db :as    db]))

(defn get-bids
  []
  (fn [ctx]
    (if-let [bids (db/get-all-bids)]
      (json/generate-string bids)
      nil)))

(defn post-bid!
  []
  (fn [ctx]
    (when-let [bid (-> (get-in ctx [:request :body])
                       slurp
                       json/parse-string)]
      (db/create-bid! bid))))

(defn get-bid
  [id]
  (fn [ctx]
    (if-let [bid (db/get-bid id)]
      {::bid (json/generate-string bid)}
      nil)))

(defn put-bid!
  [id]
  (fn [ctx]
    (when-let [bid (-> (get-in ctx [:request :body])
                       slurp
                       json/parse-string)]
      (db/update-bid! id ))))

(defn delete-bid!
  [id]
  (fn [ctx]
    (db/delete-bid! id)))

(defresource bids
  :available-media-types ["application/json"]
  :allowed-methods       [:get :post]
  :handle-ok             (get-bids)
  :post!                 (post-bid!))

(defresource bid [id]
  :available-media-types ["application/json"]
  :allowed-methods       [:get :put :delete]
  :exists?               (get-bid id)
  :existed?              (fn [_] (not (nil? (db/get-bid id))))
  :handle-ok             ::bid
  :put!                  (put-bid! id)
  :delete!               (delete-bid! id))

(defroutes api-routes
  (ANY ["/bids/:id" :id #".*"] [id]
       (bid id))
  (ANY "/bids" []
       bids))

(defroutes api-v1-routes
  (context "/api/v1" [] (friend/authorize #{::user} api-routes)))
