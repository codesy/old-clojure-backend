(ns patronage.handler-test
  (:require [cheshire.core       :as    json]
            [clojure.test        :refer [deftest
                                         is
                                         testing]]
            [patronage.handler   :refer [app-handler]]
            [patronage.models.db :as    db]
            [ring.mock.request   :refer [request]]))

(deftest test-app-handler
  (testing "main route"
    (let [response (app-handler (request :get "/"))]
      (is (= (:status response) 404))))

  (testing "bids list route"
    (let [bids [{:id    1
                  :url   "https://github.com/githubber/gitproject/issues/1"
                  :offer 5.0
                  :ask   50.0}]]
      (with-redefs [db/get-all-bids (fn [] bids)]
        (let [response (app-handler (request :get "/bids"))]
          (is (= (:status response) 200))
          (is (= (:body response)
                 (json/generate-string bids))))))))
