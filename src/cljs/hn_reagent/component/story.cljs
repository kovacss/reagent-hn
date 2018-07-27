(ns hn-reagent.component.story
    (:require [reagent.core :as r]))

(defn story-item [story]
    [:li.list-group-item
        [:a.title {:href (:url story)}   (:title story)]
        [:div
        [:span (:score story)] (str " points by ") [:span (:by story)]] ]
    )

(defn story-list [stories]
    [:ul.list-group
        (for [story (sort-by :score > stories)]
            ^{:key (:id story)} [story-item story])
        ]
    )