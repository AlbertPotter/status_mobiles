(ns syng-im.models.messages
  (:require [syng-im.persistence.realm :as r]
            [re-frame.core :refer [dispatch]]
            [cljs.reader :refer [read-string]]
            [syng-im.utils.random :refer [timestamp]]
            [syng-im.db :as db]
            [syng-im.utils.logging :as log]
            [clojure.string :refer [join split]]
            [clojure.walk :refer [stringify-keys keywordize-keys]]
            [syng-im.constants :as c]))

(defn- map-to-str
  [m]
  (join ";" (map #(join "=" %) (stringify-keys m))))

(defn- str-to-map
  [s]
  (keywordize-keys (apply hash-map (split s #"[;=]"))))

(def default-values
  {:outgoing       false
   :to             nil
   :same-author    false
   :same-direction false})

(defn save-message
  ;; todo remove chat-id parameter
  [chat-id {:keys [msg-id content]
            :as   message}]
  (when-not (r/exists? :msgs :msg-id msg-id)
    (r/write
      (fn []
        (let [content' (if (string? content)
                         content
                         (map-to-str content))
              message' (merge default-values
                              message
                              {:chat-id         chat-id
                               :content         content'
                               :timestamp       (timestamp)
                               :delivery-status nil})]
          (r/create :msgs message' true))))))

(defn command-type? [type]
  (contains?
    #{c/content-type-command c/content-type-command-request}
    type))

(defn get-messages [chat-id]
  (->> (-> (r/get-by-field :msgs :chat-id chat-id)
           (r/sorted :timestamp :asc)
           (r/collection->map))
       (into '())
       (map (fn [{:keys [content-type] :as message}]
              (if (command-type? content-type)
                (update message :content str-to-map)
                message)))))

(defn update-message! [{:keys [msg-id] :as msg}]
  (log/debug "update-message!" msg)
  (r/write
    (fn []
      (when (r/exists? :msgs :msg-id msg-id)
        (r/create :msgs msg true)))))
