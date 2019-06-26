(ns imbo-chan.core
  (:require [toucan.db :as db])
  (:gen-class))


(def db-spec
  {:dbtype "postgres"
   :dbname "imbo"
   :user "mozhar"
   :password ""})


(defn handler [request]
  (db/set-default-db-connection! db-spec)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})
