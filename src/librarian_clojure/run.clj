(ns librarian-clojure.run
  "Bootstrap for 'lein run'"
  (:use librarian-clojure.core
        [clojure.java.browse :only (browse-url)])
  (:require [librarian-clojure.db :as db]))

(defn- parse-port [[port] & args]
  (Integer. 
    (or port (get (System/getenv) "PORT") 8080)))

(defn- start-and-browse [args url]
  (let [port (parse-port args)]
    (start-server port)
    (browse-url (str "http://localhost:" port url))))

(defn- start [args]
  (let [port (parse-port args)]
    (start-server port)))

(defn run-local [& args]
  (db/init :local)
  (start-and-browse args "/"))

(defn run-local-ring [& args]
  (db/init :local))

(defn run-heroku [& args]
  (db/init :heroku)
  (start args))

(defn -main [] 
  (do
    (println "Welcome to librarian-clojure!")
    (println "")
    (println "Usage (leiningen 1):")
    (println "lein run :local [port]")
    (println "\tstarts server on [port] connecting to a local MongoDB instance, and launches web browser")
    (println "lein run :heroku [port]")
    (println "\tstarts server on [port] with MongoDB set up for Heroku")
    (println "")
    (println "Usage (leiningen 2):")
    (println "lein run-local [port]")
    (println "\tstarts server on [port] connecting to a local MongoDB instance, and launches web browser")
    (println "")
    (println "If port is empty, uses PORT environment variable, or defaults to 8080.")))
