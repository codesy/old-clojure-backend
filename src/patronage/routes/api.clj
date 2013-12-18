(ns patronage.routes.api
  (:require [cheshire.core       :as    json]
            [compojure.core      :refer :all]
            [liberator.core      :refer [defresource]]
            [patronage.models.db :as    db]))

(defresource bids
  :available-media-types ["application/json"]
  :allowed-methods       [:get]
  :handle-ok             (fn [_] (-> (db/get-all-bids)
                                    (json/generate-string))))

(defroutes api-routes
  (GET "/bids" [] bids))
