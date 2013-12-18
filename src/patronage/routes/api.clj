(ns patronage.routes.api
  (:require [cheshire.core       :as    json]
            [compojure.core      :refer :all]
            [liberator.core      :refer [defresource]]
            [patronage.models.db :as    db]))

(defresource bids
  :available-media-types ["application/json"]
  :allowed-methods       [:get :post]
  :handle-ok             (fn [ctx] (-> (db/get-all-bids)
                                      json/generate-string))
  :post!                 (fn [ctx] (db/create-bid
                                   (-> (get-in ctx [:request :body])
                                       slurp
                                       json/parse-string))))

(defroutes api-routes
  (ANY "/bids" [] bids))
