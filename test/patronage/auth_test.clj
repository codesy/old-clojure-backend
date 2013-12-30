(ns patronage.auth-test
  (:require [clojure.test      :refer [deftest
                                       is
                                       testing]]
            [patronage.auth    :refer [auth-routes
                                       github-workflow]]
            [ring.mock.request :refer [request]]))

(deftest test-auth-handler
  (let [base-url "https://localhost:3443"]
    (with-redefs [github-workflow
                  (fn [config]
                    (fn [request]
                      {:identity :test-access-token}))]
      (testing "logout"
        (let [response (-> (request :get (str base-url "/logout"))
                           auth-routes)]
          (is (= (:status response) 302))
          (is (= (get-in response [:headers "Location"]) "/")))))))
