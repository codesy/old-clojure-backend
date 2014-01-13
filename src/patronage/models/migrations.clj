(ns patronage.models.migrations
  (:require [clojure.java.jdbc :as    sql]
            [environ.core      :refer [env]]
            [ragtime.core      :refer [connection]]
            [ragtime.sql.database]))

(def db-spec (connection (env :database-url)))

(def add-users
  {:id "add-users"
   :up (fn [db-spec]
         (sql/with-connection db-spec
           (sql/create-table
            :users
            [:id                  "serial primary key"]
            [:first_name          "varchar(64)"]
            [:last_name           "varchar(64)"]
            [:email               "varchar(128)"]
            [:password            "varchar(128)"]
            [:is_admin            :boolean]
            [:current_sign_in_at  :time]
            [:last_sign_in_at     :time]
            [:created_at          :time]
            [:updated_at          :time])))
   :down (fn [db-spec]
           (sql/with-connection db-spec
             (sql/drop-table :users)))})

(def add-bids
  {:id "add-bids"
   :up (fn [db-spec]
         (sql/with-connection db-spec
           (sql/create-table
            :bids
            [:id         "serial PRIMARY KEY"]
            [:url        "varchar(2000)"]
            [:offer      "bigint"]
            [:ask        "bigint"]
            [:user_id    "integer NOT NULL"]
            [:created_at :time]
            [:updated_at :time])))
   :down (fn [db-spec]
           (sql/with-connection db-spec
             (sql/drop-table :bids)))})

(def all
  [add-users
   add-bids])
