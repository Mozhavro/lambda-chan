(ns imbo-chan.topic
  (:require [schema.core :as s]
            [common.utils :as utils]
            [imbo-chan.models.topic :refer [Topic]]
            [toucan.db :as db]
            [ring.util.http-response :refer [created]]
            [compojure.api.sweet :refer [POST]]))

(def max-topic-name-length 100)
(def max-topic-description-length 240)

(defn valid-name? [name]
  (utils/non-blank-with-max-length? name max-topic-name-length))

(defn valid-description? [description]
  (utils/non-blank-with-max-length? description max-topic-description-length))

(s/defschema TopicRequestSchema
  {:name (s/constrained s/Str valid-name?)
   :description (s/constrained s/Str valid-description?)
   :board_id (s/constrained s/Int utils/non-blank?)})

(defn id->created [id]
  (created (str "/thread/" id) {:id id}))


(defn create-topic-handler [create-user-req]
  (->> create-user-req (db/insert! Topic)
       :id
       id->created))


(def topic-routes
  [(POST "/thread" []
     :body [create-topic-req TopicRequestSchema]
     (create-topic-handler create-topic-req))])
