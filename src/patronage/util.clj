(ns patronage.util
  (:require [clojure.java.io :as io])
  (:import  (java.io File)))

(defn resource-path
  "returns the path to the public folder of the application"
  []
  (if-let [path (io/resource (str "public" File/separator))]
    (.getPath path)))
