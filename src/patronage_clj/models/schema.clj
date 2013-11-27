(ns patronage-clj.models.schema
  (:require [clojure.java.jdbc :as sql]
            [noir.io :as io]))

(def db-store "site.db")

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname (str (io/resource-path) db-store)
              :user "sa"
              :password ""
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})

(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (new java.io.File (str (io/resource-path) db-store ".h2.db"))))

(defn create-users-table
  []
  (sql/with-connection db-spec
    (sql/create-table
      :users
      [:id                 "integer PRIMARY KEY AUTO_INCREMENT"]
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
     [:id       "integer PRIMARY KEY AUTO_INCREMENT"]
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
