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
                  db/create-bid!  (fn [bid] {::id 1})
                  db/get-bid      (fn [id] (assoc bid :id 1))
                  db/update-bid!  (fn [id new-bid]
                                    (merge bid new-bid))
                  db/delete-bid!  (fn [id] nil)]
      (testing "listing all bids"
        (let [response (-> (request :get "/bids")
                           api-routes)]
          (is (= (:status response) 200))
          (is (= (:body response)
                 (json/generate-string bids)))))

      (testing "creating a bid"
        (let [response (-> (request :post "/bids"
                                    (json/generate-string bid))
                           api-routes)]
          (is (= (:status response) 201))))

      (testing "showing a bid"
        (let [response (-> (request :get "/bids/1")
                           api-routes)]
          (is (= (:status response) 200))
          (is (= (:body response)
                 (json/generate-string (assoc bid :id 1))))))

      (testing "updating a bid"
        (let [new-bid {:offer 10.0
                       :ask   100.0}
              response (-> (request :put "/bids/1"
                                    (json/generate-string new-bid))
                           api-routes)]
          (is (= (:status response) 201))))

      (testing "deleting a bid"
        (let [response (-> (request :delete "/bids/1")
                           api-routes)]
          (is (= (:status response) 204)))))))
