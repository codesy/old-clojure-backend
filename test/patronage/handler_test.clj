(ns patronage.handler-test
  (:require [clojure.test        :refer [deftest
                                         is
                                         testing]]
            [patronage.handler   :refer [app-handler]]
            [ring.mock.request   :refer [request]]))

(deftest test-app-handler
  (testing "main route"
    (let [response (app-handler (request :get "/"))]
      (is (= (:status response) 404)))))
