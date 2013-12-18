(ns patronage.routes.api-test
  (:require [cheshire.core        :as    json]
            [clojure.test         :refer [deftest
                                          is
                                          testing]]
            [liberator.dev        :refer [wrap-trace]]
            [patronage.routes.api :refer [api-routes]]
            [patronage.models.db  :as    db]
            [ring.mock.request    :refer [request]]))

(deftest test-api-handler
  (let [bid {:url   "https://github.com/githubber/gitproject/issues/1"
             :offer 5.0
             :ask   50.0}
        bids [{:id    1
               :url   "https://github.com/githubber/gitproject/issues/1"
               :offer 5.0
               :ask   50.0}]]
    (with-redefs [db/get-all-bids (fn [] bids)
                  db/create-bid   (fn [bid] {::id 1})]
      (testing "bids list route"
        (let [response (-> (request :get "/bids")
                           api-routes)]
          (is (= (:status response) 200))
          (is (= (:body response)
                 (json/generate-string bids)))))

      (testing "bids create route"
        (let [response (-> (request :post "/bids" (json/generate-string bid))
                           api-routes)]
          (is (= (:status response) 201)))))))
