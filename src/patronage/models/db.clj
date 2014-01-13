(ns patronage.models.db
  (:require [korma.core                  :refer :all]
            [korma.db                    :refer [defdb]]
            [patronage.models.migrations :refer [db-spec]]))

(defdb db db-spec)

(declare users bids)

(defn- truthy-fields
  [entity]
  (into {} (filter (comp not nil? val) entity)))

;; ===========================================================================
;; Users
;; ===========================================================================
(defentity users
  (has-many bids))

(defn create-user! [user]
  (insert users (values user)))

(defn update-user! [id user]
  (when-let [fields (truthy-fields user)]
    (update users (set-fields fields) (where {:id id}))))

(defn get-user [id]
  (first (select users (where {:id id}) (limit 1))))

(defn get-all-users []
  (select users))

;; ===========================================================================
;; Bids
;; ===========================================================================
(defentity bids
  (belongs-to users {:fk :user_id}))

(defn create-bid! [bid]
  (insert bids (values bid)))

(defn update-bid! [id bid]
  (when-let [fields (truthy-fields bid)]
    (update bids (set-fields fields) (where {:id id}))))

(defn get-bid [id]
  (first (select bids (where {:id id}) (limit 1))))

(defn get-all-bids []
  (select bids))

(defn delete-bid! [id]
  (delete bids (where {:id id})))
