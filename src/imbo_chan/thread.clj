(ns imbo-chan.thread
  (:require [schema.core :as s]
            [common.utils :as utils]
            [imbo-chan.models.thread :refer [Thread]]
            [toucan.db :as db]
            [ring.util.http-response :refer [created]]
            [compojure.api.sweet :refer [POST]]))

(def max-thread-name-length 100)
(def max-thread-description-length 240)

(defn valid-name? [name]
  (utils/non-blank-with-max-length? name max-thread-name-length))

(defn valid-description? [description]
  (utils/non-blank-with-max-length? description max-thread-description-length))

(s/defschema ThreadRequestSchema
  {:slug (s/constrained s/Str valid-name?)
   :description (s/constrained s/Str valid-description?)
   :board_id (s/constrained s/Int utils/non-blank?)})

(defn id->created [id]
  (created (str "/threads/" id) {:id id}))


(defn create-thread-handler [create-user-req]
  (->> create-user-req (db/insert! Thread)
       :id
       id->created))


(def thread-routes
  [(POST "/threads" []
     :body [create-thread-req ThreadRequestSchema]
     (create-thread-handler create-thread-req))])
