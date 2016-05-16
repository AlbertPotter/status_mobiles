(ns syng-im.android.core
  (:require-macros
    [natal-shell.back-android :refer [add-event-listener remove-event-listener]])
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [syng-im.handlers]
            [syng-im.subs]
            [syng-im.components.react :refer [navigator app-registry]]
            [syng-im.contacts.screen :refer [contact-list]]
            [syng-im.discovery.screen :refer [discovery]]
            [syng-im.discovery.tag :refer [discovery-tag]]
            [syng-im.chat.screen :refer [chat]]
            [syng-im.chats-list.screen :refer [chats-list]]
            [syng-im.new-group.screen :refer [new-group]]
            [syng-im.participants.views.create :refer [new-participants]]
            [syng-im.participants.views.remove :refer [remove-participants]]
            [syng-im.profile.screen :refer [profile my-profile]]
            [syng-im.utils.utils :refer [toast]]
            [syng-im.utils.encryption]))

(defn init-back-button-handler! []
  (let [new-listener (fn []
                       ;; todo: it might be better always return false from
                       ;; this listener and handle application's closing
                       ;; in handlers
                       (let [stack (subscribe [:navigation-stack])]
                         (when (< 1 (count @stack))
                           (dispatch [:navigate-back])
                           true)))]
    (add-event-listener "hardwareBackPress" new-listener)))

(defn app-root []
  (let [signed-up (subscribe [:signed-up])
        view-id   (subscribe [:view-id])]
    (fn []
      (case (if @signed-up @view-id :chat)
        :discovery [discovery]
        :discovery-tag [discovery-tag]
        :add-participants [new-participants]
        :remove-participants [remove-participants]
        :chat-list [chats-list]
        :new-group [new-group]
        :contact-list [contact-list]
        :chat [chat]
        :profile [profile]
        :my-profile [my-profile]))))

(defn init []
  (dispatch-sync [:initialize-db])
  (dispatch [:initialize-crypt])
  (dispatch [:initialize-chats])
  (dispatch [:initialize-protocol])
  (dispatch [:load-user-phone-number])
  (dispatch [:load-contacts])
  ;; load commands from remote server (todo: uncomment)
  ;; (dispatch [:load-commands])
  (dispatch [:init-console-chat])
  (dispatch [:init-chat])
  (init-back-button-handler!)
  (.registerComponent app-registry "SyngIm" #(r/reactify-component app-root)))
