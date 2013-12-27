(ns patronage.models.logging
  (:require [clojure.string      :as    str]
            [dire.core           :refer [with-pre-hook!]]
            [korma.db            :as    db]
            [taoensso.timbre     :as    timbre]))

(defn log-sql
  [query]
  (let [sql    (:sql-str query)
        params (if (empty? (:params query))
                 "none"
                 (str/join ", " (:params query)))]
    (timbre/info (str (:sql-str query)
                      " | params: "
                      params))))

(with-pre-hook! #'db/exec-sql #'log-sql)
