(ns patronage.routes.home
  (:require [compojure.core         :refer :all]
            [patronage.models.db    :as    db]))

(defn home-page []
  "<h1>Home Page</h1>")

(defn about-page []
  "<h1>About Page</h1>")

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
