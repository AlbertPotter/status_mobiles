(ns status-im.chat.styles.message
  (:require [status-im.components.styles :refer [color-light-blue-transparent
                                                 color-white
                                                 color-black
                                                 color-blue
                                                 selected-message-color
                                                 online-color
                                                 text1-color
                                                 text2-color]]
            [status-im.utils.platform :as p]
            [status-im.constants :refer [text-content-type
                                         content-type-command]]))

(def style-message-text
  {:fontSize   14
   :lineHeight 21
   :color      text1-color})

(def style-sub-text
  {:top        -2
   :fontSize   12
   :color      text2-color
   :lineHeight 14
   :height     16})

(defn message-padding-top
  [{:keys [new-day same-author same-direction]}]
  (cond
    new-day 0
    same-author 4
    same-direction 20
    :else 10))

(defn last-message-padding
  [{:keys [last-message typing]}]
  (when (and last-message (not typing))
    {:paddingBottom 20}))

(def message-datemark
  {:margin-top    10
   :margin-bottom -4})

(def message-empty-spacing
  {:height 16})

(def message-body-base
  {:padding-right 8
   :padding-left  8})

(defn message-body
  [{:keys [outgoing] :as message}]
  (let [align (if outgoing :flex-end :flex-start)]
    (merge message-body-base
           {:flexDirection :column
            :width         260
            :paddingTop    (message-padding-top message)
            :alignSelf     align
            :alignItems    align}
           (last-message-padding message))))

(defn incoming-group-message-body-st
  [message]
  (merge message-body-base
         {:flexDirection :row
          :alignSelf     :flex-start
          :marginTop     (message-padding-top message)
          :paddingRight  8
          :paddingLeft   8}
         (last-message-padding message)))

(def selected-message
  {:marginTop  18
   :marginLeft 40
   :fontSize   12
   :color      text2-color})

(def group-message-wrapper
  {:flexDirection :column})

(def group-message-view
  {:flexDirection :column
   :width         260
   :paddingLeft   8
   :alignItems    :flex-start})

(def message-author {:width 24})

(def photo-view {:borderRadius 12})
(def photo
  {:borderRadius 12
   :width        24
   :height       24})

(def delivery-view
  {:flexDirection :row
   :marginTop     2})

(def delivery-image
  {:marginTop 6
   :width     9
   :height    7})

(def delivery-text
  {:fontSize   12
   :color      text2-color
   :marginLeft 5})

(defn text-message
  [{:keys [outgoing group-chat incoming-group]}]
  (merge style-message-text
         {:marginTop (if incoming-group
                       4
                       0)}
         (when (and outgoing group-chat)
           {:color color-white})))

(defn message-view
  [{:keys [content-type outgoing group-chat selected]}]
  (merge {:borderRadius    14
          :padding         12
          :backgroundColor color-white}
         (when (= content-type content-type-command)
           {:paddingTop    10
            :paddingBottom 14})
         (if outgoing
           (when (and group-chat (= content-type text-content-type))
             {:backgroundColor color-blue})
           (when selected
             {:backgroundColor selected-message-color}))))

(def comand-request-view
  {:paddingRight 16})

(def command-request-message-view
  {:borderRadius    14
   :padding         12
   :paddingRight    28
   :backgroundColor color-white})

(def command-request-from-text
  (merge style-sub-text {:marginBottom 2}))

(def command-request-image-touchable
  {:position       :absolute
   :top            4
   :right          -8
   :alignItems     :center
   :justifyContent :center
   :width          48
   :height         48})

(defn command-request-image-view [command scale]
  {:width           32
   :height          32
   :borderRadius    16
   :backgroundColor (:color command)
   :transform       [{:scale scale}]})

(def command-image-view
  {:position   :absolute
   :top        0
   :right      0
   :width      24
   :height     24
   :alignItems :center})

(def command-request-image
  {:position :absolute
   :top      9
   :left     10
   :width    12
   :height   13})

(def command-request-text-view
  {:marginTop 4
   :height    14})


(def content-command-view
  {:flexDirection :column})

(def command-container
  {:flexDirection :row
   :marginRight   32})

(def command-image
  {:margin-top 5
   :width      12
   :height     13
   :tint-color :#a9a9a9cc})

(def command-text
  (merge style-message-text
         {:marginTop        8
          :marginHorizontal 0}))

(def audio-container
  {:flexDirection :row
   :alignItems    :center})

(def play-view
  {:width        33
   :height       33
   :borderRadius 16
   :elevation    1})

(def play-image
  {:width  33
   :height 33})

(def track-container
  {:marginTop  10
   :marginLeft 10
   :width      120
   :height     26
   :elevation  1})

(def track
  {:position        :absolute
   :top             4
   :width           120
   :height          2
   :backgroundColor :#EC7262})

(def track-mark
  {:position        :absolute
   :left            0
   :top             0
   :width           2
   :height          10
   :backgroundColor :#4A5258})

(def track-duration-text
  {:position      :absolute
   :left          1
   :top           11
   :fontSize      11
   :color         :#4A5258
   :letterSpacing 1
   :lineHeight    15})

(def status-container
  {:flex           1
   :alignSelf      :center
   :alignItems     :center
   :width          249
   :padding-bottom 16})

(def status-image-view
  {:marginTop 20})

(def status-from
  {:marginTop  20
   :fontSize   18
   :color      text1-color})

(def status-text
  {:marginTop  10
   :fontSize   14
   :lineHeight 20
   :textAlign  :center
   :color      text2-color})

(def online-container
  {:position        :absolute
   :top             44
   :left            44
   :width           24
   :height          24
   :borderRadius    12
   :backgroundColor online-color
   :borderWidth     2
   :borderColor     color-white})

(def online-dot
  {:position        :absolute
   :top             8
   :width           4
   :height          4
   :borderRadius    2
   :backgroundColor color-white})

(def online-dot-left
  (assoc online-dot :left 5))

(def online-dot-right
  (assoc online-dot :left 11))

(def contact-photo-container
  {:borderRadius 32})

(def contact-photo
  {:border-radius 32
   :width         64
   :height        64})

(def message-date-container
  {:backgroundColor   color-light-blue-transparent
   :height            24
   :borderRadius      12
   :alignSelf         :center
   :marginTop         20
   :marginBottom      20
   :paddingTop        5
   :paddingHorizontal 12})

(def message-date-text
  (assoc style-sub-text :textAlign :center))

(defn message-container [height]
  {:height height})

(defn new-message-container [margin]
  {:backgroundColor color-white
   :elevation       4
   :margin-bottom   margin})
