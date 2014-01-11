(ns patronage.models.schema
  (:require [clojure.java.jdbc   :as    sql]
            [environ.core        :refer [env]]
            [korma.db            :refer :all ]
            [patronage.util      :as    util]))

(def db-spec (env :database-url))

(defn create-users-table
  []
  (sql/with-connection db-spec
    (sql/create-table
       :users
       [:id                 "serial PRIMARY KEY"]
       [:first_name         "varchar(30)"]
       [:last_name          "varchar(30)"]
       [:email              "varchar(30)"]
       [:admin              :boolean]
       [:current_sign_in_at :time]
       [:last_sign_in_at    :time]
       [:password           "varchar(100)"])))

(defn create-bids-table
  []
  (sql/with-connection db-spec
    (sql/create-table
       :bids
       [:id       "serial PRIMARY KEY"]
       [:url      "varchar(2000)"]
       [:offer    "bigint"]
       [:ask      "bigint"]
       [:user_id "integer NOT NULL"])))

(defn create-tables
  "creates the database tables used by the application"
  []
  (create-users-table)
  (create-bids-table))

(defn drop-users-table
  []
  (sql/with-connection db-spec
    (sql/drop-table :users)))

(defn drop-bids-table
  []
  (sql/with-connection db-spec
    (sql/drop-table :bids)))

(defn drop-tables
  "nukes the database tables from orbit. it's the only way to be sure"
  []
  (drop-users-table)
  (drop-bids-table))
