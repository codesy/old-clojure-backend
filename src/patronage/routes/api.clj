(ns patronage.routes.api
  (:require [cheshire.core       :as    json]
            [compojure.core      :refer :all]
            [liberator.core      :refer [defresource]]
            [patronage.models.db :as    db]))

(defn list-bids [ctx]
  (json/generate-string (db/get-all-bids)))

(defn create-bid [ctx]
  (db/create-bid
   (-> (get-in ctx [:request :body])
       slurp
       json/parse-string)))

(defn show-bid
  [id]
  (fn [ctx]
    (let [bid (db/get-bid id)]
      (when-not (nil? bid)
        {::bid (json/generate-string bid)}))))

(defn update-bid
  [id]
  (fn [ctx]
    (let [values (-> (get-in ctx [:request :body])
                     slurp
                     json/parse-string)]
      (db/update-bid id
                     (:url values)
                     (:offer values)
                     (:ask values)))))

(defresource bids
  :available-media-types ["application/json"]
  :allowed-methods       [:get :post]
  :handle-ok             list-bids
  :post!                 create-bid)

(defresource bid [id]
  :available-media-types ["application/json"]
  :allowed-methods       [:get :put]
  :exists?               (show-bid id)
  :existed?              (fn [_] (not (nil? (db/get-bid id))))
  :handle-ok             ::bid
  :put!                  (update-bid id))

(defroutes api-routes
  (ANY ["/bids/:id" :id #".*"] [id] (bid id))
  (ANY "/bids" [] bids))
