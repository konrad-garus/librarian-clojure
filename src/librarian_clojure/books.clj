(ns librarian-clojure.books
  (:use librarian-clojure.db
        [somnium.congomongo :only (object-id)]) ;; TODO: Move to librarian-clojure.db and remove
  (:require [clojure.data.json :as json]))

;; https://github.com/clojure/data.json/blob/master/src/main/clojure/clojure/data/json.clj
(defn- write-json-mongodb-objectid [x out escape-unicode?]
  (json/write-json (str x) out escape-unicode?))

(extend org.bson.types.ObjectId json/Write-JSON
  {:write-json write-json-mongodb-objectid})

(defn get-books []
  (db-get-books))

(defn add-book [author title]
  (db-add-book {:author author :title title}))

(defn update-book [id author title]
  (let [id (Integer. id)]
    (db-update-book id {:author author :title title})
    (db-get-book id)))

(defn delete-book [id]
  (let [id (Integer. id)]
    (db-delete-book id)
    {:book-deleted id}))
