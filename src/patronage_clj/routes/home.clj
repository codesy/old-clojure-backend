(ns patronage-clj.routes.home
  (:use compojure.core)
  (:require [patronage-clj.models.db    :as db]
            [patronage-clj.views.layout :as layout]
            [patronage-clj.util         :as util]))

(defn home-page []
  (layout/render
   "home.html" {:content (util/md->html "/md/docs.md")
                :users   (db/get-all-users)
                :bids    (db/get-all-bids)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
