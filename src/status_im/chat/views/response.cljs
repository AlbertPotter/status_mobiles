(ns status-im.chat.views.response
  (:require-macros [status-im.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [status-im.components.react :refer [view
                                                animated-view
                                                icon
                                                image
                                                text
                                                text-input
                                                touchable-highlight]]
            [status-im.chat.views.response-suggestions :refer [response-suggestions-view]]
            [status-im.chat.styles.response :as st]))

(defn drag-touchable []
  [touchable-highlight {:style   st/drag-touchable
                        :onPress (fn []
                                   ;; TODO drag up/down
                                   )}
   [view nil
    [icon :drag-white st/drag-down-icon]]])

(defn command-icon []
  [view st/command-icon-container
   ;; TODO stub data: command icon
   [icon :dollar-green st/command-icon]])

(defn info-container [command]
  [view st/info-container
   [text {:style st/command-name}
    (:description command)]
   [text {:style st/message-info}
    ;; TODO stub data: request message info
    "By ???, MMM 1st at HH:mm"]])

(defview request-info []
  [command [:get-chat-command]]
  [view (st/request-info (:color command))
   [drag-touchable]
   [view st/inner-container
    [command-icon nil]
    [info-container command]
    [touchable-highlight {:on-press #(dispatch [:start-cancel-command])}
     [view st/cancel-container
      [icon :close-white st/cancel-icon]]]]])

(defview request-view []
  [height [:get-in [:animations :response-suggestions-height]]]
  [animated-view {:style (st/request-view height)}
   [request-info]
   [response-suggestions-view]])
