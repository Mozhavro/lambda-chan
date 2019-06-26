(ns imbo-chan.core
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.api.sweet :refer [routes]]
            [imbo-chan.thread :refer [thread-routes]])
  (:gen-class))


(def db-spec
  {:dbtype "postgres"
   :dbname "imbo"
   :user "mozhar"
   :password ""})


(def app (apply routes user-routes))


(defn -main [& args]
  (db/set-default-db-connection! db-spec)
  (models/set-root-namespace! 'imbo-chan.models)
  (run-jetty app {:port 3000}))
