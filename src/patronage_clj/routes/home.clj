(ns patronage-clj.routes.home
  (:use compojure.core)
  (:require [patronage-clj.views.layout :as layout]
            [patronage-clj.util :as util]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
